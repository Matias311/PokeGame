package com.pokegame.app.modelo;

/** Cliente. */
public class Cliente {

  private int id;
  private String nombreUsuario;
  private String passwordHash;

  /**
   * Crea un cliente, este contiene el nombre, tambien automaticamente encripta la password.
   *
   * @param nombreUsuario String
   * @param password String
   */
  public Cliente(int id, String nombreUsuario, String password) {
    this.id = id;
    this.nombreUsuario = nombreUsuario;
    this.passwordHash = com.pokegame.app.util.PasswordEncrypt.hashPassword(password);
  }

  public Cliente(String nombreUsuario, String password) {
    this.nombreUsuario = nombreUsuario;
    this.passwordHash = com.pokegame.app.util.PasswordEncrypt.hashPassword(password);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre_usuario() {
    return nombreUsuario;
  }

  public void setNombre_usuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getPassword_hash() {
    return passwordHash;
  }

  public void setPassword_hash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
