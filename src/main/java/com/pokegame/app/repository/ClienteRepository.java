package com.pokegame.app.repository;

import com.pokegame.app.modelo.Cliente;

/** ClienteRepository. */
public interface ClienteRepository<T> {

  boolean crearUsuario(Cliente cliente);

  T obtenerUsuario(int id);

  T authenticate(String username, String password);

  int obtenerNuevoId();

  boolean cambiarNombreUsuario(int id, String nuevoNombre);
}
