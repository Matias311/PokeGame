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
    try {
      PreparedStatement state =
          conn.prepareStatement("SELECT imagen_frente FROM Imagenes WHERE id_pokemon = ?");
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

  @Override
  public Imagen buscarImagenPorIdPokemon(int id) {
    Imagen imagen = null;
    try {
      PreparedStatement state =
          conn.prepareStatement("SELECT * FROM Imagenes WHERE id_pokemon = ?");
      state.setInt(1, id);
      ResultSet result = state.executeQuery();
      imagen = crearImagen(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return imagen;
  }

  public Imagen crearImagen(ResultSet result) {
    Imagen imagen = null;
    try {
      while (result.next()) {
        imagen =
            new Imagen(
                result.getInt("id"),
                result.getString("imagen_frente"),
                result.getString("imagen_espalda"),
                result.getInt("id_pokemon"));
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return imagen;
  }
}
