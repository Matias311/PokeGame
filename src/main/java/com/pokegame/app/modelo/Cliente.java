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

  /**
   * Se le pasa el id del cliente y el nombre, luego mediante el setter se le agrega la passwrod ya
   * hasheada.
   *
   * @param id int
   * @param nombreUsuario String
   */
  public Cliente(int id, String nombreUsuario) {
    this.id = id;
    this.nombreUsuario = nombreUsuario;
  }

  /**
   * Se crea un cliente solamente con el nombre y la password sin hashear, en el repositoryimpl se
   * le agrega el id.
   *
   * @param nombreUsuario String
   * @param password Streing
   */
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

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
