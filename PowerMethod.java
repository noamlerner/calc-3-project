public class PowerMethod {
    public static enum PowerMethodErr {
        MatrixNotSquare,
        InvalidGuestVecDim,
        InvalidAuxVecDim,
    }

    public static class EigenEstimate {
        public final double value;
        public final Matrix vector;

        public EigenEstimate(double value, Matrix vector) {
            this.value = value;
            this.vector = vector;
        }
    }

    public static Result<EigenEstimate, PowerMethodErr> power_method(
        Matrix matrix,
        Vector guess,
        Vector aux,
        double tolerance,
        int iterations)

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
        for (int i = 0; i < iterations && error > tolerance; i += 1) {
            Vector next_vec = matrix.times(guess);
            double next_val = next_vec.dot(aux) / guess.dot(aux);
            error = Math.abs(eigen - next_val);
            guess = next_vec;
            eigen = next_val;
        }
        return Result.succeed(new EigenEstimate(eigen, guess.norm()));
    }
}
