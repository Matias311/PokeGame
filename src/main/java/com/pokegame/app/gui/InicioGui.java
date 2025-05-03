package com.pokegame.app.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/** InicioGui. */
public class InicioGui extends JFrame {

  /** Crea la gui inicial, aqui se agregaran la gui de pokedex, equipo, multiplayer. */
  public InicioGui() {

    try {
      URL url = new URL("https://dinopixel.com/preload/0123/Master-Ball.png");
      ImageIcon icono = new ImageIcon(url);
      setIconImage(icono.getImage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    setTitle("PokeGame");
    setSize(600, 600);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("PokeDex", new Pokedex());
    tabbedPane.addTab("Equipo", new Equipo());

    add(tabbedPane);
    setVisible(true);
  }
}
