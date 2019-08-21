package shaggydev.models;

import java.util.Random;

class Neuron {

    private double[] weights;

    Neuron(int inputSize) {
        weights = new double[inputSize];

        resetWeights();
    }

    void resetWeights() {
        Random r = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = r.nextDouble();
        }
    }

    void increaseWeights(double[] input, double dist, double alpha) {

        double fdist = dist == 0 ? 1.0 : 1.0 / dist;

        //TODO Znaleźć lepszą funkcję sąsiedztwa

        for (int i = 0; i < weights.length; i++) {
            double val = fdist * alpha * (input[i] - weights[i]);
            weights[i] += val;
        }
    }


    double getDistance(double[] vector) {
        double d = 0.0;

        for (int i = 0; i < vector.length; i++) {
            d += Math.pow(vector[i] - weights[i], 2);
        }
        return Math.sqrt(d);
    }
}
