package main;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the "Convergence of the power method" problem.
 *
 * @version 1.0
 * @author Joshua Songy
 */
public class PowerConvergence {

	// The constant matrix A given in the PDF.
	private static final Matrix A =
		new Matrix(
			new double[][] {{-2.0, 1.0,  2.0},
							{ 0.0, 2.0,  3.0},
							{ 2.0, 1.0, -2.0}}
			);

	// The tolerance to calculate to.
	private static final double tolerance = 0.00005;

	// The number of random matrices to generate.
	private static final int numRandomMatrices = 200;

	// Dimensions of random matrices.
	private static final int randomMatrixWidth = 2;
	private static final int randomMatrixHeight = 2;

	// Range of random matrices.
	private static final double randomMatrixUpperBound = 2.0;
	private static final double randomMatrixLowerBound = -2.0;

	/**
	 * Generate a random integer within the given range.
	 *
	 * @argument lower The lower bound of the range.
	 * @argument upper The upper bound of the range.
	 * @return A random double between upper and lower, uniformly distributed.
	 */
	private static double randomInRange(double lower, double upper) {

		// Calculate the range.
		double range = upper - lower;

		// Generate a random number across the range.
		double num = range * Math.random();

		// Add the lower number to offset the number into the correct range.
		return num + lower;
	}

	/**
	 * Generates a random 2x2 matrix of doubles.
	 *
	 * @argument length The length of the random vector.
	 * @argument lower The lower bound of the elements.
	 * @argument upper The upper bound of the elements.
	 * @return A vector filled with random doubles in the given range.
	 */
	private static Matrix randomMatrix() {

		// Used to invalidate bad matrices.
		double determinant = 0.0;

		// Construct the matrix to fill.
		Matrix result = new Matrix(3, 3);

		while (Math.abs(determinant) < tolerance) {
			// Set every element of the vector to a random element.
			for (int i = 0; i < randomMatrixHeight; i++) {
				for (int j = 0; j < randomMatrixWidth; j++) {
					result.set(
						i, j,
						randomInRange(
							randomMatrixLowerBound,
							randomMatrixUpperBound
						)
					);
				}
			}

			// Re-calculate the determinant.
			determinant = result.determinant();
		}

		// Return the constructed vector.
		return result;
	}

	/**
	 * Generate and store the list of random vectors for this run of the problem.
	 *
	 * @return The resulting list of vectors.
	 */
	private static List<Matrix> randomMatrices() {

		// Create backing list for the vectors.
		List<Matrix> randomMatrices = new ArrayList<Matrix>();

		// Generate every random vector and add it to the list.
		for (int i = 0; i < numRandomMatrices; i++) {
			randomVectors.add(randomMatrices());
		}

		// return the results.
		return randomMatrices;
	}

	/**
	 * Private constructor for internal use.
	 */
	private IterativeConvergence() {}

	/**
     * Inverts a 2x2 matrix.
     *
     * @argument matrix The matrix to invert.
     * @return The inverted matrix.
     */
	public static Matrix tranverse(Matrix matrix) {

		// For readability.
		double a = matrix.get(0, 0);
		double b = matrix.get(0, 1);
		double c = matrix.get(1, 0);
		double d = matrix.get(1, 1);
		
		// Return the transverse.
		return new Matrix(
			{{       d, -1.0 * b},
			 {-1.0 * c,        a}}
		).multiply_scalar(
			1.0 / (a * d - b * c)
		);
	}

	public static void Part1() {
		List<Matrix> randomMatrices = randomMatrices();
	}

	public static void main(String[] args) {
		Part1();
	}
}
