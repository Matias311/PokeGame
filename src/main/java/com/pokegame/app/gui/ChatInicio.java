package com.pokegame.app.gui;

import com.pokegame.app.manager.EquipoManager;
import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Mensaje;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.MensajeRepositoryImpl;
import com.pokegame.app.util.SerializarEquipo;
import com.pokegame.app.util.VerificarSesion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/** ChatInicio. */
public class ChatInicio extends JPanel {

  private Socket socket;
  private PrintWriter writer;
  private BufferedReader reader;
  private JTextField mensaje;
  private JButton send;
  private JButton share;
  private EquipoManager managerEquipo = new EquipoManager();
  private JTextPane textpane;
  StyledDocument doc;
  private final MensajeRepositoryImpl mensajeRepository = new MensajeRepositoryImpl();

  /** Inicio de ChatInicio. */
  public ChatInicio() {
    setLayout(new BorderLayout());

    textpane = new JTextPane();
    doc = textpane.getStyledDocument();
    add(BorderLayout.CENTER, textpane);

    // seccion para escribir
    JPanel bottomPanel = new JPanel();
    bottomPanel.setBackground(new Color(179, 179, 179));

    share = new JButton("SHARE");
    bottomPanel.add(share);
    share.addActionListener(e -> mostrarSeleccionEquipo());

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
                    Mensaje msj =
                        new Mensaje(
                            tipo,
                            textoMensaje,
                            new Cliente(Integer.parseInt(idRemitente), remitente));
                    // Verificar si el mensaje es mio
                    boolean esPropio =
                        msj.getCliente().getId() == VerificarSesion.getCliente().getId();

                    // Background color para los mensajes
                    Color color = esPropio ? Color.BLUE : Color.GRAY;

                    // Añadir al modelo en el hilo de Swing
                    SwingUtilities.invokeLater(
                        () -> {
                          if (msj.getTipoMensaje().equals("mensaje")) {
                            insertarMensaje(msj, esPropio, color);
                          } else if (msj.getTipoMensaje().equals("equipo")) {
                            insertarEquipoMensaje(msj, esPropio, color);
                          }
                        });
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
      Mensaje msj = new Mensaje("mensaje", texto, VerificarSesion.getCliente());
      mensajeRepository.guardarMensaje(msj);
    }
  }

  private void mostrarSeleccionEquipo() {
    JDialog ventana = new JDialog();
    ventana.setTitle("Lista de equipos");
    ventana.setSize(300, 200);
    ventana.setLayout(new FlowLayout());

    // Dropdown con los nombres de los equipos
    List<Equipo> nombresEquipos = managerEquipo.obtenerEquipos();
    JComboBox<String> dropdown = new JComboBox<>();
    dropdown.setPreferredSize(new Dimension(300, 30));

    if (nombresEquipos.size() >= 1) {
      for (Equipo equipo : nombresEquipos) {
        dropdown.addItem(equipo.getNombre());
      }
    } else {
      dropdown.addItem("No se encontraron equipos");
    }

    // Boton de cancelar
    JButton cancelarBtn = new JButton("Cancelar");
    cancelarBtn.addActionListener(e -> ventana.dispose());

    // Boton Aceptar
    JButton aceptarBtn = new JButton("Enviar");
    aceptarBtn.addActionListener(
        e -> {
          sendEquipo((String) dropdown.getSelectedItem());
          ventana.dispose();
        });

    ventana.add(dropdown);
    ventana.add(aceptarBtn);
    ventana.add(cancelarBtn);
    ventana.setVisible(true);
  }

  private void sendEquipo(String nombreEquipo) {
    // Crear equipo y serializarlo (asi se puede mandar)
    List<Pokemon> lista = managerEquipo.obtenerPokemones(nombreEquipo);

    Equipo equipo = new Equipo(nombreEquipo, lista);
    String msj = SerializarEquipo.serializarEquipo(equipo);

    Mensaje mensaje = new Mensaje("equipo", msj, VerificarSesion.getCliente());
    mensajeRepository.guardarMensaje(mensaje);

    writer.println("equipo" + ":" + msj);
  }

  private void agregarNuevoEquipo(Equipo equipo) {
    boolean creado = managerEquipo.crearEquipo(equipo.getNombre());
    if (creado) {
      int idEquipo = managerEquipo.obtenerIdPorNombre(equipo.getNombre());
      List<Pokemon> listaPokemon = equipo.getListaPokemon();

      for (Pokemon pokemon : listaPokemon) {
        try {
          managerEquipo.agregarPokemonEquipo(idEquipo, pokemon.getId());
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
    JOptionPane.showConfirmDialog(null, "El equipo se ha agregado correctamente");
  }

  private void insertarMensaje(Mensaje msj, boolean mio, Color background) {
    try {
      // Crear un panel para contener el mensaje
      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
      panel.setBackground(background);

      // Crear etiqueta con el texto
      JLabel label = new JLabel(msj.getCliente().getNombreUsuario() + ": " + msj.getMensaje());
      label.setForeground(Color.WHITE);
      label.setHorizontalAlignment(mio ? SwingConstants.RIGHT : SwingConstants.LEFT);
      panel.add(label, BorderLayout.CENTER);

      // Insertar el panel en el JTextPane
      Style style = doc.addStyle("panelstyle", null);
      StyleConstants.setAlignment(
          style, mio ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);

      textpane.insertComponent(panel);
      doc.insertString(doc.getLength(), "\n", style);

      textpane.setCaretPosition(doc.getLength());
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  private void insertarEquipoMensaje(Mensaje msj, boolean mio, Color bacColor) {
    try {
      Equipo equipo = SerializarEquipo.deserializarEquipo(msj.getMensaje());

      // Panel base
      JPanel panel = new JPanel(new BorderLayout());
      panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
      panel.setBackground(bacColor);

      // Texto
      String mensaje =
          String.format(
              "%s : ha compartido su equipo %s",
              msj.getCliente().getNombreUsuario(), equipo.getNombre());
      JLabel label = new JLabel(mensaje);
      label.setHorizontalAlignment(mio ? SwingConstants.RIGHT : SwingConstants.LEFT);
      label.setForeground(Color.WHITE);
      panel.add(label, BorderLayout.CENTER);

      // Agregar boton de agregar equipo
      JButton btn = new JButton("+");
      btn.addActionListener(e -> agregarNuevoEquipo(equipo));
      panel.add(btn, BorderLayout.EAST);

      // Agregar el componente
      textpane.insertComponent(panel);
      doc.insertString(doc.getLength(), "\n", null);
      textpane.setCaretPosition(doc.getLength());

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
