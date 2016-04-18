package main;

/**
 * Solver implementation using givens.
 *
 * @author Joshua Songy
 * @version 1.0
 */
public class GivensSolver implements Solver {

	/**
     * Constructor.
     */
	public GivensSolver() {
	}

	/**
     * Solves the equation.
     *
     * @argument a The matrix a.
     * @argument b The vector b.
     * @return The solution.
     */
	public Vector solve(Matrix a, Vector b) {

		// Perform the QR decomposition.
		Result<QrDecomp, GivensRotation.Err> result =
			GivensRotation.qr_fact_givens(a);

		// Throw an exception if there was an error.
		if (result.is_err()) {
			throw new RuntimeException(
				"Error performing Givens solution: "
				+ result.unwrap_err().toString()
			);
		}

		// Solve.
		Result<Vector, QrDecomp.SolutionErr> solved = result.unwrap().solve(b);

		// Throw an exception if there was an error.
		if (solved.is_err()) {
			throw new RuntimeException(
				"Error performing Givens solution: "
				+ solved.unwrap_err().toString()
			);
		}

		// Return the solution.
		return solved.unwrap();
	}

	/**
     * Returns the name of this method.
     *
     * @return The name of this method.
     */
	public String name() {
		return "Givens";
	}

	/**
     * Whether this method produces a factorization.
     *
     * @return Whether this method uses an internal factorization.
     */
	public boolean hasFactorization() {
		return true;
	}

	/**
     * If this method has an internal factorization, this returns the rebuilt
     * matrix.
     *
     * @return The rebuilt matrix.
     */
	public Matrix factorization(Matrix a) {
		// Perform the QR decomposition.
		Result<QrDecomp, GivensRotation.Err> result =
			GivensRotation.qr_fact_givens(a);

		// Throw an exception if there was an error.
		if (result.is_err()) {
			throw new RuntimeException(
				"Error performing House Holder solution: "
				+ result.unwrap_err().toString()
			);
		}

		// Reconstruct the original matrix.
		return result.unwrap().q.times(result.unwrap().r);
	}
}
