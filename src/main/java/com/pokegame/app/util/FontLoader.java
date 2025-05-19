package com.pokegame.app.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FontLoader {

    public static Font loadPixelFont(float size) {
        try {

            InputStream is = FontLoader.class.getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (is == null) {
                System.err.println("No se pudo cargar la fuente pixel_font.ttf");
                return new Font("Arial", Font.PLAIN, (int)size);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int)size);
        }
    }
}



