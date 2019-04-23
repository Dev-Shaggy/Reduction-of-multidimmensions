package shaggydev.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SOM implements Runnable{
    private static SOM ourInstance = new SOM();

    public static SOM getInstance() {
        return ourInstance;
    }

    private SOM() {
    }

    private Neuron [][] map;
    private double [][] inputVectors;
    private int x,y;
    private double lambda;
    private int radius_size;


    private int epoch;

    public void createMap(int size, double [][] input){

        inputVectors = input;
        map = new Neuron[size][size];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map.length;j++){
                map[i][j]=new Neuron(inputVectors[0].length);
            }
        }
        radius_size=size;
        getLambda(radius_size);
        epoch=0;
    }
    private void getLambda(int size){
        lambda = Math.sqrt(size);
    }

    private void findWinner(double[] inputVector) {
        double max = 0.0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                double val = map[i][j].getValue(inputVector);
                if (max <= val) {
                    max = val;
                    x = i;
                    y = j;
                }
            }
        }
    }
    public Point getWinner(double[] input){
        double max = 0.0;
        Point p = new Point(0,0);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                double val = map[i][j].getValue(input);
                if (max <= val) {
                    max = val;
                    p.x=i;
                    p.y=j;
                }
            }
        }
        return p;
    }


    private void increaseWeights(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map.length;j++){
                if(distanceTo(i,j)<=lambda){
                    map[i][j].increaseWeights(lambda-distanceTo(i,j));
                }
            }
        }
    }

    private double distanceTo(int x1,int y1){
        return Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2));
    }




    public void step(){
        for(int i=0;i<inputVectors.length;i++){
            findWinner(inputVectors[i]);
            increaseWeights();
        }
        epoch++;
    }
    public int getEpoch(){
        return epoch;
    }

    private void steps(){

        Random r = new Random();
        int x;
        for(int i=0;i<1000;i++){

            x = Math.abs(r.nextInt()%inputVectors.length);
            findWinner(inputVectors[x]);
            increaseWeights();
//            for(int j=0;j<inputVectors.length;j++){
//                findWinner(inputVectors[j]);
//                increaseWeights();
//            }



            if(radius_size>10 && radius_size%10==0){
                radius_size--;
                getLambda(radius_size);
            }
        }
    }


    @Override
    public void run() {
        steps();
    }
}
