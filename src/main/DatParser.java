package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Parses a Matrix Data File
 */
public class DatParser {

    public static Matrix matrix_from_path(String path) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return matrix_from_reader(reader);
   
    }

    public static Matrix vector_from_path(String path) throws Exception {
        Matrix matrix = matrix_from_path(path);
        if (matrix.getNumCols() != 1) {
            throw new Exception("File does not contain a valid vector.");
        }
        return Vector.from_matrix(matrix);
    }

    public static Matrix matrix_from_reader(BufferedReader reader) throws Exception {
        return new Matrix(reader.lines()
            .map(l -> l.split("\\s+"))
            .map(row -> Arrays.stream(row)
                .map(Double::parseDouble)
                .toArray(Double[]::new))
            .toArray(Double[][]::new));
    }
}
