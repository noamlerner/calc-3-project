package main.tests;

import main.Matrix;
import main.QrDecomp;
import main.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 4/13/16.
 */
public class QrDecompTests {

    @Test
    public void decomp_solve_easy_1() {
        QrDecomp easy = new QrDecomp(Matrix.identity(3), Matrix.identity(3));
        Vector solution = Vector.from_data(3, 5, 7);
        assertEquals(solution,  easy.solve(solution).unwrap());
    }

    @Test
    public void decomp_intermediate_1() {
        double[][] q = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        };
        double[][] r = {
            {10, 11, 12},
            { 0, 13, 14},
            { 0,  0, 15},
        };
        QrDecomp decomp = new QrDecomp(new Matrix(q), new Matrix(r));
        Vector solution = Vector.from_data(16, 17, 18);
        Vector x = Vector.from_data(-2739 / 1950.0, -4530 / 1950.0, 40560 / 1950.0);
        assertEquals(x, decomp.solve(solution).unwrap());
    }
}
