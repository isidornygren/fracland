package com.fracland;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static void saveImage(BufferedImage image){
        try{
            // Count already existing screenshots
            int screenshots = (int)Files.list(Paths.get("screenshots/")).count();
            String name = "./screenshots/screenshot-" + screenshots + ".png";
            File output = new File(name);
            ImageIO.write(image, "png", output);
            System.out.println("Saved screenshot as " + name + " successfully.");
        }catch(IOException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Generate image
        IslandMass islands = new IslandMass(10, 64, 256);
        Matrix matrix = islands.matrix;
        BufferedImage image = ImageGenerator.generate(matrix);

        // Print the image to a display
        ImageIcon icon=new ImageIcon(image);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(matrix.width,matrix.height);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        saveImage(image);
    }
}
