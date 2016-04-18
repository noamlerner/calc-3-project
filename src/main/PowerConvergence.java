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

    // The given PDF answers.
    private static final double p1 = -(1.0 + 4.0) / 2.0;
    private static final double p2 = (1.0 + 4.0) / 2.0;

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

    // u0 from pdf.
    private static final Vector u0 = new Vector(new double[] {1.0, 0.0, 0.0});

    // Max iterations.
    private static final int maxIterations = 100;

    // The list of guesses the PDF told us to try.
    private static final Vector[] guessVectors = {
        new Vector(new double[] {1, 0}),
        new Vector(new double[] {0, 1}),
        new Vector(new double[] {1, 1})
    };

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
        Matrix result = new Matrix(randomMatrixHeight, randomMatrixWidth);

        // Check determinant to make sure this can be inverted.
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
            randomMatrices.add(randomMatrix());
        }

        // return the results.
        return randomMatrices;
    }

    /**
     * Holds private data on internal runs.
     *
     * @author Joshua Songy
     * @version 1.0
     */
    private class RunData {

        // The matrix.
        public Matrix target;

        // The inverse matrix.
        public Matrix inverse;

        // The trace.
        public double trace;

        // The number of iterations we executed.
        public int iterations;
        public int inverseIterations;

        // The calculated eigenvalues.
        public double smallestEigenValue;
        public double largestEigenValue;

        // Whether this passed.
        public boolean success;
    }

    // Holds the data on the runs we have tried so far.
    private List<RunData> runData;

    /**
     * Private constructor for internal use.
     */
    private PowerConvergence() {

        // Allocate space for the run data.
        runData = new ArrayList<RunData>();
    }

    /**
     * Use the recorded iterative method to solve every initial guess.
     *
     * @argument runs The initial guesses.
     */
    private void solveAll(List<Matrix> runs) {

        // Solve each one.
        for (Matrix target: runs) {
            solve(target);
        }
    }

    /**
     * Use the recorded iterative method to solve this inital guess.
     *
     * @argument guess The initial guess.
     */
    private void solve(Matrix target) {

        // Create the container for the results.
        RunData data = new RunData();

        // Store the matrix data.
        data.target = target;
        data.inverse = inverse(target);
        data.trace = target.trace();

        // Run until one of the guesses works.
        for (Vector guess: guessVectors) {

            Result<PowerMethod.EigenEstimate, PowerMethod.PowerMethodErr> result =
                PowerMethod.power_method(
                    target,
                    guess,
                    new Vector(
                        new double[] {randomInRange(0.0, 1.0),
                                      randomInRange(0.0, 1.0),
                                      randomInRange(0.0, 1.0)}
                        ),
                    tolerance,
                    maxIterations
                    );

            Result<PowerMethod.EigenEstimate, PowerMethod.PowerMethodErr> resultInverse =
                PowerMethod.power_method(
                    data.inverse,
                    guess,
                    new Vector(
                        new double[] {randomInRange(0.0, 1.0),
                                      randomInRange(0.0, 1.0),
                                      randomInRange(0.0, 1.0)}
                        ),
                    tolerance,
                    maxIterations
                    );

            // Populate the result.
            data.iterations = result.unwrap().iteration;
            data.inverseIterations = resultInverse.unwrap().iteration;
            data.largestEigenValue = result.unwrap().value;
            data.smallestEigenValue = 1.0 / resultInverse.unwrap().value;
            data.success = (!result.unwrap().timedOut)
                && (!resultInverse.unwrap().timedOut);

            if (data.success) {

                // Push the results to the list of results.
                runData.add(data);
                return;
            }

        }

        // Push the results to the list of results.
        runData.add(data);
    }

    /**
     * Inverts a 2x2 matrix.
     *
     * @argument matrix The matrix to invert.
     * @return The inverted matrix.
     */
    public static Matrix inverse(Matrix matrix) {

        // For readability.
        double a = matrix.get(0, 0);
        double b = matrix.get(0, 1);
        double c = matrix.get(1, 0);
        double d = matrix.get(1, 1);

        // Return the transverse.
        return new Matrix(
            new double[][] {{       d, -1.0 * b},
                            {-1.0 * c,        a}}
            ).multiply_scalar(
                1.0 / (a * d - b * c)
                );
    }

    public static void Part1() {
        List<Matrix> randomMatrices = randomMatrices();

        // Power method instance.
        PowerConvergence pm = new PowerConvergence();
        pm.solveAll(randomMatrices);

        // Print the results so we can make a graph.
        for (RunData data: pm.runData) {
            if (data.success) {

                System.out.println(
                    "Determinant: " + data.target.determinant()
                    + " Trace: " + data.target.trace()
                    + " Iterations: " + data.iterations);

                System.out.println(
                    "Determinant: " + data.inverse.determinant()
                    + " Trace: " + data.inverse.trace()
                    + " Iterations: " + data.inverseIterations);
            }
        }
    }

    public static void Part2() {

        // Precomputed (A-p1I)^-1
        Matrix p1precomputed = A.minus(
            Matrix.identity(A.M).multiply_scalar(p1)).inverse();

        // Precomputed (A-p2I)^-1
        Matrix p2precomputed = A.minus(
            Matrix.identity(A.M).multiply_scalar(p2)).inverse();

        Result<PowerMethod.EigenEstimate, PowerMethod.PowerMethodErr> result =
            PowerMethod.power_method(
                p1precomputed,
                u0,
                u0,
                tolerance,
                maxIterations
            );

        result =
            PowerMethod.power_method(
                p2precomputed,
                u0,
                u0,
                tolerance,
                maxIterations
            );

        System.out.println(result.unwrap().value);
        System.out.println(result.unwrap().value);

    }

    public static void main(String[] args) {
        Part1();
        System.out.println("=====");
        Part2();
    }
}
