package com.pokegame.app.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/** InicioGui. */
public class InicioGui extends JFrame {

  /** Crea la gui inicial, aqui se agregaran la gui de pokedex, equipo, multiplayer. */
  public InicioGui() {
    setTitle("PokeGame");
    setSize(600, 600);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("PokeDex", new Pokedex());

    add(tabbedPane);
    setVisible(true);
  }
}
