package main.tests;

import main.HouseHolders;
import main.Matrix;
import main.QrDecomp;
import org.junit.Test;

/**
 * Created by michael on 4/12/16.
 */
public class HouseholderTests {
    @Test
    public void dim_3_iter_1() {
        double[][] data = {
            {-1, -1, 1},
            { 0,  1, 1},
            { 1,  1, 0},
        };
        Matrix matrix = new Matrix(data);
        QrDecomp decomposition = HouseHolders.qr_fact_house(matrix).unwrap();
        System.out.println(decomposition);
        assert false;
        // TODO: Finish this
    }
}
