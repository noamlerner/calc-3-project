package main;

/**
 * Represents an iterative method for solving a system of equations.
 *
 * @author Joshua Songy
 * @version 1.0
 */
public interface Iterative {

	/**
     * Calculate the iterative solution.
     *
     * @argument Ab The argument matrix.
     * @argument u The initial guess of the solution.
     * @argument epsilon Tolerance parameter.
	 * @argument M The maximum number of iterations to perform before giving up.
	 */
	public void iterate(Matrix Ab, Matrix u, double epsilon, int M);

	/**
     * Returns the previously calculated solution.
     * This is illegal to call unless an "iterate" call has already been made.
	 *
     * @return The solution vector.
	 */
	public Vector getSolution();

	/**
     * Returns the number of iterations needed to calculate the solution.
     * This is illegal to call unless an "iterate" call has already been made.
	 *
     * @return The number of iterations performed.
	 */
	public int getIterations();

	/**
     * Returns the error of the solution
     * This is illegal to call unless an "iterate" call has already been made.
	 *
     * @return The error of the solution.
	 */
	public double getError();

	/**
     * Returns whether the calculation timed out.
     *
     * @return Whether the calculation timed out.
     */
	public boolean hasTimedOut();
}
