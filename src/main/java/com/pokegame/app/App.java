package com.pokegame.app;

import com.pokegame.app.util.ConexionBaseDeDatos;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    ConexionBaseDeDatos.getConexion();
  }
}
