package com.pokegame.app.modelo;

import java.util.List;

public class Equipo {
  private int id;
  private String nombre;
  private int clienteId;
  private List<Pokemon> listaPokemon;

  public List<Pokemon> getListaPokemon() {
    return listaPokemon;
  }

  public void setListaPokemon(List<Pokemon> listaPokemon) {
    this.listaPokemon = listaPokemon;
  }

  public Equipo(String nombre) {
    this.nombre = nombre;
  }

  public Equipo(String nombre, List<Pokemon> lista) {
    this.nombre = nombre;
    this.listaPokemon = lista;
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
