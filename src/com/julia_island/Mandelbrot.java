package com.julia_island;

import java.util.ArrayList;

public class Mandelbrot extends fractal {

    private static int generatePoint(double real, double imaginary){
        int iteration = 0;
        double x = 0.0;
        double y = 0.0;
        while(x*x + y*y < 4 && iteration < MAX_ITERATION){
            double temp = x*x - y*y + real;
            y = 2*x*y + imaginary;
            x = temp;
            iteration++;
        }
        return iteration;
    }

    /**
     * Returns a list of coordinates in/around the mandelbrot set with an iteration value above
     * another value.
     * @param value The value to filter the mandelbrot set with
     * @return Arraylist of coordinates with values
     */
    public ArrayList<Coordinate> getEdgeArray(double value){
        ArrayList<Coordinate> edgeArray = new ArrayList<>();
        // only keep a value above a specific value
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                if(set[x][y] > value && set[x][y] < fractal.MAX_ITERATION){
                    edgeArray.add(new Coordinate((double)x/(double)width*3.5 - 2.5, (double)y/(double)height*2 - 1));
                }
            }
        }
        return edgeArray;
    }

    public Mandelbrot(int width, int height){
        this.width = width;
        this.height = height;

        set = new int[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                // Normalise coordinates
                double real = (double)x/(double)width*3.5 - 2.5;
                double imaginary = (double)y/(double)height*2 - 1;
                set[x][y] = generatePoint(real, imaginary);
            }
        }
    }
}
