package main;

import java.util.function.Function;

/******************************************************************************
 *  A bare-bones immutable data type for M-by-N matrices.
 *  From http://introcs.cs.princeton.edu/java/95linear/Matrix.java.html
 ******************************************************************************/
@SuppressWarnings("unused")
public class Matrix {
    public static final double EPSILON = 0.000001;

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

    public Matrix(Double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i += 1) {
            for (int j = 0; j < N; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    // copy constructor
    public Matrix(Matrix A) { this(A.data); }

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

    /**
     * Find the determinant of the matrix.
     * - Joshua Songy
     *
     * @return The determinant of the matrix.
     */
    public Double determinant() {
        // Sanity check the matrix size.
        if (N != M) {
            throw new RuntimeException("Cannot take determinant of non-square matrix.");
        }

        // Base case for empty matrix.
        if (N == 0) {
            return 1.0;
        }

        // Base case for 1x1 matrix.
        if (N == 1) {
            return this.data[0][0];
        }

        // Base case for 2x2 matrix.
        if (N == 2) {
            return this.data[0][0] * this.data[1][1]
                - this.data[0][1] * this.data[1][0];
        }

        // Used to alternate between adding and subtracting.
        double pivot = 1;

        // The accumulator across the iterations.
        double accumulator = 0.0;

        // Iterate across top row.
        for (int i = 0; i < N; i++) {

            // The submatrix to find the determinant of.
            Matrix subMatrix = new Matrix(M - 1, N - 1);

            // Copy the left part of the submatrix.
            for (int ii = 0; ii < i; ii++) {
                for (int j = 1; j < N; j++) {
                    subMatrix.data[ii][j - 1] = this.data[ii][j];
                }
            }

            // Copy the right half of the submatrix.
            for (int ii = i + 1; ii < M; ii++) {
                for (int j = 1; j < N; j++) {
                    subMatrix.data[ii - 1][j - 1] = this.data[ii][j];
                }
            }

            // Recurse.
            accumulator += this.data[i][0] * pivot * subMatrix.determinant();

            // Invert the pivot.
            pivot *= -1;
        }

        // Return the result.
        return accumulator;
    }

    /**
     * Return the trace of the matrix.
     *
     * @return The trace of the matrix.
     */
    public Double trace() {

        // Sanity check the matrix size.
        if (N != M) {
            throw new RuntimeException("Cannot take trace of non-square matrix.");
        }

        // The result.
        double result = 0.0;

        // Add the diagnol.
        for (int i = 0; i < N; i++) {
            result += this.data[i][i];
        }

        // Return what we found.
        return result;
    }

    /***********************************************************************
     * Everything below this block was coded by the group                  *
     * for this calc 3 project. It was included in this file for ease use  *
     ***********************************************************************/
    
    public Matrix inverse(){
        LUFactorization lu = new LUFactorization(this);
        Matrix I = Matrix.identity(this.getNumCols());
        Matrix inverse = new Matrix(M,M);
        for(int i = 0; i < this.getNumCols(); i++){
            Matrix e = new Matrix(M,1);
            e.setCol(0, I.getCol(i));
            Matrix v = lu.solveFor(e);
            inverse.setCol(i, v.getCol(0));
        }
        return inverse;
    }

    public static Matrix rotation(int size, int row, int col, double theta) {
        Matrix givens = Matrix.identity(size);
        givens.data[row][row] =  Math.cos(theta);
        givens.data[col][col] =  Math.cos(theta);
        givens.data[row][col] = -Math.sin(theta);
        givens.data[col][row] =  Math.sin(theta);
        return givens;
    }

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

    public boolean is_upper_triangular() {
        if (getNumCols() != getNumRows() || getNumRows() < 2) {
            return false;
        }
        for (int i = 1; i < getNumRows(); i += 1) {
            for (int j = 0; j < i; j += 1) {
                if (Math.abs(data[i][j]) > EPSILON) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks to see if all the columns are normal vectors
    public boolean is_col_normal() {
        for (int col = 0; col < getNumCols(); col += 1) {
            Vector column = Vector.from_data(getCol(col));
            if (Math.abs(column.magnitude() - 1) > EPSILON) {
                return false;
            }
        }
        return true;
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
        return other != null && other instanceof Matrix && this.equals((Matrix) other, EPSILON);
    }

    public double get(int i, int j) {
        return this.data[i][j];
    }

    public void set(int i, int j, double value) {
        this.data[i][j] = value;
    }
}
