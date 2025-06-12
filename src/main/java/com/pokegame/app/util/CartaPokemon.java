package com.pokegame.app.util;

import com.pokegame.app.modelo.Imagen;
import com.pokegame.app.modelo.Pokemon;
import com.pokegame.app.util.ImagenCache;
import com.pokegame.app.gui.InformacionPokemon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/** CartaPokemon base abstracta. */
public abstract class CartaPokemon extends JPanel {

    public CartaPokemon(Pokemon pokemon, Imagen imagen) {
        setPreferredSize(new Dimension(150, 150));
        setLayout(new BorderLayout());
        setOpaque(false);

        // Nombre del Pokémon
        JLabel nombrePokemon = new JLabel(pokemon.getNombre(), SwingConstants.CENTER);
        nombrePokemon.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
        nombrePokemon.setFont(new Font("Arial", Font.BOLD, 16));
        add(BorderLayout.SOUTH, nombrePokemon);

        // Imagen del Pokémon
        JLabel imageLabel = new JLabel();
        add(BorderLayout.CENTER, imageLabel);

        try {
            ImagenCache.getImage(imagen.getImagenFrente(), imageIcon -> {
                imageLabel.setIcon(imageIcon);
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                imageLabel.setVerticalAlignment(JLabel.CENTER);
                imageLabel.revalidate();
                imageLabel.repaint();
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        // Evento de mouse para abrir la información del Pokémon
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new InformacionPokemon(pokemon.getId(), imagen.getImagenFrente());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        int shadowSize = 2;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(231, 232, 227));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }
}
