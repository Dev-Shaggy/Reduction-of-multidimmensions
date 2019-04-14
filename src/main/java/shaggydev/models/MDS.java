package shaggydev.models;

public class MDS implements Runnable{
    private static MDS ourInstance = new MDS();

    public static MDS getInstance() {
        return ourInstance;
    }

    private MDS() {
    }

    private double[][] _matrix;
    private double[][] _coordinates;
    private double sumDist;
    private int integer;

    public void setData(double[][] input){
        _matrix = new double[input.length][input.length];


        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                _matrix[j][i] = _matrix[i][j] = euclideanDistance(i, j);
            }
        }


        integer=0;
        _coordinates = new double[input.length][2];

        for (int i = 0; i < _coordinates.length; i++) {
            _coordinates[i][0] = Math.random() * 10 - 5.0;
            _coordinates[i][1] = Math.random() * 10 - 5.0;
        }
        getSumDist();
    }

    private void getSumDist() {
        sumDist = 0.0;
        for (int i = 0; i < _coordinates.length; i++) {
            for (int j = i; j < _coordinates.length; j++) {
                sumDist += _matrix[i][j];
            }
        }
    }

    private double euclideanDistance(int x, int y) {

        double distance = 0.0;

        for (int i = 0; i < _matrix[x].length; i++) {
            distance += Math.pow((_matrix[x][i] - _matrix[y][i]), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    private double eu2dim(double[] x1, double[] x2) {
        return Math.sqrt(Math.pow((x1[0] - x2[0]), 2) + Math.pow((x1[1] - x2[1]), 2));
    }

    private double stress() {
        double stress = 0.0;

        for (int i = 0; i < _coordinates.length; i++) {
            for (int j = 0; j < _coordinates.length; j++) {
                stress += Math.pow(_matrix[i][j] - eu2dim(_coordinates[i], _coordinates[j]), 2);
            }
        }
        stress /= sumDist;
        stress = Math.sqrt(stress);

        return stress;
    }

    private void distINC(int i, int j) {

        double diff = Math.sqrt(eu2dim(_coordinates[i], _coordinates[j]));

        if (Math.abs(_coordinates[i][0] - _coordinates[j][0]) <= 0.01) {
            if (_coordinates[i][0] > _coordinates[j][0]) {
                _coordinates[j][1] -= diff;
            } else {
                _coordinates[j][1] += diff;
            }
        } else {
            double a = Math.atan((_coordinates[j][1] - _coordinates[i][1]) / (_coordinates[j][0] - _coordinates[i][0]));

            if (_coordinates[i][0] > _coordinates[j][0]) {
                _coordinates[j][0] -= Math.cos(a) * diff;
                _coordinates[j][1] -= Math.sin(a) * diff;
            } else {
                _coordinates[j][0] += Math.cos(a) * diff;
                _coordinates[j][1] += Math.sin(a) * diff;
            }
        }
    }

    private void distDEC(int i, int j) {
        double diff;

        diff = Math.sqrt(eu2dim(_coordinates[i], _coordinates[j]));

        double a = Math.atan((_coordinates[j][1] - _coordinates[i][1]) / (_coordinates[j][0] - _coordinates[i][0]));

        if (_coordinates[i][0] < _coordinates[j][0]) {
            _coordinates[j][0] -= Math.cos(a) * diff;
            _coordinates[j][1] -= Math.sin(a) * diff;
        } else {
            _coordinates[j][0] += Math.cos(a) * diff;
            _coordinates[j][1] += Math.sin(a) * diff;
        }
    }


    public double[][] getCoordinates() {
        return _coordinates;
    }


    @Override
    public void run() {
        do {
            integer++;
            for (int i = 0; i < _coordinates.length; i++) {
                for (int j = i + 1; j < _coordinates.length; j++) {

                    double diff = eu2dim(_coordinates[i], _coordinates[j]) / _matrix[i][j];

                    double val = 1.0;
                    if (diff >= val) {
                        distDEC(i, j);
                    } else if (diff < val) {
                        distINC(i, j);
                    }
                }
            }

        } while (stress() > 1.1 && integer < 100);
    }
}
