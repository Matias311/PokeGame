package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.repository.EquiposRepository;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EquipoGui extends JPanel {

    private JComboBox<String> comboEquipos;
    private JPanel panelPokemones;
    private JLabel labelSinEquipos;
    private JLabel labelSinPokemones;
    private JButton botonBorrar;
    private JButton botonCambiarNombre;
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

        panelPokemones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        add(panelPokemones, BorderLayout.CENTER);

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
                for (Pokemon pokemon : pokemones) {
                    Imagen imagen = imagenRepo.buscarImagenPorIdPortadaPokemon(pokemon.getId());
                    panelPokemones.add(new CartaPokemon(pokemon, imagen));
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
        String nombre = JOptionPane.showInputDialog(this, "Ingresa el nombre del nuevo equipo:", "Nuevo Equipo", JOptionPane.PLAIN_MESSAGE);
        if (nombre != null && !nombre.trim().isEmpty()) {
            nombre = nombre.trim();
            Equipo nuevo = new Equipo(0, nombre, 1); // Cliente fijo con id 1
            if (equipoRepository.crearEquipo(nuevo)) {
                JOptionPane.showMessageDialog(this, "Equipo creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarEquipos();
                comboEquipos.setSelectedItem(nombre);
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear equipo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void borrarEquipo() {
        String nombre = (String) comboEquipos.getSelectedItem();
        if (nombre != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas borrar el equipo '" + nombre + "'?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (equipoRepository.eliminarEquipoPorNombre(nombre)) {
                    JOptionPane.showMessageDialog(this, "Equipo eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarEquipos();
                    if (comboEquipos.getItemCount() > 0) {
                        comboEquipos.setSelectedIndex(0);
                        mostrarPokemones();
                    } else {
                        panelPokemones.removeAll();
                        panelPokemones.revalidate();
                        panelPokemones.repaint();
                        add(labelSinEquipos, BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar equipo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void cambiarNombreEquipo() {
        String nombreActual = (String) comboEquipos.getSelectedItem();
        if (nombreActual != null && !nombreActual.equals("Sin equipos")) {
            String nuevoNombre = JOptionPane.showInputDialog(this,
                    "Ingresa el nuevo nombre para el equipo:",
                    "Cambiar nombre del equipo",
                    JOptionPane.PLAIN_MESSAGE);

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                nuevoNombre = nuevoNombre.trim();
                if (equipoRepository.actualizarNombreEquipo(nombreActual, nuevoNombre)) {
                    JOptionPane.showMessageDialog(this,
                            "Nombre del equipo actualizado con éxito.",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    cargarEquipos();
                    comboEquipos.setSelectedItem(nuevoNombre);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al actualizar el nombre del equipo.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un equipo válido para cambiar su nombre.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}