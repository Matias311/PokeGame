package com.pokegame.app.modelo;

/** Ataque. */
public class Ataque {

  private int id;
  private String nombre;
  private int damage;

  /**
   * Crea el ataque con todos sus atributos.
   *
   * @param id int
   * @param nombre String
   * @param damage int
   */
  public Ataque(int id, String nombre, int damage) {
    this.id = id;
    this.nombre = nombre;
    this.damage = damage;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }
}
