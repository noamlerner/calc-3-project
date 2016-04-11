public class PowerMethod {


    public enum PowerMethodErr {
        MatrixNotSquare,
    }

    public class EigenEstimate {
        public final float value;
        public final Matrix vector;

        public EigenEstimate(float value, Matrix vector) {
            this.value = value;
            this.vector = vector;
        }
    }

    public static Result<EigenEstimate, PowerMethodErr> power_method(
        Matrix matrix,
        Vector guess,
        Vector auxiliary,
        double tolerance,
        int iterations)
    {
        // Check if its square
        if (matrix.getNumRows() != matrix.getNumCols()) {
            return Result.fail(PowerMethodErr.MatrixNotSquare);
        }
        // Start power method
        double error = Double.POSITIVE_INFINITY;
        auxiliary = guess;
        for (int i = 0; i < iterations && error > tolerance; i += 1) {
            auxiliary = matrix.times(auxiliary).norm();
        }
        return null;
    }
}
