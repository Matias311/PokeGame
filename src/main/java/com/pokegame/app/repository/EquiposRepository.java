package com.pokegame.app.repository;

import com.pokegame.app.modelo.Pokemon;

import java.util.List;

public interface EquiposRepository<T> {
    List<T> obtenerNombresEquipos();
    List<Pokemon> obtenerPokemonesDeEquipo(String nombreEquipo);
}