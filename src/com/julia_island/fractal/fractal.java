package com.julia_island.fractal;

import com.julia_island.Matrix;

public class fractal {
    protected int[][] set;
    public Matrix matrix;
    protected int width, height;
    public static int MAX_ITERATION = 1000;

    public int[][] getSet(){
        return set;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
