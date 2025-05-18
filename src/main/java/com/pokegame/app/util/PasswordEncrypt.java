package com.pokegame.app.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/** PasswordEncrypt. */
public class PasswordEncrypt {

  private static final int BCRYPT_ROUNDS = 12;

  /**
   * Encripta el password y lo retorna en forma de hash.
   *
   * @param password String
   * @return retorna el password encriptado en hash
   */
  public static String hashPassword(String password) {
    return BCrypt.withDefaults().hashToString(BCRYPT_ROUNDS, password.toCharArray());
  }

  public static boolean verificarPassword(String password, String hashPass) {
    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashPass);
    return result.verified;
  }
}
