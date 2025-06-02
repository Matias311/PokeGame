package com.pokegame.app.gui;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

  /** Crea la tab de la pokedex, donde se muestran los pokemons, con filtros. */
  public Pokedex() {
    setLayout(new BorderLayout());
    setSize(900, 800);

    // Panel North, seccion busqueda, filtro
    JPanel panelNav = new JPanel();
    barraBusqueda = new JTextField();
    barraBusqueda.setPreferredSize(new Dimension(200, 20));
    panelNav.add(barraBusqueda);

    JButton btnBuscar = new JButton("Buscar");
    // Filtro de buscar por nombre
    btnBuscar.addActionListener(e -> filtrarNombre(barraBusqueda.getText()));
    panelNav.add(btnBuscar);

    JComboBox<String> dropdown =
        new JComboBox<String>(
            new String[] {
              "Seleccione ordenamiento",
              "Ordenar por nombre DESC",
              "Ordenar por id DESC",
              "Ordenar por ataque DESC"
            });
    panelNav.add(dropdown);

    add(panelNav, BorderLayout.NORTH);
    // Panel de cartas pokemon
    listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
    rePintarContenedor(listaPokemon);

    // botones de adelante y retroceder
    JPanel btnPanel = new JPanel();

    btnRetroceder = new JButton("Retroceder");
    btnRetroceder.setEnabled(false);
    btnPanel.add(btnRetroceder);
    btnRetroceder.addActionListener(
        e -> {
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
    btnAdelante.addActionListener(
        e -> {
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
      // TODO: Poner nueva clase y hacerla especifica
      contenedorPokemon.add(
          new CartaPokemon(
              pokemon, imagenServices.buscarImagenPorIdPortadaPokemon(pokemon.getId())));
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
