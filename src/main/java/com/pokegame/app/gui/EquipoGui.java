package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.EquiposRepository;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

// TODO: HACER QUE SE CARGUEN LOS EQUIPOS SOLO CON LA ID DE CLIENTE, QUE LOS EQUIPOS DE OTRA ID NO PUEDAN VER LOS MISMOS EQUIPOS QUE EL RESTO

public class  EquipoGui extends JPanel {

  private JComboBox<String> comboEquipos;
  private JPanel panelPokemones;
  private JLabel labelSinEquipos;
  private JLabel labelSinPokemones;
  private JButton botonBorrar;
  private JButton botonCambiarNombre;
  private JButton botonActualizar;
  private EquiposRepository<Equipo> equipoRepository = new EquipoRepositoryImpl();

  public EquipoGui() {
    setLayout(new BorderLayout());

    comboEquipos = new JComboBox<>();
    comboEquipos.setPreferredSize(new Dimension(200, 25));
    comboEquipos.addActionListener(e -> mostrarPokemones());

    botonActualizar = new JButton("Actualizar");
    botonActualizar.setPreferredSize(new Dimension(100, 25));
    botonActualizar.addActionListener(e -> mostrarPokemones());

    JLabel labelSeleccion = new JLabel("Equipo: ");
    labelSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelCombo.add(labelSeleccion);
    panelCombo.add(comboEquipos);
    panelCombo.add(botonActualizar);
    add(panelCombo, BorderLayout.NORTH);

    JButton botonCrear = new JButton("Crear equipo");
    botonCrear.setPreferredSize(new Dimension(150, 30));
    botonCrear.addActionListener(e -> crearEquipo());

    botonBorrar = new JButton("Borrar equipo");
    botonBorrar.setPreferredSize(new Dimension(150, 30));
    botonBorrar.addActionListener(e -> borrarEquipo());

    botonCambiarNombre = new JButton("Cambiar nombre");
    botonCambiarNombre.setPreferredSize(new Dimension(150, 30));
    botonCambiarNombre.addActionListener(e -> cambiarNombreEquipo());

    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelBoton.add(botonCrear);
    panelBoton.add(botonBorrar);
    panelBoton.add(botonCambiarNombre);
    add(panelBoton, BorderLayout.SOUTH);

    panelPokemones = new JPanel(new GridBagLayout());

    labelSinPokemones = new JLabel("El equipo no tiene Pokemones aun");
    labelSinPokemones.setHorizontalAlignment(SwingConstants.CENTER);
    labelSinPokemones.setFont(new Font("Arial", Font.BOLD, 18));

    labelSinEquipos = new JLabel("No existen equipos");
    labelSinEquipos.setHorizontalAlignment(SwingConstants.CENTER);
    labelSinEquipos.setFont(new Font("Arial", Font.BOLD, 18));
    add(labelSinEquipos, BorderLayout.CENTER);

    cargarEquipos();
  }

  private void cargarEquipos() {
    comboEquipos.removeAllItems();
    List<Equipo> equipos = equipoRepository.obtenerNombresEquipos();

    boolean hayEquipos = !equipos.isEmpty();
    botonBorrar.setEnabled(hayEquipos);
    botonCambiarNombre.setEnabled(hayEquipos);
    botonActualizar.setEnabled(hayEquipos);

    if (hayEquipos) {
      remove(labelSinEquipos);
      add(panelPokemones, BorderLayout.CENTER);
      for (Equipo equipo : equipos) {
        comboEquipos.addItem(equipo.getNombre());
      }
      comboEquipos.setSelectedIndex(0);
      mostrarPokemones();
    } else {
      panelPokemones.removeAll();
      comboEquipos.addItem("Sin equipos");
      panelPokemones.revalidate();
      panelPokemones.repaint();
      remove(panelPokemones);
      add(labelSinEquipos, BorderLayout.CENTER);
    }

    revalidate();
    repaint();
  }

