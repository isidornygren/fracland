package com.fracland.fractal;

import com.fracland.Matrix;

public class Julia extends fractal{
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
        matrix = new Matrix(width, height);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                // The points for the Julia set should be generated between -2 and 2 for x, and -2 and 2 for y
                double real = ((double)x/(double)width)*4 - 2;
                double imaginary = ((double)y/(double)height)*4 - 2;
                int point = generatePoint(real, imaginary, cx, cy);
                matrix.insert(x, y, point);
            }
        }
    }
}
