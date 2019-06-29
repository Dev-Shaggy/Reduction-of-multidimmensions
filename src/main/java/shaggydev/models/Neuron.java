package shaggydev.models;

import java.util.List;
import java.util.Random;

public class Neuron {

    private double[] weights;

    public Neuron(int inputSize) {
        weights = new double[inputSize];

        resetWeights();
    }

    public void resetWeights() {
        Random r = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = r.nextDouble();
        }
    }

    public void increaseWeights(double[] input, double dist, double alpha) {

        double fdist;

        //Liniowa
        if (dist == 0) fdist = 1.0;
        else fdist = 1.0 / dist;

        //Prostokątna
//        fdist=1;


        for (int i = 0; i < weights.length; i++) {
            double val = fdist * alpha * (input[i] - weights[i]);
            weights[i] += val;
        }
    }


    public double getDistance(double[] vector) {
        double d = 0.0;

        for (int i = 0; i < vector.length; i++) {
            d += Math.pow(vector[i] - weights[i], 2);
        }
        return Math.sqrt(d);
    }
}
