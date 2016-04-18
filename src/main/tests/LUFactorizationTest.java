package main.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.LUFactorization;
import main.Matrix;
import main.Vector;

public class LUFactorizationTest {
	@Test
    public void testLU(){
		for(int i =2; i < 7; i++){
			testDimension(i);
		}
		// two by two solve
		Matrix m = new Matrix(new double [][] {{-1,2},{16,8}});
		Matrix b = new Matrix(new double [][]{{5},{13}});
		Matrix sol = new Matrix(new double [][]{{-0.35},{2.325}});
		testSolveForB(m,b,sol);
		// three by three
		m = new Matrix(new double [][]{{9,20,9},{4,13,9},{17,16,1}});
		b = new Matrix(new double [][]{{4},{3},{16}});
		sol = new Matrix(new double [][] {{2.206185567},{-1.43298969},{1.422680412}});
		testSolveForB(m,b,sol);

		
	}
	public void testSolveForB(Matrix m, Matrix b, Matrix sol){
		LUFactorization lu = new LUFactorization(m);
		Matrix s = lu.solveFor(b);
		assertEquals(s, sol);
	}
	
	public void testDimension(int n){
		for(int i = 0; i < 100000; i++){
			double [][] matrix = new double[n][n];
			for(int j =0; j < n; j++){
				for(int k =0; k < n; k++){
					matrix[j][k] = Math.random();
				}
			}
			Matrix m = new Matrix(matrix);
			LUFactorization lu = new LUFactorization(m);
			Matrix a = lu.getL().times(lu.getU());
			assertEquals(m, a);
		}
	}

}
