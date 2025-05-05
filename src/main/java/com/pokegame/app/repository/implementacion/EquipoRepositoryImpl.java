package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.util.ConexionBaseDeDatos;
import java.sql.*;
import java.util.*;

public class EquipoRepositoryImpl {

    public List<String> obtenerNombresEquipos() {
        List<String> equipos = new ArrayList<>();
        try (Connection conn = ConexionBaseDeDatos.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM Equipo")) {

            while (rs.next()) {
                equipos.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipos;
    }

    public List<Pokemon> obtenerPokemonesDeEquipo(String nombreEquipo) {
        List<Pokemon> pokemones = new ArrayList<>();
        String query = "SELECT p.id, p.nombre, p.descripcion, p.altura, p.peso, p.region, "
             + "p.vida, p.ataque, p.defensa, img.imagen_frente, img.imagen_espalda "
             + "FROM Equipo e "
             + "JOIN EquipoPokemon ep ON e.id = ep.id_equipo "
             + "JOIN Pokemon p ON ep.id_pokemon = p.id "
             + "LEFT JOIN imagenes img ON img.id_pokemon = p.id "
             + "WHERE e.nombre = ?";

        try (Connection conn = ConexionBaseDeDatos.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreEquipo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pokemones.add(new Pokemon(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("altura"),
                        rs.getFloat("peso"),
                        rs.getString("region"),
                        rs.getInt("vida"),
                        rs.getInt("ataque"),
                        rs.getInt("defensa"),
                        rs.getString("imagen_frente"),
                        rs.getString("imagen_espalda")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pokemones;
    }

    }
