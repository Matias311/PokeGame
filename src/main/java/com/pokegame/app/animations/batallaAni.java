package com.pokegame.app.animations;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

public class batallaAni {

    public static void animarGolpe(JLabel objetivo) {
        int desplazamiento = 5, repeticiones = 4, delay = 80;
        Point posOriginal = objetivo.getLocation();
        Timer timer = new Timer(delay, null);

        timer.addActionListener(new java.awt.event.ActionListener() {
            int contador = 0; boolean arriba = true;

            @Override public void actionPerformed(java.awt.event.ActionEvent e) {
                int offsetY = arriba ? -desplazamiento : desplazamiento;
                objetivo.setLocation(posOriginal.x, posOriginal.y + offsetY);
                arriba = !arriba; contador++;
                if (contador >= repeticiones * 2) {
                    objetivo.setLocation(posOriginal);
                    timer.stop();
                }
            }
        });

        timer.start();
    }

    public static class AnimacionSlash extends JComponent {
        private BufferedImage spriteSheet;
        private final int frameHeight = 128, delayMs = 60;
        private final int[][] frames = {
                {216, 16, 0},
                {340, 24, 0},
                {444, 48, 0},
                {540, 80, 0},
                {20,  84, 128},
                {152, 80, 128},
                {280, 76, 128},
                {412, 12, 128},
                {444, 4,  128},
                {456, 4,  128}
        };
        private int frame = 0, escalaAncho, escalaAlto;

        public AnimacionSlash(String ruta, int x, int y, int escalaAncho, int escalaAlto) {
            this.escalaAncho = escalaAncho; this.escalaAlto = escalaAlto;
            try { spriteSheet = ImageIO.read(new File(ruta)); } catch (Exception e) { e.printStackTrace(); }

            setBounds(x, y, getMaxAncho() * escalaAncho, frameHeight * escalaAlto);

            new Timer(delayMs, e -> {
                frame++;
                if (frame >= frames.length) {
                    ((Timer) e.getSource()).stop();
                    Container parent = getParent();
                    if (parent != null) { parent.remove(this); parent.repaint(); }
                } else repaint();
            }).start();
        }

        private int getMaxAncho() {
            int max = 0;
            for (int[] f : frames) if (f[1] > max) max = f[1];
            return max;
        }

        @Override protected void paintComponent(Graphics g) {
            if (spriteSheet == null || frame >= frames.length) return;
            int sx = frames[frame][0], sw = frames[frame][1], sy = frames[frame][2];
            g.drawImage(spriteSheet, 0, 0, sw * escalaAncho, frameHeight * escalaAlto,
                    sx, sy, sx + sw, sy + frameHeight, this);
        }
    }
}
