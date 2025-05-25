package com.pokegame.app.gui;

import com.pokegame.app.util.VerificarSesion;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/** InicioGui. */
public class InicioGui extends JFrame {
  private JTabbedPane tabbedPane;

  /** Punto de entrada de la app. */
  public InicioGui() {
    setTitle("PokeGame");
    setSize(900, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    tabbedPane = new JTabbedPane();
    actualizarInterfaz();

    add(tabbedPane);
    setVisible(true);
  }

  /**
   * Actualiza las tab, para asi poder mostrar solamente la seccion de equipos y las de un futuro
   * cuando este logeado.
   */
  public void actualizarInterfaz() {
    tabbedPane.removeAll();

    tabbedPane.addTab("PokeDex", new Pokedex());

    if (VerificarSesion.isLoggedIn()) {
      tabbedPane.addTab("Equipos", new EquipoGui());
      // TODO: Hacer parte de perfil
      // TODO: Hacer parte de cerrar sesion
    } else {
      tabbedPane.addTab("Iniciar Sesión", new Login(this));
    }

    tabbedPane.revalidate();
    tabbedPane.repaint();
  }
}
