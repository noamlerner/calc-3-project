package main.tests;

import main.Matrix;
import main.PowerMethod;
import main.Vector;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by michael on 4/11/16.
 */
public class PowerMethodTests {
    @Test
    public void simple() {
        double[][] data_matrix = {
            {1, 0, 0},
            {0, 2, 0},
            {0, 0, 3},
        };
        double[] eigenvector = {0, 0, 1};
        Matrix matrix = new Matrix(data_matrix);
        Vector guess = Vector.from_data(1, 1, 1);
        Vector auxiliary = Vector.from_data(1, 1, 1);
        PowerMethod.EigenEstimate estimate = PowerMethod.power_method(matrix, guess, auxiliary, 0.1, 200).unwrap();

        // We didn't get an error
        assertNotEquals(estimate, null);

        // Get the right value
        assertEquals(3.0, Math.abs(estimate.value), 0.1);

        // Get the right vector
        assertArrayEquals(estimate.vector.into_array(), eigenvector, 0.1);
    }
}
