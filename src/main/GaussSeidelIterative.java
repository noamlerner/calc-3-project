package main;

public class GaussSeidelIterative implements Iterative {
	Matrix A, b, x, LDinv, negU;
	double error;
	int N;

	public void iterate(Matrix Ab, Matrix u, double epsilon, int M){
		init(Ab);
		x = new Matrix(u.getData());
		boolean set = false;
		for(int i = 0; i < M; i++){
 			Matrix xk = LDinv.times(negU.times(x).plus(b));
			error = xk.minus(x).getMaxNorm();
			x = xk;
			if( error < epsilon){
				N = i;
				i = M;
				set = true;
			}
		}
		if(!set){
			x = null;
			N = -1;
		}
		
	}

	public Vector getSolution(){
		return new Vector(x);
	}

	public int getIterations(){
		return N;
	}

	public double getError(){
		return error;
	}

	public boolean hasTimedOut() {
		return N == -1;
	}

	private void init(Matrix Ab) {
		double [][] aArr = new double [Ab.getNumRows()][Ab.getNumCols()-1];
		double [][] bArr = new double [Ab.getNumRows()][1];
		double [][] ldArr = new double [aArr.length][aArr[0].length];
		double [][] uArr = new double [aArr.length][aArr[0].length];
		if(aArr.length != aArr[0].length){
			throw new RuntimeException("Matrix is the wrong size");
		}
		for(int i = 0; i < Ab.getNumRows(); i++){
			for(int j = 0; j < Ab.getNumCols()-1; j++){
				aArr[i][j] = Ab.getRow(i)[j];
				if(j > i){
					uArr[i][j] = -aArr[i][j];
				}else {
					ldArr[i][j] = aArr[i][j];
				}
			}
			bArr[i][0] = Ab.getRow(i)[Ab.getNumCols()-1];
		}
		A = new Matrix(aArr);
		b = new Matrix(bArr);
		LDinv = new Matrix(ldArr).inverse();
		negU = new Matrix(uArr);
	}
}
