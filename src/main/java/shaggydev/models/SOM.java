package shaggydev.models;

import java.util.*;

public class SOM {
    private static SOM ourInstance = new SOM();

    public static SOM getInstance() {
        return ourInstance;
    }

    private SOM() {
    }

    private Neuron[][] map;
    private double[][] inputVectors;
    private int size;
    double alpha = 0.2;

    public double getDiff(int col, int row, double[] vector) {
        return map[row][col].getDistance(vector);
    }

    public void createMap(int size, double[][] input) {
        this.size = size;
        inputVectors = input;
        map = new Neuron[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Neuron(inputVectors[0].length);
            }
        }


    }

    public Point findBMU(double[] vector) {
        Point p = new Point(0, 0);
        double min = 1000.0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (min > map[i][j].getDistance(vector)) {
                    p.x = i;
                    p.y = j;
                    min = map[i][j].getDistance(vector);
                }
            }
        }
        return p;
    }

    public double getDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private List<double[]> shufleInput(){
        List<double[]> tmp= new ArrayList<>();

        for(double [] d: inputVectors){
            tmp.add(d);
        }

        Collections.shuffle(tmp);
        return tmp;
    }



    public void step() {

        List<double[]> data = shufleInput();
        Point p;
        double dist=0;
//        for (int i = 0; i < inputVectors.length; i++) {
//            p = findBMU(inputVectors[i]);
//
//            for (int y = 0; y < size; y++) {
//                for (int x = 0; x < size; x++) {
//                    dist = getDistance(new Point(x,y),p);
//                    if(dist<=4.0){
//                        map[x][y].increaseWeights(inputVectors[i],dist,alpha);
//                    }
//                }
//            }
//        }

        for(int i=0;i<data.size();i++){
            p = findBMU(data.get(i));
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    dist = getDistance(new Point(x,y),p);
                    if(dist<=4.0){
                        map[x][y].increaseWeights(data.get(i),dist,alpha);
                    }
                }
            }
        }
    }

    public void reset() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j].resetWeights();
            }
        }
    }
}
