package com.pokegame.app.repository;

import java.util.List;

/** MensajeRepository. */
public interface MensajeRepository<T> {

  // Traer mensajes
  List<T> traerTodosLosMensajes();

  // Guardar mensaje
  void guardarMensaje(T mensaje);
}
