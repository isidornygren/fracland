package com.fracland;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageGenerator {

    static double getSteepness(Matrix matrix, int x, int y){
        double steep = 0;
        // Add all neighbours to an array
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 1; j++){
                if(i != 0 && j != 0 && x + j >= 0 && y + i >= 0 && x + j <= matrix.width && y + i <= matrix.height){
                    steep = Math.max(steep, Math.abs(matrix.get(x, y) - matrix.get(x + j, y + i)));
                }
            }
        return steep;
    }

    static double[] getDirection(Matrix matrix, int x, int y, int n){
        double horizontal = 0;
        double vertical = 0;

        double point = matrix.get(x, y);

        for(int i = 1; i < n; i ++){
            if( (x - i) >= 0){
                horizontal += point - matrix.get(x - i, y);
            }
            if( (x + i) < matrix.width){
                horizontal += matrix.get(x + i, y) - point;
            }
            if( (y - i) >= 0){
                vertical += point - matrix.get(x, y - i);
            }
            if( (y + i) < matrix.height){
                vertical += matrix.get(x, y + i) - point;
            }
        }
        return new double[] {horizontal, vertical};
    }

    static Matrix generateShadowMap(Matrix matrix){
        Matrix shadows = new Matrix(matrix.width, matrix.height);
        for(int y = 0; y < matrix.height; y++)
            for (int x = 0; x < matrix.width; x++) {
                double[] direction = getDirection(matrix, x, y, 16);
                shadows.insert(x, y, (int)(direction[1]+(direction[0])));
            }
        shadows.normalise(100);
        return shadows;
    }

    static BufferedImage generate(Matrix matrix){
        matrix.normalise(255);

        BufferedImage image = new BufferedImage(matrix.width,matrix.height, BufferedImage.TYPE_INT_ARGB);

        Matrix shadows = generateShadowMap(matrix);

        for(int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {
                int value = matrix.get(x,y);
                Color color;
                double vert = ((double)shadows.get(x, y) + 100.0)/200.0; // 0 = shadow 1 = lit
                if(value < 50){ // Water
                    color = new Color(value,value + 75,200);
                }else if(value < 150){  // Ground
                    value = (int)(value*vert);  // Add shadows
                    if(getSteepness(matrix, x, y) > 150){
                        color = new Color(value, value, value);
                    }else
                        color = new Color(value/2, value, value/2);
                }else{  // mountain
                    value = (int)(value*vert); // Add shadows
                    color = new Color(value, value, value);
                }
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }
}
