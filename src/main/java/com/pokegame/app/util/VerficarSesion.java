package com.pokegame.app.util;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.repository.implementacion.ClienteRepositoryImpl;

/** VerficarSesion. */
public class VerficarSesion {
  private static Cliente cliente = null;
  private static ClienteRepositoryImpl services = new ClienteRepositoryImpl();

  /**
   * Verifica si el usuario esta activo o no, lo hace pidiendo el usuario a la base de datos y si lo
   * devuelve correctamente se logea.
   *
   * @param nombre String
   * @param password String
   * @return boolean
   */
  public static boolean login(String nombre, String password) {
    Cliente c = services.authenticate(nombre, password);
    if (c != null) {
      cliente = c;
      return true;
    } else {
      return false;
    }
  }

  public static Cliente getCliente() {
    return cliente;
  }

  public static void logout() {
    cliente = null;
  }

  public static boolean isLoggedIn() {
    return cliente != null;
  }

  public static boolean register(String nombre, String password) {
    Cliente c = new Cliente(nombre, password);
    return services.crearUsuario(c);
  }
}
