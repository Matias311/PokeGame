package com.pokegame.app.repository;

/** ImagenRepository. */
public interface ImagenRepository<T> {

  T buscarImagenPorIdPortadaPokemon(int id);
}
