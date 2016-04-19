package main;

public class PowerMethod {
    public enum PowerMethodErr {
        MatrixNotSquare,
        InvalidGuestVecDim,
        InvalidAuxVecDim;

        @Override
        public String toString() {
            if (this == MatrixNotSquare) {
                return "The matrix was not square.";
            } else if (this == InvalidGuestVecDim) {
                return "The dimensions of the guess vector did not match the matrix's";
            } else if (this == InvalidAuxVecDim) {
                return "The dimensions of the auxiliary vector did not match the matrix's";
            } else {
                return "Unkown error.";
            }
        }
    }

    public static class EigenEstimate {
        public final double value;
        public final Vector vector;
        public final int iteration;
		public final boolean timedOut;

        public EigenEstimate(double value, Vector vector, int iter,
							 boolean timedOut) {
            this.value = value;
            this.vector = vector;
            this.iteration = iter;
			this.timedOut = timedOut;
        }

        @Override
        public String toString() {
            return "Value Estimate: " + this.value + "\n"
                    + "Vector Estimate: " + this.vector + "\n";
        }
    }

    public static Result<EigenEstimate, PowerMethodErr> power_method(
        Matrix matrix,
        Vector guess,
        Vector aux,
        double tolerance,
        int iterations)
    {
        // Check if its square
        if (matrix.getNumRows() != matrix.getNumCols()) {
            return Result.fail(PowerMethodErr.MatrixNotSquare);
        } else if (matrix.getNumRows() != guess.length) {
            return Result.fail(PowerMethodErr.InvalidGuestVecDim);
        } else if (matrix.getNumRows() != guess.length) {
            return Result.fail(PowerMethodErr.InvalidAuxVecDim);
        }
        // Book-Keeping
        double error = Double.POSITIVE_INFINITY;
        double eigen = Double.POSITIVE_INFINITY;
        // Start power method
        int i;
        for (i = 0; i < iterations && error > tolerance * 0.5; i += 1) {
            Vector next_vec = matrix.times(guess);
            double next_val = next_vec.dot(aux) / guess.dot(aux);
            error = Math.abs(eigen - next_val);
            guess = next_vec;
            eigen = next_val;
        }

        return Result.succeed(
			new EigenEstimate(
				eigen,
				guess.norm(),
				i,
				(i == iterations) && (error > tolerance * 0.5)
			)
		);
    }
}
