package main;

/**
 * Created by michael on 4/11/16.
 */
public class HouseHolders {
    public enum Err {
        MatrixNotSquare,
    }

    public static Result<QrDecomp, Err> qr_fact_house(Matrix matrix) {
        // Check if square
        if (matrix.getNumCols() != matrix.getNumRows()) {
            return Result.fail(Err.MatrixNotSquare);
        }
        Matrix q = Matrix.identity(matrix.getNumCols());

        for (int i = 0; i < matrix.getNumCols(); i += 1) {
            // Get the sub column
            Vector sub_column = matrix.getSubColumn(i, matrix.getNumCols() - i);
            double sub_col_mag_sqr = sub_column.square_magnitude();
            double sub_col_mag = Math.sqrt(sub_col_mag_sqr);
            // Construct e_1
            Vector identity = Vector.identity(sub_column.length);
            // Construct v = x + |x| * e_1
            Vector v = sub_column.plus(identity.multiply_scalar(sub_col_mag));
            // Get the matrix identity
            Matrix I = Matrix.identity(sub_column.length);
            // Calculate (2 / |v|^2) * v * v_t
            Matrix C = v.times(v.transpose()).multiply_scalar(2 / sub_col_mag_sqr);
            // Finally calculate I - (2 / |v|^2) * v * v_t
            Matrix H = I.minus(C);
            // Update matrix
            matrix = H.times(matrix);
            // Update Q
            q = H.times(q);
        }

        return Result.succeed(new QrDecomp(q, matrix));
    }
}
