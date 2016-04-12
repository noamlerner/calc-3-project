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

        // Last column isn't needed its sub-column is one-size
        for (int i = 0; i < matrix.getNumCols() - 1; i += 1) {
            // Get the sub column
            Vector sub_column = matrix.getSubColumn(i, matrix.getNumCols() - i);
            // Construct e_1
            Vector identity = Vector.identity(sub_column.length);
            // Construct v = x + |x| * e_1
            Vector v = sub_column.plus(identity.multiply_scalar(sub_column.magnitude()));
            // Get the matrix identity
            Matrix I = Matrix.identity(sub_column.length);
            // Calculate (2 / |v|^2) * v * v_t
            Matrix C = v.times(v.transpose()).multiply_scalar(2 / v.square_magnitude());
            // Finally calculate I - (2 / |v|^2) * v * v_t
            // And make it an NxN by putting H in the bottom left corner
            Matrix H = I.minus(C).pad_top_left(matrix.getNumCols());
            // Update matrix
            matrix = H.times(matrix);
            // Update Q
            q = q.times(H);
        }

        return Result.succeed(new QrDecomp(q, matrix));
    }
}
