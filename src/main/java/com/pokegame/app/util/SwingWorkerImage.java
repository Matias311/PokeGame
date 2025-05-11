package com.pokegame.app.util;

import java.awt.MediaTracker;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

/** SwingWorkerImage. */
public class SwingWorkerImage extends SwingWorker<ImageIcon, Void> {

  private String url;
  private Map<String, ImageIcon> cache;
  private Consumer<ImageIcon> callback;
  private final int INTENTO = 5;

  /**
   * Crea un SwingWorkerImage con esos parametros para poder cargar la imagen en segundo plano en el
   * metodo doInBackground y luego en el metodo done, lo que hace es guardarla en el cache y si no
   * es null lo que hace es mandarsela al callback para que la cargue.
   *
   * @param url String
   * @param cache HashMap
   * @param callback Consumer
   */
  public SwingWorkerImage(String url, Map<String, ImageIcon> cache, Consumer<ImageIcon> callback) {
    this.url = url;
    this.cache = cache;
    this.callback = callback;
  }

  @Override
  protected ImageIcon doInBackground() throws Exception {
    int intentos = 0;
    long delayMs = 1000;
    ImageIcon image = null;
    URL imageUrl = new URL(url);

    while (intentos < INTENTO) {
      try {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setInstanceFollowRedirects(true);

        int responseCode = conn.getResponseCode();
        if (responseCode == 429) {
          // verifica si el servidor mando un mensaje del tiempo que se debe esperar y luego lo pasa
          // a ms
          String retryAfter = conn.getHeaderField("Retry-After");
          delayMs = (retryAfter != null) ? Long.parseLong(retryAfter) * 1000 : delayMs * 2;
          Thread.sleep(delayMs);
          intentos++;
          continue;
        }

        // Si la respuesta es exitosa, carga la imagen
        if (responseCode == HttpURLConnection.HTTP_OK) {
          image = new ImageIcon(imageUrl);
          return image;
        }
      } catch (Exception e) {
        System.err.println("Intento " + (intentos + 1) + " fallido: " + e.getMessage());
      }
      Thread.sleep(delayMs);
      delayMs *= 2;
      intentos++;
    }
    return null;
  }

  @Override
  protected void done() {
    try {
      ImageIcon image = get();
      if (image.getImageLoadStatus() == MediaTracker.COMPLETE) {
        cache.put(url, image);
        if (callback != null) {
          callback.accept(image);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
