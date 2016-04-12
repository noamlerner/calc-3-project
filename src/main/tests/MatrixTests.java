package main.tests;

import main.Matrix;
import main.Vector;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
}
