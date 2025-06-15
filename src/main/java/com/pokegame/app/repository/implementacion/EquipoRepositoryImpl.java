package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Equipo;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.EquiposRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;
import com.pokegame.app.util.VerificarSesion;
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
    int clienteId = VerificarSesion.getCliente().getId();

    String query = "SELECT id, nombre, cliente_id FROM Equipo WHERE cliente_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, clienteId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          equipos.add(new Equipo(rs.getInt("id"), rs.getString("nombre"), rs.getInt("cliente_id")));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return equipos;
  }

  @Override
  public List<Pokemon> obtenerPokemonesDeEquipo(String nombreEquipo) {
    List<Pokemon> pokemones = new ArrayList<>();
    String query =
        "SELECT p.id, p.nombre "
            + "FROM Equipo e "
            + "JOIN EquipoPokemon ep ON e.id = ep.id_equipo "
            + "JOIN Pokemon p ON ep.id_pokemon = p.id "
            + "WHERE e.nombre = ?";
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
  public int obtenerIdEquipoPorNombre(String nombreEquipo) {
    int clienteId = VerificarSesion.getCliente().getId();

    String query = "SELECT id FROM Equipo WHERE nombre = ? AND cliente_id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, nombreEquipo);
      stmt.setInt(2, clienteId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt("id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return -1;
  }

  @Override
  public String obtenerNombreEquipoPorId(int idEquipo) {
    String query = "SELECT nombre FROM Equipo WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, idEquipo);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getString("nombre");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean crearEquipo(Equipo equipo) {
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

  @Override
  public boolean actualizarNombreEquipo(String nombreActual, String nuevoNombre) {
    String sql = "UPDATE Equipo SET nombre = ? WHERE nombre = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, nuevoNombre);
      stmt.setString(2, nombreActual);
      int filasAfectadas = stmt.executeUpdate();
      return filasAfectadas > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean agregarPokemonAEquipo(int idEquipo, int idPokemon) {
    int nuevoId = 1;

    String sqlId = "SELECT ISNULL(MAX(id), 0) + 1 AS nuevoId FROM EquipoPokemon";
    try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlId)) {
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

  @Override
  public boolean eliminarPokemonDeEquipo(int idEquipo, int idPokemon) {
    String sql = "DELETE FROM EquipoPokemon WHERE id_equipo = ? AND id_pokemon = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idEquipo);
      stmt.setInt(2, idPokemon);
      int filasAfectadas = stmt.executeUpdate();
      return filasAfectadas > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
