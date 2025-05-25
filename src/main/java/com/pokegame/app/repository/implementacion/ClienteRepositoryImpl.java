package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.repository.ClienteRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;
import com.pokegame.app.util.PasswordEncrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** ClienteRepositoryImpl. */
public class ClienteRepositoryImpl implements ClienteRepository<Cliente> {

  private final Connection conn = ConexionBaseDeDatos.getConexion();

  @Override
  public boolean crearUsuario(Cliente cliente) {
    boolean status = false;
    try (PreparedStatement state =
        conn.prepareStatement(
            "INSERT INTO Cliente (id, nombre_usuario, password) VALUES (?, ?, ?)")) {
      state.setInt(1, obtenerNuevoId());
      state.setString(2, cliente.getNombreUsuario());
      state.setString(3, cliente.getPasswordHash());

      int rowsAffected = state.executeUpdate();
      status = rowsAffected > 0;

    } catch (Exception e) {
      System.err.println("Error al crear usuario: " + e.getMessage());
      e.printStackTrace();
    }
    return status;
  }

  @Override
  public Cliente obtenerUsuario(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'obtenerUsuario'");
  }

  @Override
  public Cliente authenticate(String username, String password) {
    Cliente cliente = null;
    try {
      PreparedStatement state =
          conn.prepareStatement("select * from Cliente where nombre_usuario = ?");
      state.setString(1, username);
      ResultSet result = state.executeQuery();
      while (result.next()) {
        if (PasswordEncrypt.verificarPassword(password, result.getString("password"))) {
          cliente = new Cliente(result.getInt("id"), result.getString("nombre_usuario"));
          cliente.setPasswordHash(result.getString("password"));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return cliente;
  }

  @Override
  public int obtenerNuevoId() {
    String sql = "SELECT ISNULL(MAX(id), 0) + 1 AS nuevoId FROM Cliente";
    try (PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        return rs.getInt("nuevoId");
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return 0;
  }
}
