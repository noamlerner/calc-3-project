package main.tests;

import main.Matrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the determinant function of the matrix class.
 * - Joshua Songy
 */
public class DeterminantTests {

    @Test
    public void determinant_test_0x0() {
		// Do the test.
        assertEquals(new Double(1.0), (new Matrix(0, 0)).determinant());
    }

    @Test
    public void determinant_test_1x1() {
		// Example data.
        double[][] data = {
            {7.0},
        };

		// Do the test.
        assertEquals(new Double(7.0), (new Matrix(data)).determinant());
    }

    @Test
    public void determinant_test_2x2() {
		// Example data.
        double[][] data = {
            {3.0, 8.0},
			{4.0, 6.0},
        };

		// Do the test.
        assertEquals(new Double(-14.0), (new Matrix(data)).determinant());
    }

    @Test
    public void determinant_test_4x4() {
		// Example data.
        double[][] data = {
            {3.0, 2.0, -1.0, 4.0},
			{2.0, 1.0, 5.0, 7.0},
			{0.0, 5.0, 2.0, -6.0},
			{-1.0, 2.0, 1.0, 0.0},
        };

		// Do the test.
        assertEquals(new Double(-418.0), (new Matrix(data)).determinant());
	}
}
