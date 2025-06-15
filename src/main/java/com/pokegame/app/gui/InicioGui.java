package com.pokegame.app.gui;

import com.pokegame.app.serverChat.ServerChat;
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

      // inicio de thread servidor
      Runnable serverRunnable = new ServerChat();
      Thread server = new Thread(serverRunnable);
      server.start();

      tabbedPane.addTab("Equipos", new EquipoGui());
      tabbedPane.addTab("Chat", new ChatInicio());
      tabbedPane.addTab("Perfil", new Perfil(this));

    } else {

      tabbedPane.addTab("Iniciar Sesión", new Login(this));
    }

    tabbedPane.revalidate();
    tabbedPane.repaint();
  }
}
