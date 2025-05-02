package com.pokegame.app.util;

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
    URL imageUrl = new URL(url);
    return new ImageIcon(imageUrl);
  }

  @Override
  protected void done() {
    try {
      ImageIcon image = get();
      cache.put(url, image);
      if (callback != null) {
        callback.accept(image);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
