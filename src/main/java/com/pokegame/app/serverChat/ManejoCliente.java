package com.pokegame.app.serverChat;

import com.pokegame.app.modelo.Mensaje;
import com.pokegame.app.repository.implementacion.MensajeRepositoryImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** ManejoCliente. */
public class ManejoCliente implements Runnable {
  private Socket clientSocket;
  private PrintWriter out;
  private static List<ManejoCliente> clientes = new ArrayList<>();
  private final MensajeRepositoryImpl mensajeRepository = new MensajeRepositoryImpl();

  /**
   * Crea el socket del cliente y espera para guardar el cliente en la lista.
   *
   * @param socket Socket
   */
  public ManejoCliente(Socket socket) {
    this.clientSocket = socket;
    synchronized (clientes) {
      clientes.add(this);
    }
  }

  @Override
  public void run() {
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
      out = new PrintWriter(clientSocket.getOutputStream(), true);

      // Obtener el nombre de usuario del cliente (si lo envía al conectarse)
      String[] primerMensaje = in.readLine().split(":", 2);
      String nombreUsuario = primerMensaje[0]; // nombre
      int idUsuario = Integer.parseInt(primerMensaje[1]);
      System.out.println("Cliente conectado: " + nombreUsuario);

      // Obtener los mensajes de la base de datos
      List<Mensaje> lista = mensajeRepository.traerTodosLosMensajes();
      for (Mensaje mensaje : lista) {
        String mensajeCompleto =
            mensaje.getTipoMensaje()
                + ":"
                + mensaje.getCliente().getNombreUsuario()
                + ":"
                + mensaje.getCliente().getId()
                + ":"
                + mensaje.getMensaje();
        for (ManejoCliente cliente : clientes) {
          cliente.out.println(mensajeCompleto);
        }
      }

      // Obtiene los mensajes
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        // Formato: "tipo:usuario:id:contenido"
        String[] contenidoMsj =
            inputLine.split(":", 2); // solamente se le pasa el tipo y el contenido
        String mensajeCompleto = null;
        if (contenidoMsj.length == 2) {
          mensajeCompleto =
              contenidoMsj[0] + ":" + nombreUsuario + ":" + idUsuario + ":" + contenidoMsj[1];
        }

        // Enviar a todos los clientes (broadcast)
        if (mensajeCompleto != null) {
          synchronized (clientes) {
            for (ManejoCliente cliente : clientes) {
              cliente.out.println(mensajeCompleto);
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println(e);
    } finally {
      synchronized (clientes) {
        clientes.remove(this);
      }
      try {
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("Error al cerrar socket: " + e.getMessage());
      }
    }
  }
}
