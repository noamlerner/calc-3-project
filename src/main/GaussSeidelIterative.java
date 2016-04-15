package main;

public class GaussSeidelIterative implements Iterative {
	Matrix A, b, x;
	double error;
	int N;

	public void iterate(Matrix Ab, Matrix u, double epsilon, int M){
		init(Ab);
		// didn't want to find inverse of matrix and its not in library so i did it this way
		x = new Matrix(u.getData());
		boolean set = false;
		for(int i = 0; i < M; i++){
			double [][] xk = new double[x.getData().length][1];
			for(int j = 0; j < A.getNumRows(); j++){
				double [] row = A.getRow(j);
				double sum = b.getRow(j)[0];
				for(int k = 0; k < row.length; k++){
					if(k != j){
						if(k>j){
							sum -= row[k] * x.getRow(k)[0];
						} else {
							sum -= row[k] * xk[k][0];
						}
					}
				}
				xk[j][0] = sum / row[j];
			}
			Matrix xkMat = new Matrix(xk);
			error = xkMat.minus(x).getMaxNorm();
			if( error < epsilon){
				N = i;
				i = M;
				set = true;
			}
		}
		if(!set){
			N = -1;
			error = -1;
			x = null;
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
		if(aArr.length != aArr[0].length){
			throw new RuntimeException("Matrix is the wrong size");
		}
		for(int i = 0; i < Ab.getNumRows(); i++){
			for(int j = 0; j < Ab.getNumCols()-1; j++){
				aArr[i][j] = Ab.getRow(i)[j];
			}
			bArr[i][0] = Ab.getRow(i)[Ab.getNumCols()-1];
		}
		A = new Matrix(aArr);
		b = new Matrix(bArr);
	}
}
