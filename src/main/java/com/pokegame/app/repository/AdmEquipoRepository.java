package com.pokegame.app.repository;

import com.pokegame.app.modelo.AdmEquipo;

//TODA LA METODOLOGIA DE AdmEquipo ES PARA TENER UN LUGAR EN DONDE HACER EL CRUD DE LOS EQUIPOS (administrar el equipo), SI NO TE PARECE UNA BUENA IDEA, AVISAME Y LO HAGO TOD0 DENUEVO
//CRUD: AGREGAR, ELIMINAR Y MODIFICAR EL EQUIPO Y LOS POKEMONES DENTRO DEL EQUIPO
//LA IDEA ES QUE LUEGO, DENTRO DE LA INTERFAZ DE CADA POKEMON, USAR EL METODO DE AGREGAR POKEMON AL EQUIPO CON UN BOTON Y LISTO
//TENGO QUE MODIFICARLO BIEN ESO SI, PERO ES UNA IDEA BASE, ME FALTA VER TU CODIGO PARA VER BIEN COMO IMPLEMENTARLO

public interface AdmEquipoRepository {

    boolean crearEquipo(AdmEquipo equipo);
    boolean eliminarEquipoPorNombre(String nombreEquipo);
    boolean agregarPokemonAEquipo(int idEquipo, int idPokemon); //CODIGO NO USADO AUN :3
}