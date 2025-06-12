package com.pokegame.app.gui;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/** Pokedex. */
public class Pokedex extends JPanel {
  private JPanel contenedorPokemon = new JPanel();
  private static int inicio = 0;
  private final int CANT = 20;
  private PokemonRepositoryImpl pokemonServices = new PokemonRepositoryImpl();
  private List<Pokemon> listaPokemon;
  private JButton btnRetroceder;
  private JButton btnAdelante;
  private JTextField barraBusqueda;

  public Pokedex() {
    setLayout(new BorderLayout());
    setSize(900, 800);

    JPanel panelNav = new JPanel();
    barraBusqueda = new JTextField();
    barraBusqueda.setPreferredSize(new Dimension(200, 20));
    panelNav.add(barraBusqueda);

    JButton btnBuscar = new JButton("Buscar");
    btnBuscar.addActionListener(e -> filtrarNombre(barraBusqueda.getText()));
    panelNav.add(btnBuscar);

    JComboBox<String> dropdown = new JComboBox<>(
            new String[] {
                    "Seleccione ordenamiento",
                    "Ordenar por nombre DESC",
                    "Ordenar por id DESC",
                    "Ordenar por ataque DESC"
            }
    );
    panelNav.add(dropdown);

    add(panelNav, BorderLayout.NORTH);
    listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
    rePintarContenedor(listaPokemon);

    JPanel btnPanel = new JPanel();
    btnRetroceder = new JButton("Retroceder");
    btnRetroceder.setEnabled(false);
    btnPanel.add(btnRetroceder);

    btnRetroceder.addActionListener(e -> {
      inicio -= 20;
      if (inicio <= 0) {
        inicio = 0;
        btnRetroceder.setEnabled(false);
      }
      if (inicio < 131) {
        btnAdelante.setEnabled(true);
      }
      listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
      rePintarContenedor(listaPokemon);
    });

    btnAdelante = new JButton("Adelante");
    btnPanel.add(btnAdelante);
    btnAdelante.addActionListener(e -> {
      inicio += 20;
      if (inicio >= 131) {
        inicio = 131;
        btnAdelante.setEnabled(false);
      }
      if (inicio > 0) {
        btnRetroceder.setEnabled(true);
      }
      listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
      rePintarContenedor(listaPokemon);
    });

    add(btnPanel, BorderLayout.SOUTH);
  }

  private void rePintarContenedor(List<Pokemon> lista) {
    remove(contenedorPokemon);
    contenedorPokemon = pintarCartas(lista);
    add(contenedorPokemon, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private JPanel pintarCartas(List<Pokemon> lista) {
    contenedorPokemon = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    remove(contenedorPokemon);
    ImagenRepositoryImpl imagenServices = new ImagenRepositoryImpl();

    for (Pokemon pokemon : lista) {
      Imagen imagen = imagenServices.buscarImagenPorIdPortadaPokemon(pokemon.getId());
      contenedorPokemon.add(new CartaPokedex(pokemon, imagen));
    }

    contenedorPokemon.repaint();
    contenedorPokemon.revalidate();
    return contenedorPokemon;
  }

  private void filtrarNombre(String nombre) {
    if (nombre.length() > 0) {
      listaPokemon = pokemonServices.traerPokemonNombre(nombre);
      rePintarContenedor(listaPokemon);
      barraBusqueda.setText("");
      if (listaPokemon.size() <= 20) {
        btnAdelante.setEnabled(false);
        btnRetroceder.setEnabled(false);
      }
    } else {
      listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
      rePintarContenedor(listaPokemon);
      btnAdelante.setEnabled(true);
    }
  }
}

