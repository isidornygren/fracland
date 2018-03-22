package com.fracland;

import java.util.Random;

public class IslandMass {
    public Matrix matrix;
    public int height, width;

    /**
     * Generates a mass of islands.
     * Creates a initial angle from the first generated island and adds the next island
     * a randomly generated distance from the first island across this line, and continues
     * this for n islands (preferable n <= 10).
     * Personally I've found that a deviation of 1/5th of the mean looks relatively okay.
     * @param n the number of islands to generate
     * @param sizeDeviation the normal deviation for the size of the island
     * @param sizeMean the normal mean of the island (diameter)
     */
    public IslandMass(int n, int sizeDeviation, int sizeMean){
        Island[] islands = new Island[n];
        Random random = new Random();

        for(int i = 0; i < n ; i++){
            int width = Math.abs((int)random.nextGaussian()*sizeDeviation + sizeMean);
            int height = Math.abs((int)random.nextGaussian()*sizeDeviation + sizeMean);
            islands[i] = new Island(width, height, 16, 255);
        }
        // Calculate starting angle of line
        double angle = random.nextDouble()*2*Math.PI;
        // Positions contains all the positions of the islands
        double[][] positions = new double[n][2];
        positions[0][0] = 0.0;
        positions[0][1] = 0.0;
        // Minimum values for the islands border inside a new grid [x,y]
        double[] minPos = {0.0,0.0};
        double[] maxPos = {islands[0].width,islands[0].height};
        for(int i = 1; i < n; i++){
            // Get a random position from the angle and the size of the previous island
            double x = Math.cos(angle)*Math.abs(random.nextGaussian()*islands[i].width/5);
            double y = Math.sin(angle)*Math.abs(random.nextGaussian()*islands[i].height/5);
            positions[i][0] = positions[i-1][0] + x;
            positions[i][1] = positions[i-1][1] + y;
            // Calculate the smallest and the largest position on a new grid
            minPos[0] = Math.min(minPos[0], positions[i][0]);
            minPos[1] = Math.min(minPos[1], positions[i][1]);
            maxPos[0] = Math.max(maxPos[0], positions[i][0] + (double)islands[i].width);
            maxPos[1] = Math.max(maxPos[1], positions[i][1] + (double)islands[i].height);
            // Calculate the next angle
            angle = (angle + random.nextGaussian()*0.5);
        }
        // Builds island matrix
        width = (int)Math.ceil(maxPos[0] - minPos[0]);
        height = (int)Math.ceil(maxPos[1] - minPos[1]);
        // Create new island array
        matrix = new Matrix(width, height);
        for(int i = 0; i < n; i++){
            // Calculate each islands position from the top left corner instead
            int x = (int)(positions[i][0]);
            int y = (int)(positions[i][1]);
            matrix.add(islands[i].getMatrix(), x + -1*(int)minPos[0], y + -1*(int)minPos[1]);
        }
    }
}
