package com.pokegame.app.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** Pokedex. */
public class Pokedex extends JPanel {

  /** Crea la tab de la pokedex, donde se muestran los pokemons, con filtros. */
  public Pokedex() {
    setLayout(new BorderLayout());
    setSize(600, 600);
    // Panel North, seccion busqueda, filtro
    JPanel panelNav = new JPanel();
    JTextField barraBusqueda = new JTextField();
    barraBusqueda.setPreferredSize(new Dimension(200, 20));
    panelNav.add(barraBusqueda);

    JButton btnBuscar = new JButton("Buscar");
    panelNav.add(btnBuscar);

    JComboBox<String> dropdown =
        new JComboBox<String>(
            new String[] {
              "Seleccione ordenamiento",
              "Ordenar por nombre",
              "Ordenar por id",
              "Ordenar por ataque"
            });
    panelNav.add(dropdown);

    add(panelNav);
    // Panel de cartas pokemon

  }
}
