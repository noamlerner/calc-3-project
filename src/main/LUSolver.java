package main;

/**
 * Solver implementation using LU factorization.
 *
 * @author Joshua Songy
 * @version 1.0
 */
public class LUSolver implements Solver {

	/**
     * Constructor.
     */
	public LUSolver() {
	}

	/**
     * Solves the equation.
     *
     * @argument a The matrix a.
     * @argument b The vector b.
     * @return The solution.
     */
	public Vector solve(Matrix a, Vector b) {

		// Instantiate the solver.
		LUFactorization solver = new LUFactorization(a);

		// Return the solution.
		return new Vector(solver.solveFor(b));
	}

	/**
     * Returns the name of this method.
     *
     * @return The name of this method.
     */
	public String name() {
		return "LU Factorization";
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

		// Instantiate the solver.
		LUFactorization solver = new LUFactorization(a);

		// Reconstruct the original matrix.
		return solver.getL().times(solver.getU());
	}
}
