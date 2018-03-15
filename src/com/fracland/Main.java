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

    public static void main(String[] args) {
        // Generate image
        IslandMass islands = new IslandMass(10, 100, 500);
        Matrix matrix = islands.matrix;
        matrix.normalise(255);
        BufferedImage image = new BufferedImage(islands.width,islands.height, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < islands.height; y++) {
            for (int x = 0; x < islands.width; x++) {
                int value = matrix.get(x,y);
                Color color = new Color(value, value, value);
                image.setRGB(x, y, color.getRGB());
            }
        }
        // Save image
        try{
            // Count already existing screenshots
            int screenshots = (int)Files.list(Paths.get("screenshots/")).count();
            String name = "./screenshots/screenshot-" + screenshots + ".png";
            File output = new File(name);
            ImageIO.write(resizeImage(image, BufferedImage.TYPE_INT_ARGB, (int)(((double)image.getWidth()/(double)image.getHeight())*256), 256), "png", output);
            System.out.println("Saved screenshot as " + name + " successfully.");
        }catch(IOException e){
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

}
