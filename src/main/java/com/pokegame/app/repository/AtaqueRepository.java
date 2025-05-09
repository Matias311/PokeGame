package com.pokegame.app.repository;

import java.util.List;

/** AtaqueRepository. */
public interface AtaqueRepository<T> {
  // trae los ataques del pokemon, se le pasa el id del pokemon
  List<T> traerTodosAtaquesPokemon(int id);
}
