package main.tests;

import main.Matrix;
import main.Vector;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michael on 4/11/16.
 */
public class MatrixTests {
    private static final double SQRT_2 = Math.sqrt(2);
    private static final double INV_SQRT_2 = 1 / SQRT_2;

    @Test
    public void subColumns() {
        double[][] data = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16},
        };
        Matrix matrix = new Matrix(data);
        assertEquals(Vector.from_data(1, 5, 9, 13), matrix.getSubColumn(0, 4));
        assertEquals(Vector.from_data(6, 10, 14), matrix.getSubColumn(1, 3));
        assertEquals(Vector.from_data(11, 15), matrix.getSubColumn(2, 2));
        assertEquals(Vector.from_data(16), matrix.getSubColumn(3, 1));
    }

    @Test
    public void pad() {
        double[][] data = {
            {2, 3},
            {4, 5},
        };
        double[][] answer = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 2, 3},
                {0, 0, 4, 5},
        };
        Matrix matrix = new Matrix(data);
        Matrix correct = new Matrix(answer);
        assertEquals(correct, matrix.pad_top_left(4));
    }

    @Test
    public void upper_triangular_true() {
        double[][] data = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 2, 3},
                {0, 0, 0, 5},
        };
        Matrix matrix = new Matrix(data);
        assertTrue(matrix.is_upper_triangular());
    }

    @Test
    public void upper_triangular_false() {
        double[][] data = {
                {1, 0, 0},
                {0, 2, 0},
                {0, 1, 3},
        };
        Matrix matrix = new Matrix(data);
        assertFalse(matrix.is_upper_triangular());
    }

    @Test
    public void big_upper_triangular() {
        double[][] data = {
            {-1.5783,   -0.8727,   -0.8839,   -0.5633,   -1.0757},
            { 0.0000,   -0.3798,    0.1130,    0.1435,    0.2806},
            { 0.0000,   -0.0000,   -0.3668,    0.0197,    0.5008},
            { 0.0000,    0.0000,   -0.0000,   -0.4442,   -0.3124},
            { 0.0000,   -0.0000,    0.0000,    0.0000,   -0.6779},
        };
        Matrix matrix = new Matrix(data);
        assertTrue("\n" + matrix + "should be upper triangular", matrix.is_upper_triangular());
    }

    @Test
    public void col_normal_true() {
        double[][] data = {
            {-INV_SQRT_2,  INV_SQRT_2,            0},
            { INV_SQRT_2,           0,  -INV_SQRT_2},
            {          0, -INV_SQRT_2,   INV_SQRT_2},
        };
        Matrix matrix = new Matrix(data);
        assertTrue(matrix.is_col_normal());
    }

    @Test
    public void col_normal_false() {
        double[][] data = {
                {INV_SQRT_2, 0,       0.1},
                {INV_SQRT_2, 1, INV_SQRT_2},
                {         0, 0, INV_SQRT_2},
        };
        Matrix matrix = new Matrix(data);
        assertFalse(matrix.is_col_normal());
    }

    @Test
    public void givens_1() {
        double[][] data = {
            {1,                     0,                      0},
            {0, Math.cos(Math.PI / 4), -Math.sin(Math.PI / 4)},
            {0, Math.sin(Math.PI / 4),  Math.cos(Math.PI / 4)},
        };
        Matrix answer = new Matrix(data);
        assertEquals(answer, Matrix.rotation(3, 1, 2, Math.PI / 4));
    }
}
