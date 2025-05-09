package com.pokegame.app.gui;

import com.pokegame.app.modelo.Ataque;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.repository.implementacion.AtaqueRepositoryImpl;
import com.pokegame.app.repository.implementacion.PokemonRepositoryImpl;
import com.pokegame.app.util.AtaqueTableModel;
import com.pokegame.app.util.ImagenCache;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/** InformacionPokemon. */
public class InformacionPokemon extends JFrame {

  /**
   * Se le pasa el id del pokemon para poder encontrar la Informacion y la Imagen para buscarla en
   * el cache.
   *
   * @param idPokemon int
   * @param urlImagen String
   */
  public InformacionPokemon(int idPokemon, String urlImagen) {
    setSize(900, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(3, 1, 5, 10));
    // Seccion nombre, stats, imagen
    JPanel seccionNombreStats = new JPanel();
    // Imagen
    JLabel imagen = new JLabel();
    seccionNombreStats.add(imagen);
    ImagenCache.getImage(
        urlImagen,
        imagenIcon -> {
          imagen.setIcon(imagenIcon);
          imagen.setHorizontalAlignment(JLabel.CENTER);
          imagen.setVerticalAlignment(JLabel.CENTER);
          imagen.revalidate();
          imagen.repaint();
        });
    // Seccion nombre + stats
    Pokemon pokemon = new PokemonRepositoryImpl().traerPokemonId(idPokemon);
    JPanel panelNombreStats = new JPanel(new GridLayout(2, 1, 5, 5));
    panelNombreStats.setPreferredSize(new Dimension(450, 200));
    JLabel labelNombre = new JLabel(pokemon.getNombre());
    panelNombreStats.add(labelNombre);
    // tabla de estadisticas
    JPanel panelStats = new JPanel(new GridLayout(6, 1, 5, 5));
    JLabel alturaLabel = new JLabel("Altura: " + pokemon.getAltura());
    panelStats.add(alturaLabel);
    JLabel pesoLabel = new JLabel("Peso: " + pokemon.getPeso());
    panelStats.add(pesoLabel);
    JLabel vidaLabel = new JLabel("Vida: " + pokemon.getVida());
    panelStats.add(vidaLabel);
    JLabel ataqueLabel = new JLabel("Ataque: " + pokemon.getAtaque());
    panelStats.add(ataqueLabel);
    JLabel defensaLabel = new JLabel("Defensa: " + pokemon.getDefensa());
    panelStats.add(defensaLabel);
    // Boton de agregar a equipo
    JButton btnAgregar = new JButton("Agregar al equipo");
    panelStats.add(btnAgregar);
    panelNombreStats.add(panelStats);
    seccionNombreStats.add(panelNombreStats);
    add(seccionNombreStats);
    // Descripcion del pokemon
    JLabel descripcion = new JLabel(pokemon.getDescripcion());
    add(descripcion);
    // Tabla de ataques
    List<Ataque> lista = new AtaqueRepositoryImpl().traerTodosAtaquesPokemon(pokemon.getId());
    AtaqueTableModel modeloTabla = new AtaqueTableModel(lista);
    JTable tablaAtaques = new JTable(modeloTabla);
    JScrollPane scroll = new JScrollPane(tablaAtaques);
    add(scroll);

    setVisible(true);
  }
}
