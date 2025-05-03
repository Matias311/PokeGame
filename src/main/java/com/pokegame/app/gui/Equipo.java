package com.pokegame.app.gui;

import com.pokegame.app.modelo.PokemonDTO;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class Equipo extends JPanel {

    private JComboBox<String> comboEquipos;
    private JPanel panelPokemones;
    private EquipoRepositoryImpl repo;

    public Equipo() {

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
        add(panelPokemones, BorderLayout.CENTER);

        if (comboEquipos.getItemCount() > 0) {
            comboEquipos.setSelectedIndex(0);
            mostrarPokemones();
        }
    }

    private void cargarEquipos() {
        List<String> equipos = repo.obtenerNombresEquipos();
        equipos.forEach(comboEquipos::addItem);
    }

    private void mostrarPokemones() {
        panelPokemones.removeAll();
        String equipoSeleccionado = (String) comboEquipos.getSelectedItem();
        if (equipoSeleccionado != null) {
            List<PokemonDTO> pokemones = repo.obtenerPokemonesDeEquipo(equipoSeleccionado);
            pokemones.forEach(pokemon -> panelPokemones.add(crearTarjetaPokemon(pokemon)));
            panelPokemones.revalidate();
            panelPokemones.repaint();
        }
    }

    private JPanel crearTarjetaPokemon(PokemonDTO pokemon) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(150, 200));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        try {
            URL url = new URL(pokemon.getImagenFrente());
            ImageIcon icon = new ImageIcon(url);
            tarjeta.add(new JLabel(icon), BorderLayout.CENTER);
        } catch (Exception e) {
            tarjeta.add(new JLabel("Sin imagen"), BorderLayout.CENTER);
        }

        JLabel nombre = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
        tarjeta.add(nombre, BorderLayout.SOUTH);

        return tarjeta;
    }
}