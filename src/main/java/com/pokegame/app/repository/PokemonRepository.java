package com.pokegame.app.repository;

import java.util.List;

/** PokeDexRepository. */
public interface PokemonRepository<T> {
  List<T> traerTodosLosPokemon();

  List<T> traerPokemonOrdenadoNombre();

  List<T> traerPokemonOrdenadoId();

  List<T> traerPokemonOrdenadoAtaque();

  T traerPokemonNombre(T nombre);

  T traerPokemonId(T id);
}
