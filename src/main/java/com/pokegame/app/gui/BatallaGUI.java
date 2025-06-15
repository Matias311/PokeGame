package com.pokegame.app.gui;

import com.pokegame.app.animations.batallaAni;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

public class    BatallaGUI extends JFrame {

    private JLayeredPane capaBatalla;
    private JLabel labelJugador, labelEnemigo, fondoLabel;
    private JLabel cartelAliado, cartelEnemigo;

    public BatallaGUI() {
        setTitle("Batalla Pokémon");
        setSize(960, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        capaBatalla = new JLayeredPane();
        capaBatalla.setPreferredSize(new Dimension(960, 448));

        fondoLabel = new JLabel();
        fondoLabel.setBounds(0, 0, 960, 448);
        fondoLabel.setIcon(new ImageIcon("src/main/resources/ImgBatalla/fondo_batalla_escalado.png"));
        capaBatalla.add(fondoLabel, JLayeredPane.DEFAULT_LAYER);

        ImageIcon cartelOriginal = new ImageIcon("src/main/resources/ImgBatalla/cartel_batalla.png");
        Image cartelEscalado = cartelOriginal.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
        ImageIcon cartelIcon = new ImageIcon(cartelEscalado);

        cartelAliado = new JLabel(cartelIcon);
        cartelAliado.setBounds(-15, 280, 300, 250);
        capaBatalla.add(cartelAliado, Integer.valueOf(600));

        cartelEnemigo = new JLabel(cartelIcon);
        cartelEnemigo.setBounds(650, -80, 300, 250);
        capaBatalla.add(cartelEnemigo, Integer.valueOf(600));

        labelJugador = new JLabel();
        labelJugador.setBounds(60, 180, 400, 400);
        capaBatalla.add(labelJugador, JLayeredPane.PALETTE_LAYER);

        labelEnemigo = new JLabel();
        labelEnemigo.setBounds(590, 75, 240, 240);
        capaBatalla.add(labelEnemigo, JLayeredPane.PALETTE_LAYER);

        JPanel panelInferior = new JPanel(new GridBagLayout());
        panelInferior.setPreferredSize(new Dimension(960, 110));
        Dimension botonSize = new Dimension(180, 45);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);

        JButton btnAtacar = new JButton("Atacar");
        btnAtacar.setPreferredSize(botonSize);
        panelInferior.add(btnAtacar, gbc);

        JButton btnCambiar = new JButton("Cambiar Pokémon");
        btnCambiar.setPreferredSize(botonSize);
        panelInferior.add(btnCambiar, gbc);

        JButton btnRendirse = new JButton("Rendirse");
        btnRendirse.setPreferredSize(botonSize);
        panelInferior.add(btnRendirse, gbc);

        add(capaBatalla, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.SOUTH);

        //Este Boton está mostrando las animaciones, estan todas las funciones guardadas en batallaAni.java
        //Cuando hagamos el ataque de verdad, solo llamamos la función y listop

        btnAtacar.addActionListener(e -> {
            batallaAni.animarGolpe(labelEnemigo);
            batallaAni.animarGolpe(labelJugador);

            //Posicion y escala para que las animaciones de ataque se vean bien
            batallaAni.AnimacionSlash slashEnemigo = new batallaAni.AnimacionSlash(
                    "src/main/resources/ImgBatalla/slash.png", 670, 120, 1, 1
            );
            batallaAni.AnimacionSlash slashAliado = new batallaAni.AnimacionSlash(
                    "src/main/resources/ImgBatalla/slash.png", 190, 280, 2, 2
            );

            capaBatalla.add(slashEnemigo, JLayeredPane.DRAG_LAYER);
            capaBatalla.add(slashAliado, JLayeredPane.DRAG_LAYER);
            capaBatalla.repaint();
        });
    }

    //TODO hay que modificar esto para que acepte la imagen de la base de datos

    public void setSpriteAliado(String rutaCompleta) {
        cargarImagenEnLabel(labelJugador, rutaCompleta, 400, 400);
    }

    public void setSpriteEnemigo(String rutaCompleta) {
        cargarImagenEnLabel(labelEnemigo, rutaCompleta, 240, 240);
    }

    private void cargarImagenEnLabel(JLabel label, String ruta, int width, int height) {
        ImageIcon icon = new ImageIcon(ruta);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    //puesto para iniciar la ventana de batalla
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatallaGUI ventana = new BatallaGUI();
            ventana.setVisible(true);
            ventana.setSpriteAliado("src/main/resources/PLACEHOLDERS/atras.png");
            ventana.setSpriteEnemigo("src/main/resources/PLACEHOLDERS/adelante.png");
        });
    }
}
