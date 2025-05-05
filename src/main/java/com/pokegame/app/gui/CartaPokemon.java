package com.pokegame.app.gui;

import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.util.ImagenCache;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
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
    setOpaque(false);
    // Nombre del pokemon
    JLabel nombrePokemon = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
    nombrePokemon.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
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
    // Eventos del mouse, cambio de mouse y escuchar el evento al hacer click para mostrar la nueva
    // GUI
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          }

          // Crea la ventana con la informacion del pokemon
          @Override
          public void mouseClicked(MouseEvent e) {
            new InformacionPokemon(pokemon.getId(), imagen.getImagenFrente());
          }
        });
  }

  // Creacion sombra carta del pokemon
  @Override
  protected void paintComponent(Graphics g) {
    int shadowSize = 2;
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(new Color(231, 232, 227));
    g2.fillRoundRect(
        shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
    g2.dispose();
    super.paintComponent(g);
  }
}
