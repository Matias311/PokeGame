package com.pokegame.app.modelo;

/** Imagen. */
public class Imagen {
  private int id;
  private String imagenFrente;
  private String imagenEspalda;
  private int idPokemon;

  /**
   * Crea la imagen de frente.
   *
   * @param imagenFrente String
   */
  public Imagen(String imagenFrente) {
    this.imagenFrente = imagenFrente;
  }

  /**
   * Crea un objeto imagen que contiene la imagenes del pokemon.
   *
   * @param id int
   * @param imagenFrente String
   * @param imagenEspalda String
   * @param idPokemon int
   */
  public Imagen(int id, String imagenFrente, String imagenEspalda, int idPokemon) {
    this.id = id;
    this.imagenFrente = imagenFrente;
    this.imagenEspalda = imagenEspalda;
    this.idPokemon = idPokemon;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getImagenFrente() {
    return imagenFrente;
  }

  public void setImagenFrente(String imagenFrente) {
    this.imagenFrente = imagenFrente;
  }

  public String getImagenEspalda() {
    return imagenEspalda;
  }

  public void setImagenEspalda(String imagenEspalda) {
    this.imagenEspalda = imagenEspalda;
  }

  public int getIdPokemon() {
    return idPokemon;
  }

  public void setIdPokemon(int idPokemon) {
    this.idPokemon = idPokemon;
  }

  @Override
  public String toString() {
    String format =
        String.format(
            "id:%c\nimagen_frente:%s\nimagen_espalda:%s\nid_pokemon:%s",
            id, imagenFrente, imagenEspalda, idPokemon);
    return format;
  }
}
