package com.pokegame.app.repository;

import java.util.List;

/** PokeDexRepository. */
public interface PokemonRepository<T> {

  List<T> traerPokemonOrdenadoNombre();

  List<T> traerPokemonOrdenadoId();

  List<T> traerPokemonOrdenadoAtaque();

  List<T> traerPokemonNombre(String nombre);

  T traerPokemonId(int id);

  List<T> traerPaginacionPokemon(int inicio, int cant);
}
