package main;

/**
 * Solver interface for solving Ax=b equations.
 *
 * @author Joshua Songy
 * @version 1.0
 */
public interface Solver {

	/**
     * Solves the equation.
     *
     * @argument a The matrix a.
     * @argument b The vector b.
     * @return The solution.
     */
	public Vector solve(Matrix a, Vector b);

	/**
     * Returns the name of this method.
     *
     * @return The name of this method.
     */
	public String name();

	/**
     * Whether this method produces a factorization.
     *
     * @return Whether this method uses an internal factorization.
     */
	public boolean hasFactorization();

	/**
     * If this method has an internal factorization, this returns the rebuilt
     * matrix.
     *
     * @return The rebuilt matrix.
     */
	public Matrix factorization(Matrix a);
}
