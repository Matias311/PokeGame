package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.repository.ImagenRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** ImagenRepositoryImpl. */
public class ImagenRepositoryImpl implements ImagenRepository<Imagen> {

  private Connection conn = com.pokegame.app.util.ConexionBaseDeDatos.getConexion();

  @Override
  public Imagen buscarImagenPorIdPortadaPokemon(int id) {
    Imagen imagen = null;
    try (PreparedStatement state =
        conn.prepareStatement("SELECT imagen_frente FROM Imagenes WHERE id_pokemon = ?"); ) {
      state.setInt(1, id);
      ResultSet result = state.executeQuery();
      while (result.next()) {
        imagen = new Imagen(result.getString("imagen_frente"));
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return imagen;
  }
}
