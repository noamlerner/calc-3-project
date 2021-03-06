package main;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the "Convergence of the iterative methods" problem.
 *
 * @version 1.0
 * @author Joshua Songy
 */
public class IterativeConvergence {

	// The constant matrix A given in the PDF.
	private static final Matrix A =
		new Matrix(
			new double[][] {{1.0, 1.0 / 3.0, 1.0 / 9.0},
							{1.0 / 3.0, 1.0, 1.0 / 3.0},
							{1.0 / 9.0, 1.0 / 3.0, 1.0}}
			);

	// The constant vector B given in the PDF.
	private static final Vector B =
		new Vector(
			new double[] {0.9, 0.1, 0.3}
			);

	// The exact solution to the matrix equation given above, taken from PDF.
	private static final Vector exactSolution =
		new Vector(
			new double[] {39.0 / 40.0, -13.0 / 40.0, 12.0 / 40.0}
			);

	// A in part 2.
	private static final Matrix A2 =
		new Matrix(
			new double[][] {{1.0, 2.0},
							{2.0, 1.0}}
			);

	// B in part 2.
	private static final Vector B2 =
		new Vector(
			new double[] {3.0, 3.0}
			);

	private static final Vector exactSolution2 =
		new Vector(
			new double[] {1.0, 1.0}
			);

	// The initial guess to use for part 2.	
	private static final Vector initialGuess2 =
		new Vector(
			new double[] {10.0, 2.0}
			);

	// The length of random vectors we want to generate.
	private static final int randomVectorLength = 3;

	// The bounds of the random vectors we want to generate.
	private static final double randomVectorLowerBound = -10.0;
	private static final double randomVectorUpperBound = 10.0;

	// The number of random vectors to generate.
	private static final int numRandomVectors = 100;

	// The tolerance to calculate to.
	private static final double tolerance = 0.00005;

	// The max number of iterations to perform.
	private static final int maxIterations = 100;

	/**
	 * Holds private data on internal runs.
	 *
	 * @author Joshua Songy
	 * @version 1.0
	 */
	private class RunData {

		// The number of iterations it took to calculate the answer.
		public int iterations;

		// The initial guess.
		public Vector initial;

		// The calculated result.
		public Vector result;

		// The calculated error.
		public double error;

		// Whether this passed.
		public boolean success;
	}

	// Holds the data on the runs we have tried so far.
	private List<RunData> runData;

	// The iterative method we are using.
	private Iterative method;

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
	 * Generates a random vector of doubles.
	 *
	 * @argument length The length of the random vector.
	 * @argument lower The lower bound of the elements.
	 * @argument upper The upper bound of the elements.
	 * @return A vector filled with random doubles in the given range.
	 */
	private static Vector randomVector(int length, double lower, double upper) {

		// Construct the vector to fill.
		Vector result = new Vector(length);

		// Set every element of the vector to a random element.
		for (int i = 0; i < length; i++) {
			result.set(i, randomInRange(lower, upper));
		}

		// Return the constructed vector.
		return result;
	}

	/**
	 * Generate a random vector to specifications defined in the PDF.
	 *
	 * @return The random vector.
	 */
	private static Vector randomVector() {
		return randomVector(
			randomVectorLength,
			randomVectorLowerBound,
			randomVectorUpperBound
			);
	}

	/**
	 * Generate and store the list of random vectors for this run of the problem.
	 *
	 * @return The resulting list of vectors.
	 */
	private static List<Vector> randomVectors() {

		// Create backing list for the vectors.
		List<Vector> randomVectors = new ArrayList<Vector>();

		// Generate every random vector and add it to the list.
		for (int i = 0; i < numRandomVectors; i++) {
			randomVectors.add(randomVector());
		}

		// return the results.
		return randomVectors;
	}

	/**
	 * Private constructor for internal use.
	 * 
	 * @argument The iteration method to use.
	 */
	private IterativeConvergence(Iterative iterative) {

		// Allocate space for the run data.
		runData = new ArrayList<RunData>();

		// Record the method we are using.
		method = iterative;
	}

	/**
	 * Use the recorded iterative method to solve every initial guess.
	 * 
	 * @argument runs The initial guesses.
	 */
	private void solveAll(List<Vector> runs) {

		// Solve each one.
		for (Vector guess : runs) {
			solve(guess);
		}
	}

	/**
     * Returns {A|b}.
     *
     * @augment a The matrix.
     * @augment b The vector.
     * @return The augmented matrix.
     */
	private Matrix augment(Matrix a, Vector b) {

		// The augmented matrix.
		Matrix augmented = new Matrix(a.M, a.N + 1);

		// Copy the elements from A into the augmented matrix.
		for (int i = 0; i < a.M; i++) {
			for (int j = 0; j < a.N; j++) {
				augmented.set(i, j, a.get(i, j));
			}
		}

		// Copy the augmented section.
		for (int i = 0; i < a.M; i++) {
			augmented.set(i, a.N, b.get(i));
		}

		// Return the augmented matrix.
		return augmented;
	}

