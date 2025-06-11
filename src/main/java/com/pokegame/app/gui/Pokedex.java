package com.pokegame.app.gui;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;
import com.pokegame.app.repository.implementacion.ImagenRepositoryImpl;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;
import com.pokegame.app.modelo.Imagen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
      Imagen imagen = imagenServices.buscarImagenPorIdPortadaPokemon(pokemon.getId());
      contenedorPokemon.add(new CartaPokemon(pokemon, imagen) {
        {
          // Solo mostrar el botón si el usuario está logeado
          if (com.pokegame.app.util.VerificarSesion.isLoggedIn()) {
            JButton botonAgregar = new JButton("+");
            botonAgregar.setMargin(new Insets(2, 6, 2, 6));
            botonAgregar.setFont(new Font("Arial", Font.BOLD, 11));
            botonAgregar.setBounds(120, 5, 25, 25);
            botonAgregar.setFocusable(false);
            botonAgregar.setBackground(new Color(240, 240, 240)); // Gris casi blanco
            botonAgregar.setForeground(new Color(100, 100, 100)); // gris

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
          for (Equipo equipo : equipos) {
            combo.addItem(equipo.getNombre());
          }

          JButton botonAgregar = new JButton("Agregar al equipo");
          botonAgregar.setEnabled(false);

          combo.addActionListener(e -> {
            botonAgregar.setEnabled(combo.getSelectedIndex() != 0);
          });

          botonAgregar.addActionListener(e -> {
            String nombreEquipo = (String) combo.getSelectedItem();
            int idEquipo = equipoRepo.obtenerIdEquipoPorNombre(nombreEquipo);

            List<Pokemon> actuales = equipoRepo.obtenerPokemonesDeEquipo(nombreEquipo);

            boolean yaEsta = actuales.stream().anyMatch(p -> p.getId() == pokemon.getId());
            if (yaEsta) {
              JOptionPane.showMessageDialog(null, pokemon.getNombre() + " ya está en el equipo.");
              return;
            }

            if (actuales.size() >= 6) {
              JOptionPane.showMessageDialog(null, "El equipo ya tiene 6 Pokémon.");
              return;
            }

            if (equipoRepo.agregarPokemonAEquipo(idEquipo, pokemon.getId())) {
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
      });
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
