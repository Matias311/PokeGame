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
  private JPanel contenedorPokemon;
  private static int inicio = 0;
  private final int CANT = 20;
  private PokemonRepositoryImpl pokemonServices = new PokemonRepositoryImpl();

  /** Crea la tab de la pokedex, donde se muestran los pokemons, con filtros. */
  public Pokedex() {
    setLayout(new BorderLayout());
    setSize(900, 800);

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

    add(panelNav, BorderLayout.NORTH);
    // Panel de cartas pokemon
    List<Pokemon> listaPokemon = pokemonServices.traerPaginacionPokemon(inicio, CANT);
    contenedorPokemon = pintarCartas(listaPokemon);
    add(contenedorPokemon, BorderLayout.CENTER);

    // botones de adelante y retroceder
    JPanel btnPanel = new JPanel();
    JButton btnRetroceder = new JButton("Retroceder");
    if (inicio == 0 || inicio < 0) {
      btnRetroceder.setEnabled(false);
    }
    btnRetroceder.addActionListener(
        e -> {
          inicio -= 20;
          if (inicio <= 0) {
            inicio = 0;
            btnRetroceder.setEnabled(false);
          }
          rePintarContenedor(inicio, pokemonServices);
        });
    btnPanel.add(btnRetroceder);

    JButton btnAdelante = new JButton("Adelante");
    if (inicio >= 131) {
      btnAdelante.setEnabled(false);
    }
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
          rePintarContenedor(inicio, pokemonServices);
        });
    btnPanel.add(btnAdelante);
    add(btnPanel, BorderLayout.SOUTH);
  }

  private void rePintarContenedor(int inicio, PokemonRepositoryImpl pokemonServices) {
    List<Pokemon> lista = pokemonServices.traerPaginacionPokemon(inicio, CANT);
    remove(contenedorPokemon);
    contenedorPokemon = pintarCartas(lista);
    add(contenedorPokemon, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private JPanel pintarCartas(List<Pokemon> lista) {
    contenedorPokemon = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    contenedorPokemon.repaint();
    contenedorPokemon.revalidate();
    ImagenRepositoryImpl imagenServices = new ImagenRepositoryImpl();
    for (Pokemon pokemon : lista) {
      contenedorPokemon.add(
          new CartaPokemon(
              pokemon, imagenServices.buscarImagenPorIdPortadaPokemon(pokemon.getId())));
    }
    contenedorPokemon.repaint();
    contenedorPokemon.revalidate();
    return contenedorPokemon;
  }
}
