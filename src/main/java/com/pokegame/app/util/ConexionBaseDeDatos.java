package com.pokegame.app.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/** ConexionBaseDeDatos. */
public class ConexionBaseDeDatos {
  /**
   * Getter para conexion a la base de datos, carga las properties de la base de datos.
   *
   * @return Connection
   */
  public static Connection getConexion() {
    try {
      if (conexion != null && !conexion.isClosed()) {
        return conexion;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Properties prop = new Properties();
    try {
      InputStream input =
          ConexionBaseDeDatos.class.getClassLoader().getResourceAsStream("db.properties");
      if (input == null) {
        throw new Exception("Error no se cargaron las variables de entorno");
      }
      prop.load(input);
      String basedatos = prop.getProperty("db.basedatos");
      String usuario = prop.getProperty("db.user");
      String password = prop.getProperty("db.pass");
      String host = prop.getProperty("db.host");
      String port = prop.getProperty("db.port");
      String url =
          String.format(
              "jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true",
              host, port, basedatos);
      conexion = (Connection) DriverManager.getConnection(url, usuario, password);
    } catch (Exception e) {
      System.out.println(e);
    }
    return conexion;
  }

  private static Connection conexion = null;
}
