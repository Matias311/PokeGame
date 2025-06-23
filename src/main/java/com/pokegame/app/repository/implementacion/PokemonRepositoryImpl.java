package com.pokegame.app.repository.implementacion;

import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.PokemonRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            "SELECT id, nombre FROM Pokemon ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS"
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
    List<Pokemon> lista = new ArrayList<>();
    try (PreparedStatement state = conn.prepareStatement(
            "SELECT id, nombre FROM Pokemon ORDER BY nombre ASC")) {
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;
  }

  @Override
  public List<Pokemon> traerPokemonOrdenadoId() {
    List<Pokemon> lista = new ArrayList<>();
    try (PreparedStatement state = conn.prepareStatement(
            "SELECT id, nombre FROM Pokemon ORDER BY id ASC")) {
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;
  }

  @Override
  public List<Pokemon> traerPokemonOrdenadoAtaque() {
    List<Pokemon> lista = new ArrayList<>();
    try (PreparedStatement state = conn.prepareStatement(
            "SELECT id, nombre FROM Pokemon ORDER BY ataque DESC")) {
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;
  }

  public List<Pokemon> traerPokemonOrdenadoConPaginacion(String orden, int inicio, int cantidad) {
    List<Pokemon> lista = new ArrayList<>();
    String sql = "SELECT id, nombre FROM Pokemon ORDER BY " + orden + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    try (PreparedStatement state = conn.prepareStatement(sql)) {
      state.setInt(1, inicio);
      state.setInt(2, cantidad);
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (Exception e) {
      System.out.println(e);
    }
    return lista;
  }

  @Override
  public List<Pokemon> traerPokemonNombre(String nombre) {
    List<Pokemon> lista = new ArrayList<Pokemon>();
    String preNombre = nombre.toLowerCase().trim() + "%";
    try (PreparedStatement state =
        conn.prepareStatement("select id, nombre from Pokemon where nombre like ?"); ) {
      state.setString(1, preNombre);
      ResultSet result = state.executeQuery();
      lista = crearListaPokemonIdNombre(result);
    } catch (SQLException e) {
      System.out.println(e);
    }
    return lista;
  }

  @Override
  public Pokemon traerPokemonId(int id) {
    Pokemon pokemon = null;
    String sql = "SELECT * FROM Pokemon WHERE id = ?";
    try {
      PreparedStatement state = conn.prepareStatement(sql);
      state.setInt(1, id);
      ResultSet result = state.executeQuery();
      while (result.next()) {
        pokemon =
            new Pokemon(
                result.getInt("id"),
                result.getString("nombre"),
                result.getString("descripcion"),
                result.getInt("altura"),
                result.getInt("peso"),
                result.getString("region"),
                result.getInt("vida"),
                result.getInt("ataque"),
                result.getInt("defensa"));
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return pokemon;
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
