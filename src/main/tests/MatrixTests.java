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
}
