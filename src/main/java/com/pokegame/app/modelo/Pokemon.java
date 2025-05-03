package com.pokegame.app.modelo;

/** Pokemon. */
public class Pokemon {

  private int id;
  private String nombre;
  private String descripcion;
  private int altura;
  private int peso;
  private String region;
  private int vida;
  private int ataque;
  private int defensa;

  /**
   * crea un pokemon con el id, nombre.
   *
   * @param id int
   * @param nombre String
   */
  public Pokemon(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  /**
   * Recive id, nombre, descripcion, altura, peso, region, vida, ataque del pokemon.
   *
   * @param id int
   * @param nombre String
   * @param descripcion String
   * @param altura int
   * @param peso int
   * @param region String
   * @param vida int
   * @param ataque int
   */
  public Pokemon(
      int id,
      String nombre,
      String descripcion,
      int altura,
      int peso,
      String region,
      int vida,
      int ataque,
      int defensa) {
    
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.altura = altura;
    this.peso = peso;
    this.region = region;
    this.vida = vida;
    this.ataque = ataque;
    this.defensa = defensa;
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

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getAltura() {
    return altura;
  }

  public void setAltura(int altura) {
    this.altura = altura;
  }

  public int getPeso() {
    return peso;
  }

  public void setPeso(int peso) {
    this.peso = peso;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public int getVida() {
    return vida;
  }

  public void setVida(int vida) {
    this.vida = vida;
  }

  public int getAtaque() {
    return ataque;
  }

  public void setAtaque(int ataque) {
    this.ataque = ataque;
  }

  public int getDefensa() {
    return defensa;
  }

  public void setDefensa(int defensa) {
    this.defensa = defensa;
  }

  @Override
  public String toString() {
    String format =
        String.format(
            "id: %c\n"
                + "nombre: %s\n"
                + "descripcion: %s\n"
                + "altura: %c\n"
                + "peso: %c\n"
                + "region: %s\n"
                + "vida: %c\n"
                + "ataque: %c\n"
                + "defensa: %c",
            id, nombre, descripcion, altura, peso, region, vida, ataque, defensa);
    return format;
  }
}
