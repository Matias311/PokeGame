package com.pokegame.app.gui;

import com.pokegame.app.modelo.*;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.util.CartaPokemon;
import com.pokegame.app.util.VerificarSesion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartaPokedex extends CartaPokemon {

    public CartaPokedex(Pokemon pokemon, Imagen imagen) {
        super(pokemon, imagen);

        if (VerificarSesion.isLoggedIn()) {
            JButton botonAgregar = new JButton("+");
            botonAgregar.setMargin(new Insets(2, 6, 2, 6));
            botonAgregar.setFont(new Font("Arial", Font.BOLD, 11));
            botonAgregar.setBounds(120, 5, 25, 25);
            botonAgregar.setFocusable(false);
            botonAgregar.setBackground(new Color(240, 240, 240));
            botonAgregar.setForeground(new Color(100, 100, 100));

            JPanel panelBoton = new JPanel(null);
            panelBoton.setOpaque(false);
            panelBoton.setPreferredSize(new Dimension(150, 30));
            panelBoton.add(botonAgregar);
            add(panelBoton, BorderLayout.NORTH);

            botonAgregar.addActionListener(e -> mostrarPopupAgregar(pokemon));
        }
    }

    private void mostrarPopupAgregar(Pokemon pokemon) {
        EquipoRepositoryImpl equipoRepo = new EquipoRepositoryImpl();
        List<Equipo> equipos = equipoRepo.obtenerNombresEquipos();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Selecciona un Equipo");
        for (Equipo equipo : equipos) combo.addItem(equipo.getNombre());

        JButton botonAgregar = new JButton("Agregar al equipo");
        botonAgregar.setEnabled(false);

        combo.addActionListener(e -> botonAgregar.setEnabled(combo.getSelectedIndex() != 0));

        botonAgregar.addActionListener(e -> {
            String nombreEquipo = (String) combo.getSelectedItem();
            int idEquipo = equipoRepo.obtenerIdEquipoPorNombre(nombreEquipo);
            List<Pokemon> actuales = equipoRepo.obtenerPokemonesDeEquipo(nombreEquipo);

            if (actuales.stream().anyMatch(p -> p.getId() == pokemon.getId())) {
                JOptionPane.showMessageDialog(null, pokemon.getNombre() + " ya está en el equipo.");
            } else if (actuales.size() >= 6) {
                JOptionPane.showMessageDialog(null, "El equipo ya tiene 6 Pokémon.");
            } else if (equipoRepo.agregarPokemonAEquipo(idEquipo, pokemon.getId())) {
                JOptionPane.showMessageDialog(null, "Agregado con éxito al equipo " + nombreEquipo);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el Pokémon.");
            }
        });

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(combo, BorderLayout.NORTH);
        centro.add(botonAgregar, BorderLayout.SOUTH);
        panel.add(centro, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(null, panel, "Agregar Pokémon al equipo", JOptionPane.PLAIN_MESSAGE);
    }
}

