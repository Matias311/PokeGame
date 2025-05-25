package com.pokegame.app.gui;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.util.VerificarSesion;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Perfil. */
public class Perfil extends JPanel {

  private Cliente CLIENTE = VerificarSesion.getCliente();
  private InicioGui padre;

  /** Crea la interfaz del perfil. */
  public Perfil(InicioGui gui) {
    this.padre = gui;
    setLayout(new GridLayout(2, 1, 5, 5));

    // Label del usuario
    JLabel nombreUsuario = new JLabel(CLIENTE.getNombreUsuario());
    add(nombreUsuario);

    // Boton de cerrar sesion
    JButton logout = new JButton("Cerrar sesion");
    logout.addActionListener(
        e -> {
          VerificarSesion.logout();
          padre.actualizarInterfaz();
        });
    add(logout);
  }
}
