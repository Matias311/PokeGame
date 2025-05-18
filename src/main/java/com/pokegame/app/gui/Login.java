package com.pokegame.app.gui;

import com.pokegame.app.util.VerficarSesion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/** Panel de login con diseño mejorado y lógica completa para autenticación. */
public class Login extends JPanel {
  private static final int FIELD_WIDTH = 250;
  private static final int FIELD_HEIGHT = 35;
  private static final int PADDING = 30;
  private static final int GAP = 15;

  private JTextField usernameField;
  private JTextField passwordField;
  private JButton loginButton;
  private JButton registerButton;
  private VerficarSesion servicesSesion;
  private InicioGui padre;

  public Login(InicioGui iniciogui) {
    this.padre = iniciogui;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
    setBackground(new Color(240, 240, 240));

    add(createTitlePanel());
    add(Box.createVerticalStrut(GAP));
    add(createUsernamePanel());
    add(Box.createVerticalStrut(GAP));
    add(createPasswordPanel());
    add(Box.createVerticalStrut(GAP * 2));
    add(createLoginButton());
    add(Box.createVerticalStrut(GAP * 2));
    add(createAccountCreationPanel());
  }

  private JPanel createTitlePanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(240, 240, 240));

    JLabel title = new JLabel("Inicio de Sesión");
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setForeground(new Color(50, 50, 50));

    panel.add(title);
    return panel;
  }

  private JPanel createUsernamePanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBackground(new Color(240, 240, 240));

    JLabel label = new JLabel("Usuario:");
    label.setPreferredSize(new Dimension(100, FIELD_HEIGHT));

    usernameField = createStyledTextField();

    panel.add(label);
    panel.add(usernameField);
    return panel;
  }

  private JPanel createPasswordPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBackground(new Color(240, 240, 240));

    JLabel label = new JLabel("Contraseña:");
    label.setPreferredSize(new Dimension(100, FIELD_HEIGHT));

    passwordField = new JPasswordField();
    passwordField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    passwordField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));

    panel.add(label);
    panel.add(passwordField);
    return panel;
  }

  private JButton createLoginButton() {
    loginButton = new JButton("INICIAR SESIÓN");
    loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginButton.setPreferredSize(new Dimension(FIELD_WIDTH + 110, FIELD_HEIGHT + 10));
    loginButton.setMaximumSize(new Dimension(FIELD_WIDTH + 110, FIELD_HEIGHT + 10));
    loginButton.setFont(new Font("Arial", Font.BOLD, 14));
    loginButton.setFocusPainted(false);

    // Verificacion del login
    loginButton.addActionListener(
        e -> {
          String nombre = usernameField.getText();
          String password = passwordField.getText();
          boolean iniciado = VerficarSesion.login(nombre, password);
          if (iniciado) {
            SwingUtilities.invokeLater(
                () -> {
                  padre.actualizarInterfaz();
                });
            JOptionPane.showMessageDialog(null, "Se ha iniciado sesion correctamente");
          } else {
            // TODO: Hacer que si me equivoco limpiar los inputs
            JOptionPane.showMessageDialog(null, "Error, verifica los datos");
          }
        });

    return loginButton;
  }

  private JPanel createAccountCreationPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(240, 240, 240));

    JLabel label = new JLabel("¿No tienes una cuenta?");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setForeground(new Color(100, 100, 100));

    registerButton = new JButton("Regístrate aquí");
    registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    registerButton.setFont(new Font("Arial", Font.PLAIN, 12));
    registerButton.setForeground(new Color(70, 130, 180));
    registerButton.setBackground(new Color(240, 240, 240));
    registerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    registerButton.setContentAreaFilled(false);
    registerButton.setFocusPainted(false);
    registerButton.addActionListener(e -> new RegistroCliente());

    panel.add(label);
    panel.add(Box.createVerticalStrut(5));
    panel.add(registerButton);

    return panel;
  }

  private JTextField createStyledTextField() {
    JTextField field = new JTextField();
    field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    return field;
  }
}
