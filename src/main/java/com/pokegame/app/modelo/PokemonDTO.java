package com.pokegame.app.modelo;

public class PokemonDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private float altura;
    private float peso;
    private String region;
    private int vida;
    private int ataque;
    private int defensa;
    private String imagenFrente;
    private String imagenEspalda;

    public PokemonDTO(int id, String nombre, String descripcion, float altura, float peso,
                      String region, int vida, int ataque, int defensa,
                      String imagenFrente, String imagenEspalda) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.altura = altura;
        this.peso = peso;
        this.region = region;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.imagenFrente = imagenFrente;
        this.imagenEspalda = imagenEspalda;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getAltura() {
        return altura;
    }

    public float getPeso() {
        return peso;
    }

    public String getRegion() {
        return region;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public String getImagenFrente() {
        return imagenFrente;
    }

    public String getImagenEspalda() {
        return imagenEspalda;
    }


}
