package com.pokegame.app.gui;

import com.pokegame.app.modelo.*;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.util.CartaPokemon;
import com.pokegame.app.util.VerificarSesion;

import javax.swing.*;
import java.awt.*;

public class CartaEquipo extends CartaPokemon {

    public CartaEquipo(Pokemon pokemon, Imagen imagen, String equipoNombre, Runnable refrescarCallback) {
        super(pokemon, imagen);

        if (VerificarSesion.isLoggedIn()) {
            JButton botonEliminar = new JButton("-");
            botonEliminar.setMargin(new Insets(2, 6, 2, 6));
            botonEliminar.setFont(new Font("Arial", Font.BOLD, 11));
            botonEliminar.setBounds(120, 5, 25, 25);
            botonEliminar.setFocusable(false);
            botonEliminar.setBackground(new Color(240, 240, 240));
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
                    EquipoRepositoryImpl repo = new EquipoRepositoryImpl();
                    int idEquipo = repo.obtenerIdEquipoPorNombre(equipoNombre);
                    if (repo.eliminarPokemonDeEquipo(idEquipo, pokemon.getId())) {
                        JOptionPane.showMessageDialog(this, pokemon.getNombre() + " eliminado del equipo.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        refrescarCallback.run();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }
}