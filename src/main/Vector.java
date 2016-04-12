package main;

import java.util.function.Function;
import java.util.Arrays;

public class Vector extends Matrix {
    public final int length;

    private Vector(double[][] matrix) {
        super(matrix);
        this.length = matrix.length;
    }

    public static Vector from_matrix(Matrix matrix) {
        assert matrix.getNumCols() == 1;
        return new Vector(matrix.data);
    }

    public static Vector from_data(double... data) {
        double[][] matrix = new double[data.length][1];
        for (int i = 0; i < data.length; i += 1) {
            matrix[i][0] = data[i];
        }
        return new Vector(matrix);
    }

    public double square_magnitude() {
        return Arrays.stream(data)
            .map(d -> d[0] * d[0])
            .reduce(0.0, Double::sum);
    }

    public double magnitude() {
        return Math.sqrt(square_magnitude());
    }

    public Vector map(Function<Double, Double> mapper) {
        return new Vector(super.map(mapper).data);
    }

    public Vector norm() {
        return new Vector(divide_scalar(magnitude()).data);
    }

    public double dot(Vector other) {
        assert this.length == other.length;
        double sum = 0.0;
        for (int i = 0; i < this.length; i += 1) {
            sum += this.data[i][0] * other.data[i][0];
        }
        return sum;
    }

    public double[] into_array() {
        double[] array = new double[this.length];
        for (int i = 0; i < this.length; i += 1) {
            array[i] = this.data[i][0];
        }
        return array;
    }
}
