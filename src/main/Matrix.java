package main;

import java.util.function.Function;

/******************************************************************************
 *  A bare-bones immutable data type for M-by-N matrices.
 *  From http://introcs.cs.princeton.edu/java/95linear/Matrix.java.html
 ******************************************************************************/
@SuppressWarnings("unused")
public class Matrix {
    public final int M;           // number of rows
    public final int N;           // number of columns
    public final double[][] data; // M-by-N array

    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    // create matrix based on 2d array
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                    this.data[i][j] = data[i][j];
    }

    // copy constructor
    private Matrix(Matrix A) { this(A.data); }

    // create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.random();
        return A;
    }

    // create and return the N-by-N identity matrix
    public static Matrix identity(int N) {
        Matrix I = new Matrix(N, N);
        for (int i = 0; i < N; i++)
            I.data[i][i] = 1;
        return I;
    }

    // create and return the transpose of the invoking matrix
    public Matrix transpose() {
        Matrix A = new Matrix(N, M);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[j][i] = this.data[i][j];
        return A;
    }

    // return C = A + B
    public Matrix plus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] + B.data[i][j];
        return C;
    }

    // return C = A - B
    public Matrix minus(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                C.data[i][j] = A.data[i][j] - B.data[i][j];
        return C;
    }

    // does A = B exactly?
    public boolean eq(Matrix B) {
        Matrix A = this;
        if (B.M != A.M || B.N != A.N) throw new RuntimeException("Illegal matrix dimensions.");
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                if (A.data[i][j] != B.data[i][j]) return false;
        return true;
    }

    // return C = A * B
    public Matrix times(Matrix B) {
        Matrix A = this;
        if (A.N != B.M) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.M, B.N);
        for (int i = 0; i < C.M; i++)
            for (int j = 0; j < C.N; j++)
                for (int k = 0; k < A.N; k++)
                    C.data[i][j] += (A.data[i][k] * B.data[k][j]);
        return C;
    }

    // print matrix to standard output
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                builder.append(String.format("%9.4f ", data[i][j]));
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    /***********************************************************************
     * Everything below this block was coded by the group                  *
     * for this calc 3 project. It was included in this file for ease use  *
     ***********************************************************************/

    public int getNumRows() {
        return M;
    }

    public int getNumCols() {
        return N;
    }

    /* swap rows i and j
     */
    public void swap(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /* returns a matrix whher a scaled (by scale) version of the row addend
     * has been added to the row addTo
     */
    public void addRowToRow(int addend, int addTo, double scale) {
        if (Double.isNaN(scale)) {
            scale = 0;
        }
        for (int i = 0; i < N; i++) {
            data[addTo][i] += data[addend][i] * scale;
        }
    }

    /* sets column c to col
     */
    public void setCol(int c, double [] col) {
        for (int i = 0; i < M; i++) {
            data[i][c] = col[i];
        }
    }

    public double[] getCol(int c) {
        double[] col = new double[M];
        for (int i = 0; i < M; i++) {
            col[i] = data[i][c];
        }
        return col;
    }

    public double[] getRow(int r) {
        double[] row = data[r];
        return row;
    }

    public double[][] getData() {
        return data;
    }

    public Matrix map(Function<Double, Double> mapper) {
        Matrix mapped = new Matrix(this);
        for (int i = 0; i < M; i += 1) {
            for (int j = 0; j < N; j += 1) {
                mapped.data[i][j] = mapper.apply(mapped.data[i][j]);
            }
        }
        return mapped;
    }

    public Matrix divide_scalar(double factor) {
        return map(e -> e / factor);
    }

    public Matrix multiply_scalar(double factor) {
        return map(e -> e * factor);
    }

    public Vector times(Vector vector) {
        return Vector.from_matrix(this.times(vector));
    }

    public double getMaxNorm() {
        return 0.0;
        // TODO
    }
}