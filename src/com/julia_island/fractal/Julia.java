package com.julia_island.fractal;

import com.julia_island.Matrix;

public class Julia extends fractal{
    private int maxPoint = 0;

    private static int generatePoint(double real, double imaginary, double cx, double cy){
        int iteration = 0;
        while(real*real + imaginary*imaginary < 4 && iteration < MAX_ITERATION){
            double temp = real*real - imaginary*imaginary;
            imaginary = 2*real*imaginary + cy;
            real = temp + cx;

            iteration++;
        }
        return iteration;
    }

    public Julia(int width, int height, double cx, double cy){
        this.width = width;
        this.height = height;
        // set = new int[width][height];
        matrix = new Matrix(width, height);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                // The points for the Julia set should be generated between -2 and 2 for x, and -2 and 2 for y
                double real = ((double)x/(double)width)*4 - 2;
                double imaginary = ((double)y/(double)height)*4 - 2;
                int point = generatePoint(real, imaginary, cx, cy);
                // set[x][y] = point;
                matrix.insert(x, y, point);
                // maxPoint = Math.max(point, maxPoint);
            }
        }
    }

    /*public void normalise(int value){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                set[x][y] = (int)((double)set[x][y]/(double)maxPoint*value);
            }
        }
        maxPoint = value;
    }

    /**
     * Adds two julia sets together
     * @param julia the other julia set to add
     */
    /*public void add(Julia julia){
        if(julia.width != width || julia.height != height)
            throw new IllegalArgumentException();
        int[][] setB = julia.getSet();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                set[x][y] = set[x][y] + setB[x][y];
            }
        }
        this.maxPoint += julia.maxPoint;
    }*/
}
