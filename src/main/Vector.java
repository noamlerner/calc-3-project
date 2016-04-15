package main;

import java.util.function.Function;
import java.util.Arrays;

public class Vector extends Matrix {
    public final int length;

    private Vector(double[][] matrix) {
        super(matrix);
        this.length = matrix.length;
    }

    public Vector(Matrix matrix) {
        super(matrix);
        this.length = matrix.M;
    }

    /**
     * Copy constructor
     * @param vector to be copied
     */
    public Vector(Vector vector) {
        super(vector.data);
        this.length = vector.length;
    }

	/**
     * Constructs a vector filled with zeros.
	 * 
     * @argument length The length of the new vector.
     */
	public Vector(int length) {

		// Populate the backing data.
		super(new Matrix(length, 1));

		// Record length.
		this.length = length;
	}

	/**
     * Constructs a vector filled with passed in elements.
     *
     * @argument data The elements of the vector.
     */
	public Vector(double[] data) {
		// Dirty hack.
		super(new Matrix(data.length, 1));

		// Set our length.
		this.length = data.length;

		// Copy over elements.
		for (int i = 0; i < this.length; i++) {
			this.data[i][0] = data[i];
		}
	}

    public static Vector from_matrix(Matrix matrix) {
        assert matrix.getNumCols() == 1;
        return new Vector(matrix.data);
    }

    public static Vector identity(int N) {
        assert N > 0;
        double[][] matrix = new double[N][1];
        matrix[0][0] = 1;
        for (int i = 1; i < N; i += 1) {
            matrix[i][0] = 0;
        }
        return new Vector(matrix);
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

    public Vector multiply_scalar(double factor) {
        return new Vector(super.multiply_scalar(factor).data);
    }

    public Vector plus(Vector other) {
        return new Vector(super.plus(other).data);
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

	/**
     * Returns an element of the vector.
     *
     * @argument index The index of the element.
     * @return The element of the vector at index.
	 */
	public double get(int index) {
		return this.data[index][0];
	}

	/**
     * Set an element of the vector.
     *
     * @argument index The index of the element.
     * @argument value The value to set the element to.
	 */
	public void set(int index, double value) {
		this.data[index][0] = value;
	}
}
