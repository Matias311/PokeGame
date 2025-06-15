package com.pokegame.app.serverChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ManejoCliente implements Runnable {
  private Socket clientSocket;
  private PrintWriter out;
  private static List<ManejoCliente> clientes = new ArrayList<>();

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
      String nombreUsuario = in.readLine(); // Asume que el cliente envía su nombre primero
      System.out.println("Cliente conectado: " + nombreUsuario);

      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        // Formato: "usuario:mensaje"
        String mensajeCompleto = nombreUsuario + ":" + inputLine;

        // Enviar a todos los clientes (broadcast)
        synchronized (clientes) {
          for (ManejoCliente cliente : clientes) {
            cliente.out.println(mensajeCompleto);
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
