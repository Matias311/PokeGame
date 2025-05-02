package com.pokegame.app.gui;

import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.util.ImagenCache;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** CartaPokemon. */
public class CartaPokemon extends JPanel {

  /**
   * Obtiene el objeto pokemon, imagen y crea la carta para el menu principal.
   *
   * @param pokemon Pokemon
   * @param imagen Imagen
   */
  public CartaPokemon(Pokemon pokemon, Imagen imagen) {
    setPreferredSize(new Dimension(150, 150));
    setLayout(new BorderLayout());
    // Nombre del pokemon
    JLabel nombrePokemon = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
    nombrePokemon.setFont(new Font("Arial", Font.BOLD, 16));
    add(BorderLayout.SOUTH, nombrePokemon);
    // imagen
    try {
      JLabel imageLabel = new JLabel();
      add(BorderLayout.CENTER, imageLabel);
      // peticion de imagen al cache, carga asincrona
      ImagenCache.getImage(
          imagen.getImagenFrente(),
          imageIcon -> {
            imageLabel.setIcon(imageIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            imageLabel.revalidate();
            imageLabel.repaint();
          });
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
