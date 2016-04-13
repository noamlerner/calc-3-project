package main;

/**
 * Created by michael on 4/11/16.
 */
public class QrDecomp {
    public final Matrix q;
    public final Matrix r;

    public QrDecomp(Matrix q, Matrix r) {
        this.q = q;
        this.r = r;
    }

    public enum SolutionErr {
        MatrixAndVectorDiffDim,
    }

    public Result<Vector, SolutionErr> solve(Vector y) {
        if (this.q.getNumRows() != y.length) {
            return Result.fail(SolutionErr.MatrixAndVectorDiffDim);
        }
        Vector output = this.q.transpose().times(y);
        double[] solutions = new double[output.length];

        for (int col = r.getNumCols() - 1; col >= 0; col -= 1) {
            solutions[col] = output.data[col][0] /  r.data[col][col];
            for (int row = col - 1; row >= 0; row -= 1) {
                output.data[row][0] -= solutions[col] * r.data[row][col];
            }
        }

        return Result.succeed(Vector.from_data(solutions));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Q:\n");
        builder.append(q);
        builder.append("\nR:\n");
        builder.append(r);
        return builder.toString();
    }
}
