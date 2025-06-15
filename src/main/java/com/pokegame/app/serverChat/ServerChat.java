package com.pokegame.app.serverChat;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/** ServerChat. */
public class ServerChat implements Runnable {

  private final int socket = 3131;
  private Socket clientSocket;

  @Override
  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(socket)) {
      serverSocket.setReuseAddress(true);

      System.out.println("El servidor ha iniciado");

      while (true) {
        clientSocket = serverSocket.accept();

        // Manejar al cliente aceptado
        Runnable clientManejo = new ManejoCliente(clientSocket);
        Thread clientThread = new Thread(clientManejo);
        clientThread.start();
      }

    } catch (BindException e) {
      // se ignora porque significa que el servidor ya esta iniciado
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
