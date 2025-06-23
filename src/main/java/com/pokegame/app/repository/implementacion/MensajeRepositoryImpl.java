package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.modelo.Mensaje;
import com.pokegame.app.repository.MensajeRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** MensajeRepositoryImpl. */
public class MensajeRepositoryImpl implements MensajeRepository<Mensaje> {

  private final Connection conn = ConexionBaseDeDatos.getConexion();
  private final ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl();

  @Override
  public List<Mensaje> traerTodosLosMensajes() {
    List<Mensaje> lista = new ArrayList<>();

    try (Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM MENSAJE ORDER BY fecha_hora;")) {
      while (result.next()) {
        Cliente cliente = clienteRepository.obtenerUsuario((result.getInt("id_cliente")));
        Mensaje msj =
            new Mensaje(
                result.getString("tipo_mensaje"), result.getString("contenido_mensaje"), cliente);
        lista.add(msj);
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return lista;
  }

  @Override
  public void guardarMensaje(Mensaje mensaje) {
    try (PreparedStatement state =
        conn.prepareStatement(
            "insert into Mensaje (tipo_mensaje, contenido_mensaje, id_cliente) values (?,?,?);")) {
      state.setString(1, mensaje.getTipoMensaje());
      state.setString(2, mensaje.getMensaje());
      state.setInt(3, mensaje.getCliente().getId());
      state.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
