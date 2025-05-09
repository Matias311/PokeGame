package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Ataque;
import com.pokegame.app.repository.AtaqueRepository;
import com.pokegame.app.util.ConexionBaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** AtaqueRepositoryImpl. */
public class AtaqueRepositoryImpl implements AtaqueRepository<Ataque> {

  private Connection conn = ConexionBaseDeDatos.getConexion();

  @Override
  public List<Ataque> traerTodosAtaquesPokemon(int id) {
    List<Ataque> lista = new ArrayList<Ataque>();
    try {
      String query =
          "select mov.id, mov.nombre, mov.damage from Movimiento mov JOIN PokemonMovimiento pm ON"
              + " pm.id_movimiento =  mov.id JOIN Pokemon p ON pm.id_pokemon = p.id where p.id = ?";
      PreparedStatement state = conn.prepareStatement(query);
      state.setInt(1, id);
      ResultSet result = state.executeQuery();
      while (result.next()) {
        Ataque ataque =
            new Ataque(result.getInt("id"), result.getString("nombre"), result.getInt("damage"));
        lista.add(ataque);
      }
    } catch (Exception e) {
      System.out.println("Error");
    }
    return lista;
  }
}
