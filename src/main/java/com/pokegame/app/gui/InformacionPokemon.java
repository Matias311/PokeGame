package com.pokegame.app.gui;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;
import com.pokegame.app.util.ImagenCache;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** InformacionPokemon. */
public class InformacionPokemon extends JFrame {

  /**
   * Se le pasa el id del pokemon para poder encontrar la Informacion y la Imagen para buscarla en
   * el cache.
   *
   * @param idPokemon int
   * @param urlImagen String
   */
  public InformacionPokemon(int idPokemon, String urlImagen) {
    setSize(900, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(3, 1, 5, 10));
    // Seccion nombre, stats, imagen
    JPanel seccionNombreStats = new JPanel();
    // Imagen
    JLabel imagen = new JLabel();
    seccionNombreStats.add(imagen);
    ImagenCache.getImage(
        urlImagen,
        imagenIcon -> {
          imagen.setIcon(imagenIcon);
          imagen.setHorizontalAlignment(JLabel.CENTER);
          imagen.setVerticalAlignment(JLabel.CENTER);
          imagen.revalidate();
          imagen.repaint();
        });
    // Seccion nombre + stats
    Pokemon pokemon = new PokemonRepositoryImpl().traerPokemonId(idPokemon);
    JPanel panelNombreStats = new JPanel();
    panelNombreStats.setPreferredSize(new Dimension(450, 200));
    JLabel labelNombre = new JLabel(pokemon.getNombre());
    panelNombreStats.add(labelNombre);
    // tabla de estadisticas
    seccionNombreStats.add(panelNombreStats);

    add(seccionNombreStats);
    setVisible(true);
  }
}
