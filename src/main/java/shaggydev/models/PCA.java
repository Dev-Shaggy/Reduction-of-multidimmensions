package shaggydev.models;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class PCA implements Runnable {
    private static PCA ourInstance = new PCA();

    public static PCA getInstance() {
        return ourInstance;
    }

    private PCA() {
    }

    private Matrix baseMatrix;
    private Matrix finalMatrix;
    private EigenvalueDecomposition eig;


    public void setData(double[][] input) {
        this.baseMatrix = new Matrix(input);
        Matrix correlationMatrix = new Matrix(this.createCorrelationTable(input));
        this.eig = correlationMatrix.eig();
    }

    private double[][] createCorrelationTable(double[][] input) {
        double[][] tmpCorrelationTable = new double[input[0].length][input[0].length];
        double[] avgValTable = this.countAvgValues(input);

        double SD1, SD2, COV, valA, valB;

        for (int i = 0; i < input[0].length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (i == j) tmpCorrelationTable[i][j] = 1.0;
                else {
                    SD1 = 0.0;
                    SD2 = 0.0;
                    COV = 0.0;

                    for (double[] doubles : input) {
                        valA = doubles[i] - avgValTable[i];
                        valB = doubles[j] - avgValTable[j];
                        SD1 += Math.pow(valA, 2);
                        SD2 += Math.pow(valB, 2);
                        COV += valA * valB;
                    }

                    SD1 /= input.length;
                    SD2 /= input.length;
                    COV /= input.length;

                    if (SD1 == 0 || SD2 == 0) {
                        tmpCorrelationTable[j][i] = 0.0;
                    } else {
                        SD1 = Math.sqrt(SD1);
                        SD2 = Math.sqrt(SD2);

                        tmpCorrelationTable[i][j] = COV / (SD1 * SD2);
                        tmpCorrelationTable[j][i] = COV / (SD1 * SD2);
                    }
                }
            }
        }
        return tmpCorrelationTable;
    }

    private double[] countAvgValues(double[][] input) {
        double[] tmpAvgValues = new double[input[0].length];
        for (int i = 0; i < tmpAvgValues.length; i++) {
            tmpAvgValues[i] = 0.0;
        }

        System.out.println();
        for (double[] doubles : input) {
            for (int j = 0; j < doubles.length; j++) {
                tmpAvgValues[j] += doubles[j];
            }
        }
        for (int i = 0; i < tmpAvgValues.length; i++) {
            tmpAvgValues[i] /= input.length;
        }
        return tmpAvgValues;
    }

    private Matrix getVectors() {
        return this.eig.getV();
    }

    private Matrix getValues() {
        return this.eig.getD();
    }


    private Matrix getMostSignifantColumns() {

        double[][] eigValTab = this.getValues().getArray();
        double[] percentageValue = new double[eigValTab.length];

        int[] tableColumnID = new int[2];

        double sum = 0.0;
        for (int i = 0; i < percentageValue.length; i++) {
            sum += eigValTab[i][i];
        }
        for (int i = 0; i < percentageValue.length; i++) {
            percentageValue[i] = eigValTab[i][i] / sum;
        }

        for (int i = 0; i < 2; i++) {
            double max = 0;
            int maxid = 0;
            for (int j = 0; j < percentageValue.length; j++) {

                if (max < percentageValue[j]) {
                    max = percentageValue[j];
                    tableColumnID[i] = j;
                    maxid = j;
                }
            }
            percentageValue[maxid] = 0.0;
        }
        return this.getVectors().getMatrix(0, eigValTab.length - 1, tableColumnID);
    }

    private Matrix getFinalMatrix() {
        return this.baseMatrix.times(this.getMostSignifantColumns());
    }

    public double[][] getCoordinates() {
        return finalMatrix.getArray();
    }


    @Override
    public void run() {
        finalMatrix = getFinalMatrix();
    }
}