  private void mostrarPokemones() {
    panelPokemones.removeAll();
    remove(labelSinPokemones);
    String equipoSeleccionado = (String) comboEquipos.getSelectedItem();

    if (equipoSeleccionado != null && !equipoSeleccionado.equals("Sin equipos")) {
      List<Pokemon> pokemones = equipoRepository.obtenerPokemonesDeEquipo(equipoSeleccionado);
      ImagenRepositoryImpl imagenRepo = new ImagenRepositoryImpl();

      if (pokemones.isEmpty()) {
        remove(panelPokemones);
        add(labelSinPokemones, BorderLayout.CENTER);
      } else {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        int columnas = 3;
        for (int i = 0; i < pokemones.size(); i++) {
          Pokemon pokemon = pokemones.get(i);
          Imagen imagen = imagenRepo.buscarImagenPorIdPortadaPokemon(pokemon.getId());
          CartaPokemon carta = new CartaPokemon(pokemon, imagen);
          gbc.gridx = i % columnas;
          gbc.gridy = i / columnas;
          panelPokemones.add(carta, gbc);
        }
        remove(labelSinPokemones);
        add(panelPokemones, BorderLayout.CENTER);
      }

      panelPokemones.revalidate();
      panelPokemones.repaint();
      revalidate();
      repaint();
    }
  }

  private void crearEquipo() {
    String nombre = JOptionPane.showInputDialog(
            this, "Ingresa el nombre del nuevo equipo:", "Nuevo Equipo", JOptionPane.PLAIN_MESSAGE);
    if (nombre != null && !nombre.trim().isEmpty()) {
      nombre = nombre.trim();
      Equipo nuevo = new Equipo(0, nombre, 1); // Cliente fijo con id 1
      if (equipoRepository.crearEquipo(nuevo)) {
        JOptionPane.showMessageDialog(
                this, "Equipo creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarEquipos();
        comboEquipos.setSelectedItem(nombre);
      } else {
        JOptionPane.showMessageDialog(
                this, "Error al crear equipo.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void borrarEquipo() {
    String nombre = (String) comboEquipos.getSelectedItem();
    if (nombre != null) {
      int confirmacion = JOptionPane.showConfirmDialog(
              this,
              "¿Seguro que deseas borrar el equipo '" + nombre + "'?",
              "Confirmar eliminación",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.PLAIN_MESSAGE);
      if (confirmacion == JOptionPane.YES_OPTION) {
        if (equipoRepository.eliminarEquipoPorNombre(nombre)) {
          JOptionPane.showMessageDialog(
                  this, "Equipo eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
          cargarEquipos();
        } else {
          JOptionPane.showMessageDialog(
                  this, "Error al eliminar equipo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  private void cambiarNombreEquipo() {
    String nombreActual = (String) comboEquipos.getSelectedItem();
    if (nombreActual != null && !nombreActual.equals("Sin equipos")) {
      String nuevoNombre = JOptionPane.showInputDialog(
              this,
              "Ingresa el nuevo nombre para el equipo:",
              "Cambiar nombre del equipo",
              JOptionPane.PLAIN_MESSAGE);
      if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
        nuevoNombre = nuevoNombre.trim();
        if (equipoRepository.actualizarNombreEquipo(nombreActual, nuevoNombre)) {
          JOptionPane.showMessageDialog(
                  this,
                  "Nombre del equipo actualizado con éxito.",
                  "Éxito",
                  JOptionPane.INFORMATION_MESSAGE);
          cargarEquipos();
          comboEquipos.setSelectedItem(nuevoNombre);
        } else {
          JOptionPane.showMessageDialog(
                  this,
                  "Error al actualizar el nombre del equipo.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
        }
      }
    } else {
      JOptionPane.showMessageDialog(
              this,
              "Selecciona un equipo válido para cambiar su nombre.",
              "Advertencia",
              JOptionPane.WARNING_MESSAGE);
    }
  }
}
