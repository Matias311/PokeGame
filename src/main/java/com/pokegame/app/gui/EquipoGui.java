package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.EquiposRepository;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class EquipoGui extends JPanel {

  private JComboBox<String> comboEquipos;
  private JPanel panelPokemones;
  private JLabel labelSinEquipos;
  private JLabel labelSinPokemones;
  private JButton botonGestionar;
  private EquiposRepository<Equipo> equipoRepository = new EquipoRepositoryImpl();

  public EquipoGui() {
    setLayout(new BorderLayout());

    comboEquipos = new JComboBox<>();
    comboEquipos.setPreferredSize(new Dimension(200, 25));
    comboEquipos.addActionListener(e -> mostrarPokemones());

    JLabel labelSeleccion = new JLabel("Equipo: ");
    labelSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelCombo.add(labelSeleccion);
    panelCombo.add(comboEquipos);
    add(panelCombo, BorderLayout.NORTH);

    // Botón único para gestionar equipos
    botonGestionar = new JButton("Gestionar Equipos");
    botonGestionar.setPreferredSize(new Dimension(180, 30));
    botonGestionar.addActionListener(e -> mostrarDialogoGestion());

    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelBoton.add(botonGestionar);
    add(panelBoton, BorderLayout.SOUTH);

    panelPokemones = new JPanel(new GridBagLayout());

    labelSinPokemones = new JLabel("El equipo no tiene Pokemones aun");
    labelSinPokemones.setHorizontalAlignment(SwingConstants.CENTER);
    labelSinPokemones.setFont(new Font("Arial", Font.BOLD, 18));

    labelSinEquipos = new JLabel("No existen equipos");
    labelSinEquipos.setHorizontalAlignment(SwingConstants.CENTER);
    labelSinEquipos.setFont(new Font("Arial", Font.BOLD, 18));
    add(labelSinEquipos, BorderLayout.CENTER);

    // AncestorListener para recargar equipos al visualizar el panel
    addAncestorListener(new AncestorListener() {
      @Override
      public void ancestorAdded(AncestorEvent event) {
        cargarEquipos();
      }
      @Override
      public void ancestorRemoved(AncestorEvent event) {}
      @Override
      public void ancestorMoved(AncestorEvent event) {}
    });
  }

  private void mostrarDialogoGestion() {
    // Crear diálogo personalizado
    JDialog dialogo = new JDialog();
    dialogo.setTitle("Gestión de Equipos");
    dialogo.setLayout(new FlowLayout());
    dialogo.setSize(300, 200);
    dialogo.setModal(true);
    dialogo.setLocationRelativeTo(this);

    // Botones para las operaciones
    JButton botonCrear = new JButton("Crear Equipo");
    botonCrear.setPreferredSize(new Dimension(200, 30));
    botonCrear.addActionListener(e -> {
      crearEquipo();
      dialogo.dispose();
    });

    JButton botonBorrar = new JButton("Borrar Equipo");
    botonBorrar.setPreferredSize(new Dimension(200, 30));
    botonBorrar.addActionListener(e -> {
      dialogo.dispose();
      seleccionarEquipoParaBorrar();
    });

    JButton botonCambiarNombre = new JButton("Cambiar Nombre");
    botonCambiarNombre.setPreferredSize(new Dimension(200, 30));
    botonCambiarNombre.addActionListener(e -> {
      dialogo.dispose();
      seleccionarEquipoParaRenombrar();
    });

    // Panel para organizar los botones
    JPanel panelBotones = new JPanel();
    panelBotones.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 10, 5, 10);

    panelBotones.add(botonCrear, gbc);
    panelBotones.add(botonBorrar, gbc);
    panelBotones.add(botonCambiarNombre, gbc);

    dialogo.add(panelBotones);
    dialogo.setVisible(true);
  }

  private void seleccionarEquipoParaBorrar() {
    List<Equipo> equipos = equipoRepository.obtenerNombresEquipos();

    if (equipos.isEmpty()) {
      JOptionPane.showMessageDialog(
              this,
              "No hay equipos para borrar.",
              "Advertencia",
              JOptionPane.WARNING_MESSAGE);
      return;
    }

    String[] nombresEquipos = equipos.stream()
            .map(Equipo::getNombre)
            .toArray(String[]::new);

    String equipoSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona el equipo a borrar:",
            "Seleccionar equipo",
            JOptionPane.PLAIN_MESSAGE,
            null,
            nombresEquipos,
            nombresEquipos[0]);

    if (equipoSeleccionado != null) {
      comboEquipos.setSelectedItem(equipoSeleccionado);
      borrarEquipo();
    }
  }

  private void seleccionarEquipoParaRenombrar() {
    List<Equipo> equipos = equipoRepository.obtenerNombresEquipos();

    if (equipos.isEmpty()) {
      JOptionPane.showMessageDialog(
              this,
              "No hay equipos para renombrar.",
              "Advertencia",
              JOptionPane.WARNING_MESSAGE);
      return;
    }

    String[] nombresEquipos = equipos.stream()
            .map(Equipo::getNombre)
            .toArray(String[]::new);

    String equipoSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona el equipo a renombrar:",
            "Seleccionar equipo",
            JOptionPane.PLAIN_MESSAGE,
            null,
            nombresEquipos,
            nombresEquipos[0]);

    if (equipoSeleccionado != null) {
      comboEquipos.setSelectedItem(equipoSeleccionado);
      cambiarNombreEquipo();
    }
  }

  private void cargarEquipos() {
    comboEquipos.removeAllItems();
    List<Equipo> equipos = equipoRepository.obtenerNombresEquipos();

    boolean hayEquipos = !equipos.isEmpty();
    botonGestionar.setEnabled(hayEquipos || true);

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
          CartaPokemon carta = new CartaPokemon(pokemon, imagen) {
            {
              if (com.pokegame.app.util.VerificarSesion.isLoggedIn()) {
                JButton botonEliminar = new JButton("-");
                botonEliminar.setMargin(new Insets(2, 6, 2, 6));
                botonEliminar.setFont(new Font("Arial", Font.BOLD, 11));
                botonEliminar.setBounds(120, 5, 25, 25);
                botonEliminar.setFocusable(false);
                botonEliminar.setBackground(new Color(240, 240, 240)); // Gris casi blanco
                botonEliminar.setForeground(new Color(100, 100, 100));

                JPanel panelBoton = new JPanel(null);
                panelBoton.setOpaque(false);
                panelBoton.setPreferredSize(new Dimension(150, 30));
                panelBoton.add(botonEliminar);

                add(panelBoton, BorderLayout.NORTH);

                botonEliminar.addActionListener(e -> {
                  int confirmacion = JOptionPane.showConfirmDialog(
                          this,
                          "¿Eliminar a " + pokemon.getNombre() + " del equipo?",
                          "Confirmar eliminación",
                          JOptionPane.YES_NO_OPTION,
                          JOptionPane.WARNING_MESSAGE);

                  if (confirmacion == JOptionPane.YES_OPTION) {
                    if (equipoRepository.eliminarPokemonDeEquipo(
                            equipoRepository.obtenerIdEquipoPorNombre(equipoSeleccionado),
                            pokemon.getId())) {
                      JOptionPane.showMessageDialog(
                              this,
                              pokemon.getNombre() + " eliminado del equipo.",
                              "Éxito",
                              JOptionPane.INFORMATION_MESSAGE);
                      mostrarPokemones(); // Refrescar la vista
                    } else {
                      JOptionPane.showMessageDialog(
                              this,
                              "Error al eliminar el Pokémon.",
                              "Error",
                              JOptionPane.ERROR_MESSAGE);
                    }
                  }
                });
              }
            }
          };
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
    String nombre =
            JOptionPane.showInputDialog(
                    this, "Ingresa el nombre del nuevo equipo:", "Nuevo Equipo", JOptionPane.PLAIN_MESSAGE);
    if (nombre != null && !nombre.trim().isEmpty()) {
      nombre = nombre.trim();
      Equipo nuevo = new Equipo(0, nombre, 1);
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
      int confirmacion =
              JOptionPane.showConfirmDialog(
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
      String nuevoNombre =
              JOptionPane.showInputDialog(
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