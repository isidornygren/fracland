package com.julia_island;

public class fractal {
    protected int[][] set;
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
