package com.pokegame.app.util;

import com.google.gson.Gson;
import com.pokegame.app.modelo.Equipo;
import java.util.Base64;

/** SerializarEquipo. */
public class SerializarEquipo {

  /**
   * Serializa un equipo y retorna los bytes.
   *
   * @param equipo Equipo
   * @return String
   */
  public static String serializarEquipo(Equipo equipo) {
    Gson gson = new Gson();
    String json = gson.toJson(equipo);
    return Base64.getEncoder().encodeToString(json.getBytes());
  }

  /**
   * Deserializa bytes a un equipo.
   *
   * @param base64 String
   * @return Equipo
   */
  public static Equipo deserializarEquipo(String base64) {
    byte[] decoded = Base64.getDecoder().decode(base64);
    String json = new String(decoded);
    return new Gson().fromJson(json, Equipo.class);
  }
}
