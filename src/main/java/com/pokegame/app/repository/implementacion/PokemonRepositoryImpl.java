package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.PokemonRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** PokemonRepositoryImpl. */
public class PokemonRepositoryImpl implements PokemonRepository<Pokemon> {


  private Connection conn = com.pokegame.app.util.ConexionBaseDeDatos.getConexion();

  @Override
  public List<Pokemon> traerPaginacionPokemon(int inicio, int cant) {
    List<Pokemon> lista = new ArrayList<Pokemon>();
    try (PreparedStatement state =
        conn.prepareStatement(
            "SELECT id, nombre FROM Pokemon ORDER BY id ASC OFFSET ? ROWS FETCH NEXT ? ROWS"
                + " ONLY"); ) {
      state.setInt(1, inicio);
      state.setInt(2, cant);
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;

  }

  @Override
  public List<Pokemon> traerPokemonOrdenadoNombre() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'traerPokemonOrdenadoNombre'");
  }

  @Override
  public List<Pokemon> traerPokemonOrdenadoId() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'traerPokemonOrdenadoId'");
  }

  @Override
  public List<Pokemon> traerPokemonOrdenadoAtaque() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'traerPokemonOrdenadoAtaque'");
  }

  @Override
  public Pokemon traerPokemonNombre(Pokemon nombre) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'traerPokemonNombre'");
  }

  @Override
  public Pokemon traerPokemonId(Pokemon id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'traerPokemonId'");
  }


  /**
   * Crea pokemon con id, nombre.
   *
   * @param result ResultSet
   * @return List
   */
  public List<Pokemon> crearListaPokemonIdNombre(ResultSet result) {
    List<Pokemon> lista = new ArrayList<Pokemon>();
    try {
      while (result.next()) {
        Pokemon pokemon = new Pokemon(result.getInt("id"), result.getString("nombre"));
        lista.add(pokemon);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;
  }

}
