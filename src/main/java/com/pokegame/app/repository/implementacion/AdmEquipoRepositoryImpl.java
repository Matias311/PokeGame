package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.AdmEquipo;
import com.pokegame.app.repository.AdmEquipoRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODA LA METODOLOGIA DE AdmEquipo ES PARA TENER UN LUGAR EN DONDE HACER EL CRUD DE LOS EQUIPOS (administrar el equipo), SI NO TE PARECE UNA BUENA IDEA, AVISAME Y LO HAGO TOD0 DENUEVO

public class AdmEquipoRepositoryImpl implements AdmEquipoRepository {

    private final Connection conn = ConexionBaseDeDatos.getConexion();

//SI SE TE OCURRE UNA MEJOR IDEA PARA HACER ESTO YA QUE NO ESTA EL AUTOINCREMENT AVISAME XDXDXDXD
    private int obtenerNuevoIdEquipo() throws SQLException {
        String sql = "SELECT ISNULL(MAX(id), 0) + 1 AS nuevoId FROM Equipo";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nuevoId");
            }
        }
        throw new SQLException("Hubo un problema al obtener el ID del Equipo.");
    }

    @Override
    public boolean crearEquipo(AdmEquipo equipo) {
        String sql = "INSERT INTO Equipo (id, nombre, cliente_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int nuevoId = obtenerNuevoIdEquipo();
            stmt.setInt(1, nuevoId);
            stmt.setString(2, equipo.getNombre());
            stmt.setInt(3, equipo.getClienteId());
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarEquipoPorNombre(String nombreEquipo) {
        String queryObtenerId = "SELECT id FROM Equipo WHERE nombre = ?";
        String queryEliminarRelaciones = "DELETE FROM EquipoPokemon WHERE id_equipo = ?";
        String queryEliminarEquipo = "DELETE FROM Equipo WHERE id = ?";

        try (PreparedStatement stmtId = conn.prepareStatement(queryObtenerId)) {
            stmtId.setString(1, nombreEquipo);
            ResultSet rs = stmtId.executeQuery();

            if (rs.next()) {
                int idEquipo = rs.getInt("id");

                try (PreparedStatement stmtRelaciones = conn.prepareStatement(queryEliminarRelaciones)) {
                    stmtRelaciones.setInt(1, idEquipo);
                    stmtRelaciones.executeUpdate();
                }

                try (PreparedStatement stmtEliminar = conn.prepareStatement(queryEliminarEquipo)) {
                    stmtEliminar.setInt(1, idEquipo);
                    return stmtEliminar.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //ESTO ES CODIGO NO USADO, PERO EN VOLA SIRVE AGREGAR LOS POKEMONES LUEGO
    @Override
    public boolean agregarPokemonAEquipo(int idEquipo, int idPokemon) {
        int nuevoId = 1;

        //ESTE ISNULL ESTA HECHO PARA ASIGNARLE UNA ID A "id" de la tabla EquipoPokemon, pq como no es auto incremental, no puedo hacer un insert directo;
        String sqlId = "SELECT ISNULL(MAX(id), 0) + 1 AS nuevoId FROM EquipoPokemon";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlId)) {
            if (rs.next()) {
                nuevoId = rs.getInt("nuevoId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        String insertSql = "INSERT INTO EquipoPokemon (id, id_equipo, id_pokemon) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, nuevoId);
            stmt.setInt(2, idEquipo);
            stmt.setInt(3, idPokemon);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