	/**
	 * Use the recorded iterative method to solve this inital guess.
	 *
	 * @argument guess The initial guess.
	 */
	private void solve(Vector guess) {

		// Create the container for the results.
		RunData data = new RunData();

		// Run the iterative over the guess.
		method.iterate(augment(A, B), guess, tolerance, maxIterations);

		// Record the results.
		data.success = !method.hasTimedOut();

		if (data.success) {
			data.error = method.getError();
			data.initial = guess;
			data.iterations = method.getIterations();
			data.result = method.getSolution();
		}

		// Push the results to the list of results.
		runData.add(data);
	}

	/**
	 * Calculate the approximate solution by averaging what we have calculated.
	 *
	 * @return The approximate solution vector.
	 */
	private Vector approx() {
		
		// The number of valid runs.
		double numRuns = 0;

		// The current approximate solution
		Vector approx = new Vector(3);

		// Calculate average.
		for (RunData data : runData) {

			// Don't factor in if it timed out.
			if (!data.success) {
				continue;
			}

			// Increment number of valid runs we have found.
			numRuns++;

			// Add the run to the running total
			for (int i = 0; i < 3; i++) {
				approx.set(i, approx.get(i) + data.result.get(i));
			}
		}

		// Average the run.
		for (int i = 0; i < 3; i++) {
			approx.set(i, approx.get(i) / numRuns);
		}

		// Return the average.
		return approx;
	}

	public static void Part1() {

		// Generate the list of vectors to run over.
		List<Vector> runs = randomVectors();

		// jacobi iterative runs.
		IterativeConvergence jacobi =
			new IterativeConvergence(
				new JacobiIterative()
				);

		// gauss seidel iterative runs.
		IterativeConvergence gauss =
			new IterativeConvergence(
				new GaussSeidelIterative()
				);

		// Perform the calculations in both.
		jacobi.solveAll(runs);
		gauss.solveAll(runs);

		// Calculate the average result for the runData of each.
		Vector approxJacobi = jacobi.approx();
		Vector approxGauss = gauss.approx();

		// Print solution so far.
		System.out.println(
			"Jacobi approximate solution:\n" +
			approxJacobi.toString()
			);

		System.out.println(
			"Jacobi approximate error:\n" +
			approxJacobi.minus(exactSolution).getMaxNorm()
			);

		System.out.println(
			"Gauss seidel approximate solution:\n" +
			approxGauss.toString()
			);

		System.out.println(
			"Gauss seidel approximate error:\n" +
			approxGauss.minus(exactSolution).getMaxNorm()
			);

		// The average ratio.
		double runRatio = 0.0;

		// The number of ratios given.
		double numRatios = 0.0;

		// Find each ratio.
		for (int i = 0; i < jacobi.runData.size(); i++) {

			if (jacobi.runData.get(i).success && gauss.runData.get(i).success) {
				double ratio = (double)jacobi.runData.get(i).iterations
					/ (double) gauss.runData.get(i).iterations;
				System.out.println("Ratio for vector " + i + " is: " + ratio);

				runRatio += ratio;
				numRatios++;
			}
		}

		System.out.println("Average ratio is: " + (runRatio / numRatios));
	}


	/**
     * Return the error from calculating the iteration in part 2, with M max 
     * retries.
     *
     * @argument maxIterations M
     * @return The error.
	 */
	private double calculateError(int maxIterations) {
		// Run the iterative over the guess.
		method.iterate(augment(A2, B2), initialGuess2, tolerance, maxIterations);

		// Return the error.
		return method.getError();
	}

	public static void Part2() {
		// jacobi iterative runs.
		IterativeConvergence jacobi =
			new IterativeConvergence(
				new JacobiIterative()
				);

		// gauss seidel iterative runs.
		IterativeConvergence gauss =
			new IterativeConvergence(
				new GaussSeidelIterative()
				);

		// Print jacobi errors.
		for (int i = 2; i <= 10; i++) {
			System.out.println(
				"Jacobi error for M="
				+ i
				+ " is: "
				+ jacobi.calculateError(i));
		}

		// Print gauss errors.
		for (int i = 2; i <= 10; i++) {
			System.out.println(
				"Gauss error for M="
				+ i
				+ " is: "
				+ gauss.calculateError(i));
		}
	}

	public static void main(String[] args) {
		Part1();
		Part2();
	}
}
