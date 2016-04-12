package main.tests;

import main.GivensRotation;
import main.Matrix;
import main.QrDecomp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by michael on 4/12/16.
 */
public class GivensRotationTests {
    public final static double SQRT_2 = Math.sqrt(2);
    public final static double INV_SQRT_2 = 1 / SQRT_2;

    @Test
    public void givens_test_1() {
        double[][] data = {
            {-1, -1, 1},
            { 0,  1, 1},
            { 1,  1, 0},
        };
        Matrix matrix = new Matrix(data);

        double[][] q_data = {
            { INV_SQRT_2,  0, -INV_SQRT_2},
            {          0, -1,           0},
            {-INV_SQRT_2,  0, -INV_SQRT_2},
        };
        Matrix q = new Matrix(q_data);

        double[][] r_data = {
            {-SQRT_2, -SQRT_2,  INV_SQRT_2},
            {      0,      -1,          -1},
            {      0,       0, -INV_SQRT_2},
        };
        Matrix r = new Matrix(r_data);

        QrDecomp decomposition = GivensRotation.qr_fact_givens(matrix).unwrap();
        assertNotNull(decomposition);
        assertEquals(r, decomposition.r);
        assertEquals(q, decomposition.q);
    }

    @Test
    public void randomized_givens() {
        final int SIZES = 6;
        final int TRIALS = 10000;

        for (int size = 2; size < SIZES; size += 1) {
            for (int trial = 0; trial < TRIALS; trial += 1) {
                Matrix test = Matrix.random(size, size);
                QrDecomp decomposition = GivensRotation.qr_fact_givens(test).unwrap();
                try {
                    assertNotNull(decomposition);
                    assertNotNull(decomposition.q);
                    assertNotNull(decomposition.r);
                    assertTrue(decomposition.r.is_upper_triangular());
                    assertTrue(decomposition.q.is_col_normal());
                    assertEquals(test, decomposition.q.times(decomposition.r));
                } catch (AssertionError err) {
                }
            }
        }
    }
}
