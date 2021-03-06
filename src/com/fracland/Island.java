package com.fracland;

import com.fracland.fractal.Julia;
import com.fracland.fractal.Mandelbrot;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Island {
    private Matrix matrix;
    private double EDGE_BLEED = 0.75; // how much relative area to cover away from the edge of the mandelbrot to generate the julia sets
    public int width, height;

    /**
     * Generate a new island with the fractal island generator
     * @param width int width of the map of the island
     * @param height int height of the map of the island
     * @param n number of fractal generations to pass the island through (5-10 gives a random looking island)
     * @param maxHeight the maximum value of a point in the island (will be normalised to this value)
     */
    public Island(int width, int height, int n, int maxHeight){
        if(n == 0)
            throw new IllegalArgumentException("n needs to be higher than 0");
        Mandelbrot mandel = new Mandelbrot(width, height);
        this.width = width;
        this.height = height;

        ArrayList<Coordinate> edges = mandel.getEdgeArray(Math.max(height, width)*EDGE_BLEED);

        // Get a random edge point on the mandelbrot set
        Coordinate coordinate = edges.get(ThreadLocalRandom.current().nextInt(0,edges.size()));

        // Generate the first julia set
        Julia julia = new Julia(width, height, coordinate.x, coordinate.y);
        julia.matrix.normalise(maxHeight);

        // Values between 5 and 10 seem to generate the best looking islands
        for(int i = 0; i < (n - 1); i++){
            Coordinate coordinateB = edges.get(ThreadLocalRandom.current().nextInt(0,edges.size()));

            Julia juliaB = new Julia(width, height, coordinateB.x, coordinateB.y);
            juliaB.matrix.normalise(maxHeight);
            julia.matrix.add(juliaB.matrix);
        }
        julia.matrix.normalise(maxHeight);

        matrix = julia.matrix;
    }
    public Matrix getMatrix(){
        return matrix;
    }
}
