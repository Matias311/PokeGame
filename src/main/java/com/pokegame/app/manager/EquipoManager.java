package com.pokegame.app.manager;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.EquipoRepositoryImpl;

import java.util.List;

public class EquipoManager {

    private final EquipoRepositoryImpl equipoRepo = new EquipoRepositoryImpl();

    public List<Equipo> obtenerEquipos() {
        return equipoRepo.obtenerNombresEquipos();
    }

    public int obtenerIdPorNombre(String nombre) {
        return equipoRepo.obtenerIdEquipoPorNombre(nombre);
    }

    public List<Pokemon> obtenerPokemones(String nombreEquipo) {
        return equipoRepo.obtenerPokemonesDeEquipo(nombreEquipo);
    }

    public boolean crearEquipo(String nombre) {
        return equipoRepo.crearEquipo(new Equipo(0, nombre, 1));
    }

    public boolean eliminarEquipo(String nombre) {
        return equipoRepo.eliminarEquipoPorNombre(nombre);
    }

    public boolean actualizarNombreEquipo(String actual, String nuevo) {
        return equipoRepo.actualizarNombreEquipo(actual, nuevo);
    }

    public boolean eliminarPokemon(String nombreEquipo, Pokemon pokemon) {
        int idEquipo = obtenerIdPorNombre(nombreEquipo);
        return equipoRepo.eliminarPokemonDeEquipo(idEquipo, pokemon.getId());
    }
}

