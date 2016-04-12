package main.tests;

import main.HouseHolders;
import main.Matrix;
import main.QrDecomp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
