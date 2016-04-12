package main.tests;

import main.HouseHolders;
import main.Matrix;
import main.QrDecomp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by michael on 4/12/16.
 */
public class HouseholderTests {
    @Test
    public void dim_3_iter_2() {
        final double INV_SQRT_2 = 1 / Math.sqrt(2);
        final double SQRT_2 = Math.sqrt(2);
        double[][] data = {
            {-1, -1, 1},
            { 0,  1, 1},
            { 1,  1, 0},
        };
        double[][] correct_q = {
            { INV_SQRT_2,  0, -INV_SQRT_2},
            {          0, -1,           0},
            {-INV_SQRT_2,  0, -INV_SQRT_2},
        };
        double[][] correct_r = {
            {-SQRT_2, -SQRT_2,  INV_SQRT_2},
            {      0,      -1,          -1},
            {      0,       0, -INV_SQRT_2},
        };
        Matrix matrix = new Matrix(data);
        Matrix answer_q = new Matrix(correct_q);
        Matrix answer_r = new Matrix(correct_r);
        QrDecomp decomposition = HouseHolders.qr_fact_house(matrix).unwrap();

        // Check that its expeceted
        assertEquals(answer_q, decomposition.q);
        assertEquals(answer_r, decomposition.r);

        // Check that it still equals the original
        assertEquals(matrix, decomposition.q.times(decomposition.r));
    }

    @Test
    public void randomized_householder() {
        final int SIZES = 6;
        final int TRIALS = 10000;
        int successes = 0;
        int decompositions = 0;
        double total = (double) (SIZES - 2) * TRIALS;

        for (int size = 2; size < SIZES; size += 1) {
            for (int trial = 0; trial < TRIALS; trial += 1) {
                Matrix test = Matrix.random(size, size);
                QrDecomp decomposition = HouseHolders.qr_fact_house(test).unwrap();
                try {
                    assertTrue(decomposition != null);
                    assertTrue(decomposition.q != null);
                    assertTrue(decomposition.r != null);
                    assertTrue(decomposition.r.is_upper_triangular());
                    assertTrue(decomposition.q.is_col_normal());
                    decompositions += 1;
                    assertEquals(test, decomposition.q.times(decomposition.r));
                    successes += 1;
                } catch (AssertionError err) {
                }
            }
        }

        System.out.println("Decomposition: " + (decompositions * 100 / total) + "%");
        System.out.println("Full Success:  " + (successes * 100 / total) + "%");
    }
}
