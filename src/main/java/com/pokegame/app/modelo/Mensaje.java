package com.pokegame.app.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Mensaje implementa Serializable para poder pasarla a bits. */
public class Mensaje implements Serializable {

  private String tipoMensaje;
  private String mensaje;
  private Cliente cliente;
  private LocalDateTime time;

  /**
   * Crea el mensaje con string mensaje y cliente.
   *
   * @param mensaje String
   * @param cliente Cliente
   */
  public Mensaje(String tipoMensaje, String mensaje, Cliente cliente) {
    this.mensaje = mensaje;
    this.cliente = cliente;
    this.time = LocalDateTime.now();
    this.tipoMensaje = tipoMensaje;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getTipoMensaje() {
    return tipoMensaje;
  }

  public void setTipoMensaje(String tipoMensaje) {
    this.tipoMensaje = tipoMensaje;
  }
}
