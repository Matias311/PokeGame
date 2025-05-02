package com.pokegame.app.gui;

import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;
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
    JLabel nombrePokemon = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
    nombrePokemon.setFont(new Font("Arial", Font.BOLD, 16));
    add(BorderLayout.SOUTH, nombrePokemon);
    try {
      URL url = new URL(imagen.getImagenFrente());
      ImageIcon image = new ImageIcon(url, pokemon.getNombre());
      JLabel imageLabel = new JLabel(image);
      add(BorderLayout.CENTER, imageLabel);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
