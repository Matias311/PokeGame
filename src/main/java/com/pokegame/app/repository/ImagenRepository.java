package com.pokegame.app.repository;

/** ImagenRepository. */
public interface ImagenRepository<T> {

  T buscarImagenPorIdPokemon(int id);

  T buscarImagenPorIdPortadaPokemon(int id);
}
