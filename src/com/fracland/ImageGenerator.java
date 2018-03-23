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
                if(value < 50) { // Water
                    if(value > 30)
                        color = new Color(103, 172, 214);
                    else if(value > 10)
                        color = new Color(65, 124, 201);
                    else
                        color = new Color(56, 103, 175 );
                }else if (value < 65 && getSteepness(matrix, x, y) < 25){  // Sand
                    //color = new Color((int)(242*vert), (int)(223*vert), (int)(152*vert));
                    color = new Color(242, 223, 152);
                }else if(value < 150){  // Ground
                    if(getSteepness(matrix, x, y) > 40){
                        //color = new Color(102, 136, 59);
                        if(vert < 0.3) {
                            color = new Color(63, 73, 75); // dark mountain shadow
                        }else if(vert < 0.5){
                            color = new Color(74, 89, 100); // mountain shadow
                        }else
                            color = new Color(181, 112, 72);
                    }else if(value < 90){
                        if(vert < 0.5) {  // Grassland shadow
                            color = new Color(122, 166, 71);
                        }else {
                            color = new Color(183, 183, 71);
                        }
                    }
                    else{
                        if(vert < 0.5) {  // Grassland shadow
                            color =  new Color(67, 140, 114);
                        }else{
                            color = new Color(122, 166, 71);
                        }
                    }
                }else if(value < 200){  // mountain
                    if(vert < 0.3) {
                        color = new Color(63, 73, 75); // dark mountain shadow
                    }else if(vert < 0.5){
                        color = new Color(74, 89, 100); // mountain shadow
                    }else
                        color = new Color(181, 112, 72);
                }else{  // Snow
                    if(vert < 0.5){
                        color = new Color(150, 200, 255); // snow shadow
                    }else
                        color = new Color(225, 255, 255);
                }
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }
}
