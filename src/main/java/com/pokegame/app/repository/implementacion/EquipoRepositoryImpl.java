package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.EquiposRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipoRepositoryImpl implements EquiposRepository<Equipo> {

    private final Connection conn = ConexionBaseDeDatos.getConexion();

    @Override
    public List<Equipo> obtenerNombresEquipos() {
        List<Equipo> equipos = new ArrayList<>();
        String query = "SELECT nombre FROM Equipo";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                equipos.add(new Equipo(rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipos;
    }

    @Override
    public List<Pokemon> obtenerPokemonesDeEquipo(String nombreEquipo) {
        List<Pokemon> pokemones = new ArrayList<>();
        String query = "SELECT p.id, p.nombre " +
                "FROM Equipo e " +
                "JOIN EquipoPokemon ep ON e.id = ep.id_equipo " +
                "JOIN Pokemon p ON ep.id_pokemon = p.id " +
                "WHERE e.nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreEquipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pokemon pokemon = new Pokemon(rs.getInt("id"), rs.getString("nombre"));
                    pokemones.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemones;
    }
}