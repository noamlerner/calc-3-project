package main.tests;

import main.Matrix;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InverseTest {
	@Test
	public void testInverse(){
		for(int i = 2; i < 7; i++){
			for(int j = 0; j < 10000; j++){
				inverseOfSize(i);
			}
		}
	}

	public void inverseOfSize(int size){
		Matrix A = Matrix.random(size, size);
		Matrix invA = A.inverse();
		Matrix I = Matrix.identity(size);
		// AA-1 = I
		assertEquals(I,A.times(invA));
		//A-1A=I
		assertEquals(I,invA.times(A));
	}
}
