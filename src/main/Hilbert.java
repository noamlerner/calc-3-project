package main;

import java.util.List;
import java.util.ArrayList;

/**
 * The implementation of the "Hilbert" problem.
 *
 * @version 1.0
 * @author Joshua Songy
 */
public class Hilbert {

	// n bounds.
	private static final int nLowerBound = 2;
	private static final int nUpperBound = 20;

	// The methods to solve with.
	private static final Solver[] methods = new Solver[] {
		new LUSolver(),
		new HouseHolderSolver(),
		new GivensSolver()
	};

	/**
	 * Generates an nxn hilbert matrix.
	 *
	 * @argument size The size of the hilbert matrix.
	 */
		private static Matrix hilbert(int size) {

			// The resulting matrix.
			Matrix result = new Matrix(size, size);

			// Populate it.
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					result.set(
						i, j, 
						1.0 / (((double)i) + ((double)j) + 1.0)
						);
				}
			}

			// Return the result.
			return result;
		}

	/**
	 * Generates the b vector.
	 *
	 * @argument size the size of the vector.
	 */
	private static Vector bVector(int size) {

		// The vector.
		Vector result = new Vector(size);

		// Populate it.
		for (int i = 0; i < size; i++) {
			result.set(i, Math.pow(0.1, ((double)size) / 3.0));
		}

		return result;
	}

	public static void main(String[] args) {


		// Solve for each method.
		for (Solver method: methods) {

			// Solve for every n.
			for (int n = nLowerBound; n <= nUpperBound; n++) {

				// Generate the hilbert matrix.
				Matrix hilbert = hilbert(n);

				// Generate the target.
				Vector target = bVector(n);

				// Solve.
				Vector solution = method.solve(hilbert, target);

				// Find the error.
				Vector error = hilbert
					.times(solution)
					.plus(target.multiply_scalar(-1.0));

				// Print results.
				System.out.println(
					"Solving size: "
					+ n
					+ " using method: "
					+ method.name() + "\n"
					+ "Solution is:\n"
					+ solution.toString()
					+ "Error is:\n"
					+ error.toString()
					);

				// Print out factorization error if needed.
				if (method.hasFactorization()) {
					System.out.println(
						"Factorization error is: \n"
						+ method.factorization(hilbert).plus(hilbert.multiply_scalar(-1.0)).toString()
									   );
				}
			}
		}
	}
}
