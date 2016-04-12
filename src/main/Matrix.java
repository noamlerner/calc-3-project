package main;

import java.util.Arrays;
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

    public Vector getSubColumn(int column, int height) {
        assert height <= M;
        assert column < N;
        double[] sub_col = new double[height];
        for (int i = M - height, j = 0; i < M; i += 1, j += 1) {
            sub_col[j] = data[i][column];
        }
        return Vector.from_data(sub_col);
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
        return Vector.from_matrix(this.times((Matrix) vector));
    }

    public double getMaxNorm(){
    	double max = 0;
    	double temp;
    	for(int i = 0; i < data.length; i++){
    		for(int j = 0; j < data[i].length; j++){
    			temp = Math.abs(data[i][j]);
    			max = temp > max ? temp : max;
    		}
    	}
    	return max;
    }

    /**
     * Pads a smaller matrix with the identity and puts
     * the smaller matrix in the bottom left corner.
     * If size is the same as the current size then
     * the matrix is unchanged.
     *
     * So this matrix:
     * 5 8
     * 2 1
     *
     * Padded with size 3 is:
     * 1 0 0
     * 0 5 8
     * 0 2 1
     *
     * @param size of the output Matrix
     * @return a new size*size padded matrix
     */
    public Matrix pad_top_left(int size) {
        assert getNumCols() == getNumRows();
        assert size >= getNumCols();
        double[][] matrix = Matrix.identity(size).data;
        for (int i = size - getNumRows(), i_m = 0; i < size; i += 1, i_m += 1) {
            for (int j = size - getNumCols(), j_m = 0; j < size; j += 1, j_m += 1) {
                matrix[i][j] = this.data[i_m][j_m];
            }
        }
        return new Matrix(matrix);
    }

    public boolean equals(Matrix other, double epsilon) {
        for (int i = 0; i < getNumRows(); i += 1) {
            for (int j = 0; j < getNumCols(); j += 1) {
                if (Math.abs(this.data[i][j] - other.data[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Matrix && this.equals((Matrix) other, 0.0);
    }
}
