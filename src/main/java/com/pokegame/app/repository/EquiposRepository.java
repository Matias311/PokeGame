package com.pokegame.app.repository;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import java.util.List;

public interface EquiposRepository<T> {
    List<T> obtenerNombresEquipos();
    List<Pokemon> obtenerPokemonesDeEquipo(String nombreEquipo);
    boolean crearEquipo(Equipo equipo);
    boolean eliminarEquipoPorNombre(String nombreEquipo);
    boolean agregarPokemonAEquipo(int idEquipo, int idPokemon);
    boolean actualizarNombreEquipo(String nombreActual, String nuevoNombre);
    int obtenerIdEquipoPorNombre(String nombreEquipo); // Nueva función añadida
    String obtenerNombreEquipoPorId(int idEquipo);
}
