package com.pokegame.app.gui;

import com.pokegame.app.manager.EquipoManager;
import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;
import java.awt.BorderLayout;
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

  private final EquipoManager manager = new EquipoManager();
  private JComboBox<String> comboEquipos;
  private JPanel panelPokemones;
  private JLabel labelSinEquipos;
  private JLabel labelSinPokemones;
  private JButton botonGestionar;

  public EquipoGui() {
    setLayout(new BorderLayout());

    comboEquipos = new JComboBox<>();
    comboEquipos.setPreferredSize(new Dimension(200, 25));
    comboEquipos.addActionListener(e -> mostrarPokemones());

    JLabel labelSeleccion = new JLabel("Equipo: ");
    JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelCombo.add(labelSeleccion);
    panelCombo.add(comboEquipos);
    add(panelCombo, BorderLayout.NORTH);

    // ← Botón centrado como en tu diseño original
    botonGestionar = new JButton("Gestionar Equipos");
    botonGestionar.setPreferredSize(new Dimension(180, 30));
    botonGestionar.addActionListener(e -> mostrarDialogoGestion());

    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelBoton.add(botonGestionar);
    add(panelBoton, BorderLayout.SOUTH);

    panelPokemones = new JPanel(new GridBagLayout());

    labelSinPokemones = new JLabel("El equipo no tiene Pokemones aun", SwingConstants.CENTER);
    labelSinPokemones.setFont(new Font("Arial", Font.BOLD, 18));

    labelSinEquipos = new JLabel("No existen equipos", SwingConstants.CENTER);
    labelSinEquipos.setFont(new Font("Arial", Font.BOLD, 18));
    add(labelSinEquipos, BorderLayout.CENTER);

    addAncestorListener(
        new AncestorListener() {
          public void ancestorAdded(AncestorEvent event) {
            cargarEquipos();
          }

          public void ancestorRemoved(AncestorEvent event) {}

          public void ancestorMoved(AncestorEvent event) {}
        });
  }

  private void mostrarDialogoGestion() {
    JDialog dialogo = new JDialog();
    dialogo.setTitle("Gestión de Equipos");
    dialogo.setLayout(new FlowLayout());
    dialogo.setSize(300, 200);
    dialogo.setModal(true);
    dialogo.setLocationRelativeTo(this);

    JButton botonCrear = new JButton("Crear Equipo");
    botonCrear.setPreferredSize(new Dimension(200, 30));
    botonCrear.addActionListener(
        e -> {
          dialogo.dispose(); // Cierra antes de mostrar cualquier JOptionPane
          String nombre =
              JOptionPane.showInputDialog(
                  this,
                  "Ingresa el nombre del nuevo equipo:",
                  "Nuevo Equipo",
                  JOptionPane.PLAIN_MESSAGE);
          if (nombre != null && !nombre.trim().isEmpty()) {
            if (manager.crearEquipo(nombre.trim())) {
              JOptionPane.showMessageDialog(this, "Equipo creado con éxito.");
              cargarEquipos();
            }
          }
        });

    JButton botonBorrar = new JButton("Borrar Equipo");
    botonBorrar.setPreferredSize(new Dimension(200, 30));
    botonBorrar.addActionListener(
        e -> {
          dialogo.dispose();
          String nombre = (String) comboEquipos.getSelectedItem();
          if (nombre != null && manager.eliminarEquipo(nombre)) {
            JOptionPane.showMessageDialog(this, "Equipo eliminado.");
            cargarEquipos();
          }
        });

    JButton botonRenombrar = new JButton("Cambiar Nombre");
    botonRenombrar.setPreferredSize(new Dimension(200, 30));
    botonRenombrar.addActionListener(
        e -> {
          dialogo.dispose();
          String actual = (String) comboEquipos.getSelectedItem();
          String nuevo =
              JOptionPane.showInputDialog(
                  this, "Ingresa el nuevo nombre:", "Renombrar Equipo", JOptionPane.PLAIN_MESSAGE);
          if (nuevo != null && manager.actualizarNombreEquipo(actual, nuevo.trim())) {
            JOptionPane.showMessageDialog(this, "Nombre actualizado.");
            cargarEquipos();
          }
        });

    JPanel panelBotones = new JPanel();
    panelBotones.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 10, 5, 10);

    panelBotones.add(botonCrear, gbc);
    panelBotones.add(botonBorrar, gbc);
    panelBotones.add(botonRenombrar, gbc);

    dialogo.add(panelBotones);
    dialogo.setVisible(true);
  }

  private void cargarEquipos() {
    comboEquipos.removeAllItems();
    List<Equipo> equipos = manager.obtenerEquipos();

    if (!equipos.isEmpty()) {
      remove(labelSinEquipos);
      add(panelPokemones, BorderLayout.CENTER);
      for (Equipo e : equipos) comboEquipos.addItem(e.getNombre());
      comboEquipos.setSelectedIndex(0);
      mostrarPokemones();
    } else {
      comboEquipos.addItem("Sin equipos");
      remove(panelPokemones);
      add(labelSinEquipos, BorderLayout.CENTER);
    }

    revalidate();
    repaint();
  }

  private void mostrarPokemones() {
    panelPokemones.removeAll();
    remove(labelSinPokemones);
    String nombreEquipo = (String) comboEquipos.getSelectedItem();

    if (nombreEquipo != null && !nombreEquipo.equals("Sin equipos")) {
      List<Pokemon> pokemones = manager.obtenerPokemones(nombreEquipo);
      ImagenRepositoryImpl imagenRepo = new ImagenRepositoryImpl();

      if (pokemones.isEmpty()) {
        remove(panelPokemones);
        add(labelSinPokemones, BorderLayout.CENTER);
      } else {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < pokemones.size(); i++) {
          Pokemon pokemon = pokemones.get(i);
          Imagen imagen = imagenRepo.buscarImagenPorIdPortadaPokemon(pokemon.getId());
          CartaEquipo carta =
              new CartaEquipo(pokemon, imagen, nombreEquipo, this::mostrarPokemones);
          gbc.gridx = i % 3;
          gbc.gridy = i / 3;
          panelPokemones.add(carta, gbc);
        }

        add(panelPokemones, BorderLayout.CENTER);
      }

      panelPokemones.revalidate();
      panelPokemones.repaint();
      revalidate();
      repaint();
    }
  }
}
