package com.pokegame.app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.ImageIcon;

/** ImagenCache. */
public class ImagenCache {
  public static Map<String, ImageIcon> cache = new HashMap<>();

  /**
   * Lo que hace es verificar si la imagen esta cargada (esta en el cache) si no lo esta llama a
   * SwingWorker (nuevo hilo) y este carga en segundo plano la imagen y retorna la imagen, y
   * actualiza el chache.
   *
   * @param url String
   * @param callback Consumer
   */
  public static void getImage(String url, Consumer<ImageIcon> callback) {
    if (cache.containsKey(url)) {
      callback.accept(cache.get(url));
    } else {
      new SwingWorkerImage(url, cache, callback).execute();
    }
  }
}
