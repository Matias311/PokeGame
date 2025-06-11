package com.pokegame.app.gui;

import com.pokegame.app.modelo.Ataque;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.AtaqueRepositoryImpl;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;
import com.pokegame.app.util.AtaqueTableModel;
import com.pokegame.app.util.ImagenCache;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
public class InformacionPokemon extends JFrame {

  private Pokemon pokemon;

  /**
   * Se le pasa el id del pokemon para poder encontrar la información y la URL de la imagen para
   * buscarla en el cache.
   *
   * @param idPokemon int
   * @param urlImagen String
   */
  public InformacionPokemon(int idPokemon, String urlImagen) {
    setSize(900, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(3, 1, 12, 5));

    // Obtener el Pokémon
    this.pokemon = new PokemonRepositoryImpl().traerPokemonId(idPokemon);

    // Sección Nombre e imagen
    JPanel seccionNombreImagen = new JPanel();

    // Imagen
    JLabel imagen = new JLabel();
    seccionNombreImagen.add(imagen);
    ImagenCache.getImage(
            urlImagen,
            imagenIcon -> {
              // Hace un escalado de la imagen
              ImageIcon image =
                      new ImageIcon(imagenIcon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
              imagen.setIcon(image);
              imagen.setHorizontalAlignment(JLabel.CENTER);
              imagen.setVerticalAlignment(JLabel.CENTER);
              imagen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
              imagen.revalidate();
              imagen.repaint();
            });

    // Sección nombre
    JLabel labelNombre = new JLabel(pokemon.getNombre());
    labelNombre.setFont(new Font("Arial", Font.BOLD, 25));
    seccionNombreImagen.add(labelNombre);
    add(seccionNombreImagen);

    // Descripción y estadísticas del pokemon
    JPanel seccionDescripcionStats = new JPanel(new GridLayout(1, 3, 20, 0));
    seccionDescripcionStats.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

    // Estadísticas
    JPanel seccionStats = new JPanel(new GridLayout(6, 2, 5, 5));
    seccionStats.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
    seccionStats.add(new JLabel("Altura:", SwingConstants.RIGHT));
    seccionStats.add(new JLabel(pokemon.getAltura() + " m"));
    seccionStats.add(new JLabel("Peso:", SwingConstants.RIGHT));
    seccionStats.add(new JLabel(pokemon.getPeso() + " kg"));
    seccionStats.add(new JLabel("Vida:", SwingConstants.RIGHT));
    seccionStats.add(new JLabel(String.valueOf(pokemon.getVida())));
    seccionStats.add(new JLabel("Ataque:", SwingConstants.RIGHT));
    seccionStats.add(new JLabel(String.valueOf(pokemon.getAtaque())));
    seccionStats.add(new JLabel("Defensa:", SwingConstants.RIGHT));
    seccionStats.add(new JLabel(String.valueOf(pokemon.getDefensa())));
    seccionDescripcionStats.add(seccionStats);

    // Descripción
    JPanel seccionDescripcion = new JPanel(new BorderLayout(5, 5));
    seccionDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));

    // Texto de descripción en un JTextArea
    JTextArea descripcionArea = new JTextArea(pokemon.getDescripcion());
    descripcionArea.setWrapStyleWord(true);
    descripcionArea.setEditable(false);
    descripcionArea.setBackground(getContentPane().getBackground());
    descripcionArea.setMargin(new Insets(10, 10, 10, 10));
    descripcionArea.setFocusable(false);

    seccionDescripcion.add(descripcionArea, BorderLayout.CENTER);
    seccionDescripcionStats.add(seccionDescripcion);

    // Se agrega sección central (stats + descripción) al frame
    add(seccionDescripcionStats);

    // Tabla de ataques del Pokémon
    List<Ataque> lista = new AtaqueRepositoryImpl().traerTodosAtaquesPokemon(pokemon.getId());
    AtaqueTableModel modeloTabla = new AtaqueTableModel(lista);
    JTable tablaAtaques = new JTable(modeloTabla);
    JScrollPane scroll = new JScrollPane(tablaAtaques);
    add(scroll);

    setVisible(true);
  }
}
