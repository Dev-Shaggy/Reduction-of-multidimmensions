package shaggydev.models;

import java.util.List;

public class Neuron {

    private double [] weights;

    public Neuron(int inputSize){
        weights = new double[inputSize];

        for(int i=0;i<weights.length;i++){
            weights[i]= Math.random();
        }
    }
    public double getValue(List<Double> inputVector){
        double sum=0.0;

        for(int i=0; i<weights.length;i++){
            sum+=weights[i]*inputVector.get(i);
        }
        return sum;
    }

    public double getValue(double [] inputVector){
        double sum=0.0;

        for(int i=0; i<weights.length;i++){
            sum+=weights[i]*inputVector[i];
        }
        return sum;
    }


    public void increaseWeights(double alpha){
        for(int i=0;i<weights.length;i++){
            weights[i]+=alpha*0.001*weights[i];
        }
    }
}
