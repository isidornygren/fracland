package com.fracland;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        int width = 1000, height = 1000;
        // Generate image
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Island island = new Island(width, height, 50, 255);
        Matrix matrix = island.getMatrix();

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = matrix.get(x,y);
                Color color = new Color(value, value, value);
                image.setRGB(x, y, color.getRGB());
            }
        }

        JFrame frame = new JFrame();
        frame.setSize(width,height);
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
