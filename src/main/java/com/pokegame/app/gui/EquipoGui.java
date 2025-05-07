package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;
import com.pokegame.app.modelo.Imagen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class EquipoGui extends JPanel {

    private JComboBox<String> comboEquipos;
    private JPanel panelPokemones;
    private final EquipoRepositoryImpl repo;

    public EquipoGui() {
        setLayout(new BorderLayout());
        repo = new EquipoRepositoryImpl();

        comboEquipos = new JComboBox<>();
        comboEquipos.setPreferredSize(new Dimension(200, 25));
        cargarEquipos();
        comboEquipos.addActionListener(e -> mostrarPokemones());

        JLabel labelSeleccion = new JLabel("Equipo: ");
        labelSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCombo.add(labelSeleccion);
        panelCombo.add(comboEquipos);
        add(panelCombo, BorderLayout.NORTH);

        JButton botonBorrar = new JButton("Borrar equipo");
        botonBorrar.setPreferredSize(new Dimension(150, 30));
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(botonBorrar);
        add(panelBoton, BorderLayout.SOUTH);

        panelPokemones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JScrollPane scrollPane = new JScrollPane(panelPokemones);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        if (comboEquipos.getItemCount() > 0) {
            comboEquipos.setSelectedIndex(0);
            mostrarPokemones();
        }
    }

    private void cargarEquipos() {
        List<Equipo> equipos = repo.obtenerNombresEquipos();
        for (Equipo equipo : equipos) {
            comboEquipos.addItem(equipo.getNombre());
        }
    }

    private void mostrarPokemones() {
        panelPokemones.removeAll();
        String equipoSeleccionado = (String) comboEquipos.getSelectedItem();
        if (equipoSeleccionado != null) {
            List<Pokemon> pokemones = repo.obtenerPokemonesDeEquipo(equipoSeleccionado);
            ImagenRepositoryImpl imagenRepo = new ImagenRepositoryImpl();
            for (Pokemon pokemon : pokemones) {
                Imagen imagen = imagenRepo.buscarImagenPorIdPortadaPokemon(pokemon.getId());
                panelPokemones.add(new CartaPokemon(pokemon, imagen));
            }
            panelPokemones.revalidate();
            panelPokemones.repaint();
        }
    }
}