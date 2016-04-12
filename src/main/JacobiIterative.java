package main;

public class JacobiIterative {
	Matrix A, Dinv, R, b,x; // D is actually the inverse of D cause that's whats needed
	double error;
	int N;
	public JacobiIterative(Matrix Ab, Matrix u, double epsilon, int M){
		init(Ab);
		x = new Matrix(u.getData());
		boolean set = false;
		for(int i = 0; i < M; i++){
			Matrix xk = Dinv.times(b.minus(R.times(x)));
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
			error = -1;
			N = -1;
		}
		
	}
	public Matrix getSolution(){
		return x;
	}
	public int getIterations(){
		return N;
	}
	public double getError(){
		return error;
	}
	private void init(Matrix Ab) {
		double [][] aArr = new double [Ab.getNumRows()][Ab.getNumCols()-1];
		double [][] bArr = new double [Ab.getNumRows()][1];
		double [][] dArr = new double [Ab.getNumRows()][Ab.getNumCols()-1];
		double [][] rArr = new double [Ab.getNumRows()][Ab.getNumCols()-1];		
		if(aArr.length != aArr[0].length){
			throw new RuntimeException("Matrix is the wrong size");
		}
		for(int i = 0; i < Ab.getNumRows(); i++){
			for(int j = 0; j < Ab.getNumCols()-1; j++){
				aArr[i][j] = Ab.getRow(i)[j];
				if(i == j){
					dArr[i][j] = 1/Ab.getRow(i)[j]; // 1 /num produces the inverse
				}else {
					rArr[i][j] = Ab.getRow(i)[j];
				}
			}
			bArr[i][0] = Ab.getRow(i)[Ab.getNumCols()-1];
		}
		A = new Matrix(aArr);
		b = new Matrix(bArr);
		Dinv = new Matrix(dArr);
		R = new Matrix(rArr);
	}
}
