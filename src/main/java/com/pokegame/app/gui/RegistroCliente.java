package com.pokegame.app.gui;

import com.pokegame.app.util.VerificarSesion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/** Frame para el registro de nuevos clientes con diseño consistente al login. */
public class RegistroCliente extends JFrame {
  private static final int FIELD_WIDTH = 250;
  private static final int FIELD_HEIGHT = 35;
  private static final int PADDING = 30;
  private static final int GAP = 15;

  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton registerButton;
  private JButton cancelButton;

  /** Crea la guia de Registrar, aqui el usuariario puede crear su cuenta. */
  public RegistroCliente() {
    configureFrame();
    initComponents();
    pack();
  }

  private void configureFrame() {
    setTitle("Registro de Nuevo Cliente");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);
    setVisible(true);
  }

  private void initComponents() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
    mainPanel.setBackground(new Color(240, 240, 240));

    // Título
    JLabel title = new JLabel("Registro de Nuevo Usuario");
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setForeground(new Color(50, 50, 50));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    mainPanel.add(title);
    mainPanel.add(Box.createVerticalStrut(GAP * 2));

    // Campos del formulario
    mainPanel.add(createFormFieldPanel("Usuario:", usernameField = createStyledTextField()));
    mainPanel.add(Box.createVerticalStrut(GAP));
    mainPanel.add(createFormFieldPanel("Contraseña:", passwordField = createPasswordField()));
    mainPanel.add(Box.createVerticalStrut(GAP));
    mainPanel.add(Box.createVerticalStrut(GAP * 2));

    // Panel de botones
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
    buttonPanel.setBackground(new Color(240, 240, 240));

    registerButton = createButton("REGISTRARSE", new Color(70, 130, 180));

    // Listener para crear cuenta
    registerButton.addActionListener(
        e -> {
          String username = usernameField.getText();
          String pass1 = passwordField.getText();

          boolean creada = VerificarSesion.register(username, pass1);
          if (creada) {
            // TODO: hacer que se cierre una vez se le de click al panel de opcion
            JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
          } else {
            JOptionPane.showMessageDialog(null, "Error, verifique sus datos");
          }
        });

    // TODO: hacer que al darle click a cancelar cierre este jframe
    cancelButton = createButton("CANCELAR", new Color(220, 80, 60));

    buttonPanel.add(registerButton);
    buttonPanel.add(cancelButton);

    mainPanel.add(buttonPanel);

    add(mainPanel);
  }

  private JPanel createFormFieldPanel(String labelText, JComponent field) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBackground(new Color(240, 240, 240));

    JLabel label = new JLabel(labelText);
    label.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
    label.setFont(new Font("Arial", Font.PLAIN, 14));

    panel.add(label);
    panel.add(field);

    return panel;
  }

  private JTextField createStyledTextField() {
    JTextField field = new JTextField();
    field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    field.setFont(new Font("Arial", Font.PLAIN, 14));
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    return field;
  }

  private JPasswordField createPasswordField() {
    JPasswordField field = new JPasswordField();
    field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
    field.setFont(new Font("Arial", Font.PLAIN, 14));
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    return field;
  }

  private JButton createButton(String text, Color bgColor) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(150, FIELD_HEIGHT));
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setFocusPainted(false);

    // Efecto hover
    button.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor.darker());
          }

          public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor);
          }
        });

    return button;
  }
}
