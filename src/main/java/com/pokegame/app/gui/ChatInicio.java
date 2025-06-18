package com.pokegame.app.gui;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.modelo.Mensaje;
import com.pokegame.app.util.VerificarSesion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/** ChatInicio. */
public class ChatInicio extends JPanel {

  private DefaultListModel<Mensaje> modelo;
  private Socket socket;
  private PrintWriter writer;
  private BufferedReader reader;
  private JTextField mensaje;
  private JButton send;

  /** Inicio de ChatInicio. */
  public ChatInicio() {
    setLayout(new BorderLayout());
    JList<Mensaje> list = new JList<>();
    modelo = new DefaultListModel<>();
    list.setModel(modelo);

    list.setCellRenderer(
        new DefaultListCellRenderer() {
          @Override
          public Component getListCellRendererComponent(
              JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label =
                (JLabel)
                    super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

            Mensaje msj = (Mensaje) value;
            label.setText(msj.getCliente().getNombreUsuario() + ": " + msj.getMensaje());
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Alinear a la derecha si es propio, izquierda si es de otro (por id del cliente)
            boolean esPropio = msj.getCliente().getId() == VerificarSesion.getCliente().getId();
            if (esPropio) { // Mensaje propio (derecha)
              label.setBackground(new Color(212, 237, 218)); // Color verde claro
              label.setHorizontalAlignment(SwingConstants.RIGHT);
            } else { // Mensaje de otro (izquierda)
              label.setBackground(new Color(255, 238, 186)); // Color amarillo claro
              label.setHorizontalAlignment(SwingConstants.LEFT);
            }

            return label;
          }
        });

    add(BorderLayout.CENTER, list);

    // seccion para escribir
    JPanel bottomPanel = new JPanel();
    bottomPanel.setBackground(new Color(179, 179, 179));

    JButton share = new JButton("SHARE");
    bottomPanel.add(share);

    mensaje = new JTextField();
    mensaje.setPreferredSize(new Dimension(400, 30));
    bottomPanel.add(mensaje);

    send = new JButton("SEND");
    send.addActionListener(e -> sendMensaje());
    bottomPanel.add(send);
    add(BorderLayout.SOUTH, bottomPanel);
    conexionServidor();
    setVisible(true);
  }

  private void conexionServidor() {
    try {
      socket = new Socket("localhost", 3131);
      writer = new PrintWriter(socket.getOutputStream(), true);
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // Enviar el nombre de usuario al servidor (lo primero que se envía)
      Cliente clienteLocal = VerificarSesion.getCliente();
      writer.println(
          clienteLocal.getNombreUsuario()
              + ":"
              + clienteLocal.getId()); // Envía el nombre al servidor

      // Hilo para recibir mensajes
      new Thread(
              () -> {
                try {
                  String mensajeRecibido;
                  while ((mensajeRecibido = reader.readLine()) != null) {
                    // Separar el usuario del mensaje (formato "tipo:usuario:id:mensaje")
                    String[] partes = mensajeRecibido.split(":", 4);
                    String tipo = partes[0];
                    String remitente = partes[1];
                    String idRemitente = partes[2];
                    String textoMensaje = partes[3];

                    // Crear el objeto Mensaje
                    if (tipo.equals("mensaje")) {
                      Mensaje msj =
                          new Mensaje(
                              textoMensaje, new Cliente(Integer.parseInt(idRemitente), remitente));

                      // Añadir al modelo en el hilo de Swing
                      SwingUtilities.invokeLater(
                          () -> {
                            modelo.addElement(msj);
                          });
                    }
                  }
                } catch (Exception e) {
                  System.out.println("Error al recibir mensaje: " + e.getMessage());
                }
              })
          .start();

    } catch (Exception e) {
      System.err.println("Error de conexión: " + e.getMessage());
    }
  }

  private void sendMensaje() {
    String texto = mensaje.getText().trim();
    if (!texto.isEmpty()) {
      writer.println("mensaje" + ":" + texto); // Envía solo el texto (el servidor añade el usuario)
      mensaje.setText(""); // Limpia el campo de texto
    }
  }
}
