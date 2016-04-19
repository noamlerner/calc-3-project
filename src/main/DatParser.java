package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Parses a Matrix Data File
 */
public class DatParser {

    public static Result<Matrix, Exception> matrix_from_path(String path) {
        try {
            // Make a reader
            BufferedReader reader = new BufferedReader(new FileReader(path));
            return Result.succeed(matrix_from_reader(reader));
        } catch (Exception err) {
            return Result.fail(err);
        }
    }

    public static Result<Vector, Exception> vector_from_path(String path) {
        Result<Matrix, Exception> result = matrix_from_path(path);
        if (result.is_err()) {
            return Result.fail(result.unwrap_err());
        }
        Matrix matrix = result.unwrap();
        if (matrix.getNumCols() != 1) {
            return Result.fail(new Exception("File does not contain a valid vector."));
        }
        return Result.succeed(Vector.from_matrix(matrix));
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
