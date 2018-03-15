package com.fracland;

public class Matrix {
    private int[][] set;
    public int width, height;
    private int max = 0;

    public Matrix(int width, int height){
        set = new int[width][height];
        this.width = width;
        this.height = height;
    }
    public void insert(int x, int y, int value){
        if(x > width || y > height)
            throw new IllegalArgumentException("parameter out of bounds");
        set[x][y] = value;
        max = Math.max(max, value);
    }
    public int get(int x, int y){
        if(x > width || y > height)
            throw new IllegalArgumentException("parameter out of bounds");
        return set[x][y];
    }

    public void add(Matrix b){
        if(b.width != width || b.height != height)
            throw new IllegalArgumentException();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int value = get(x, y) + b.get(x,y);
                insert(x, y, value);
            }
        }
    }
    public void add(Matrix b, int dx, int dy){
        if      (dx < 0 || (b.width  + dx) > width ||
                 dy < 0 || (b.height + dy) > height)
            throw new IllegalArgumentException("Tried inserting a matrix outside of another matrix: " +
                    dx + "," + dy + "," + (b.width + dx) + "," + (b.height + dy));
        for(int y = 0; y < b.height; y++){
            for(int x = 0; x < b.width; x++){
                int value = get(x + dx, y + dy) + b.get(x,y);
                insert(x + dx, y + dy, value);
            }
        }
    }
    public void addMax(Matrix b){
        if(b.width != width || b.height != height)
            throw new IllegalArgumentException();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int value = Math.max(get(x, y),b.get(x,y));
                insert(x, y, value);
            }
        }
    }
    public void addProduct(Matrix b){
        if(b.width != width || b.height != height)
            throw new IllegalArgumentException();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int value = get(x, y)*b.get(x,y);
                insert(x, y, value);
            }
        }
    }
    public void normalise(int value){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int newValue = (int)(((double)get(x,y)/(double)max)*value);
                set[x][y] = newValue;
            }
        }
        max = value;
    }
    public String toString(){
        String ret = "";
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                ret += get(x,y) + " ";
            }
            ret += "\n";
        }
        return ret;
    }
}
