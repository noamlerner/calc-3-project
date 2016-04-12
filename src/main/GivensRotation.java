package main;

/**
 * Created by michael on 4/12/16.
 */
public class GivensRotation {
    public enum Err {
        MatrixNotSquare,
    }

    public static Result<QrDecomp, Err> qr_fact_givens(Matrix matrix) {
        // Check preconditions
        if (matrix.getNumCols() != matrix.getNumRows()) {
            return Result.fail(Err.MatrixNotSquare);
        }
        // Create q and r
        Matrix q = Matrix.identity(matrix.getNumRows());
        Matrix r = new Matrix(matrix);

        // zero things one by one, with x being the pivot element and y is what to be zeroed
        for (int row = 1; row < matrix.getNumRows(); row += 1) {
            for (int col = 0; col < row; col += 1) {
                if (r.is_upper_triangular()) {
                    return Result.succeed(new QrDecomp(q, r));
                }
                // Get the angle
                double x = matrix.data[col][col];
                double y = matrix.data[row][col];
                double theta = Math.atan2(y, x);
                // Get givens rotation
                Matrix rotation = Matrix.rotation(matrix.getNumRows(), row, col, theta);
                // Update Q & R
                r = rotation.times(r);
                q = q.times(rotation.transpose());
            }
        }

        return Result.succeed(new QrDecomp(q, r));
    }
}
