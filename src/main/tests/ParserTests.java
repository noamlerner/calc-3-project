package main.tests;

import main.DatParser;
import main.Matrix;
import main.Vector;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by michael on 4/18/16.
 */
public class ParserTests {
    @Test
    public void test_simple_3x3() throws Exception {
        String matrix =
                "1 2 3\n" +
                "4 5 6\n" +
                "7 8 9\n";
        ByteArrayInputStream bains = new ByteArrayInputStream(matrix.getBytes());
        InputStreamReader ins_reader = new InputStreamReader(bains);
        BufferedReader reader = new BufferedReader(ins_reader);

        Matrix correct_parse = new Matrix(new double[][] {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        });

        assertEquals(correct_parse, DatParser.matrix_from_reader(reader));
    }

    @Test
    public void test_simple_3x3_no_eof_newline() throws Exception {
        String matrix =
                "1 2 3\n" +
                "4 5 6\n" +
                "7 8 9";
        ByteArrayInputStream bains = new ByteArrayInputStream(matrix.getBytes());
        InputStreamReader ins_reader = new InputStreamReader(bains);
        BufferedReader reader = new BufferedReader(ins_reader);

        Matrix correct_parse = new Matrix(new double[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        });

        assertEquals(correct_parse, DatParser.matrix_from_reader(reader));
    }

    @Test
    public void test_simple_3x1() throws Exception {
        String matrix =
                "1\n" +
                "4\n" +
                "7";
        ByteArrayInputStream bains = new ByteArrayInputStream(matrix.getBytes());
        InputStreamReader ins_reader = new InputStreamReader(bains);
        BufferedReader reader = new BufferedReader(ins_reader);

        Vector correct_parse = Vector.from_data(1, 4, 7);

        assertEquals(correct_parse, DatParser.matrix_from_reader(reader));
    }
}
