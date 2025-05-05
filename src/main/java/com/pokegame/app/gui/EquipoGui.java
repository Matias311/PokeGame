package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EquipoGui extends JPanel {

    private JComboBox<String> comboEquipos;
    private JPanel panelPokemones;
    private EquipoRepositoryImpl repo;

    public EquipoGui() {

        setLayout(new BorderLayout());
        repo = new EquipoRepositoryImpl();

        comboEquipos = new JComboBox<>();
        comboEquipos.setPreferredSize(new Dimension(200, 25));
        cargarEquipos();

        //comboEquipos.addActionListener(e -> mostrarPokemones());

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
        add(panelPokemones, BorderLayout.CENTER);

        if (comboEquipos.getItemCount() > 0) {
            comboEquipos.setSelectedIndex(0);
            //mostrarPokemones();
        }
    }

    private void cargarEquipos() {
        List<Equipo> equipos = repo.obtenerNombresEquipos();
        for (Equipo equipo : equipos) {
            comboEquipos.addItem(equipo.getNombre());
        }
    }

/*
 *     private void mostrarPokemones() {
        panelPokemones.removeAll();
        String equipoSeleccionado = (String) comboEquipos.getSelectedItem();
        if (equipoSeleccionado != null) {
            List<Pokemon> pokemones = repo.obtenerPokemonesDeEquipo(equipoSeleccionado);
            pokemones.forEach(pokemon -> panelPokemones.add(new CartaPokemon(pokemon, new ImagenRepositoryImpl().buscarImagenPorIdPortadaPokemon(pokemon.getId()))));
            panelPokemones.revalidate();
            panelPokemones.repaint();
        }
    }
 */

}