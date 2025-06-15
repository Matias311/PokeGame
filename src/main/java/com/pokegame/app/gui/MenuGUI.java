package com.pokegame.app.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

// EJEMPLO DE COMO PODRIA SER UN MENÚ PRINCIPAL
public class MenuGUI extends JFrame {

    public MenuGUI() {
        setTitle("Pokémon - Menú Principal");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel panelTitulo = new JPanel();
        panelTitulo.setPreferredSize(new Dimension(900, 150));
        JLabel labelTitulo = new JLabel(); // acá ponemos una foto del logo de pokemon o alguna wea asi xd
        panelTitulo.add(labelTitulo);


        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 300, 150, 300));
        panelBotones.setOpaque(false);

        JButton btnCrear = new JButton("Crear Partida");
        JButton btnUnirse = new JButton("Unirse a Partida");
        JButton btnSalir = new JButton("Salir");

        Dimension botonSize = new Dimension(300, 50);
        btnCrear.setMaximumSize(botonSize);
        btnUnirse.setMaximumSize(botonSize);
        btnSalir.setMaximumSize(botonSize);

        btnCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUnirse.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotones.add(btnCrear);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        panelBotones.add(btnUnirse);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        panelBotones.add(btnSalir);

        btnSalir.addActionListener(e -> System.exit(0));

        add(panelTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}
