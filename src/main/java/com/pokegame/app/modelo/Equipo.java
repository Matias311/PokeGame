package com.pokegame.app.modelo;

public class Equipo {
  private int id;
  private String nombre;
  private int clienteId;

  public Equipo(String nombre) {
    this.nombre = nombre;
  }

  public Equipo(int id, String nombre, int clienteId) {
    this.id = id;
    this.nombre = nombre;
    this.clienteId = clienteId;
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public int getClienteId() {
    return clienteId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
  }

  @Override
  public String toString() {
    return "Equipo [id=" + id + ", nombre=" + nombre + ", clienteId=" + clienteId + "]";
  }
}
