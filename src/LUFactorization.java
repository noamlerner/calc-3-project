public class LUFactorization {
	Matrix A;
	Matrix L;
	Matrix U;
	double error;
	public LUFactorization(Matrix m){
		A = m;
		if(A.getNumCols() != A.getNumRows()){
			throw new RuntimeException("The matrix for LU Factorization needs to have the same number of rows and columns");
		}
		L = new Matrix(A.getNumRows(),A.getNumRows());
		U = new Matrix(A.getData());
		A.show();
		for(int i = 0; i < U.getNumCols();i++){
			double [] col = U.getCol(i);
			double div = col[i];
			// try to swap rows if the pivot is a 0
			if(div == 0){
				for(int j = i; j < U.getNumRows(); j++){
					if(U.getRow(j)[0] != 0){
						U.swap(i,j);
						col = U.getCol(i);
						div = col[i];
						j = U.getNumRows();
					}
				}
				// if nothing was found, all entries are 0 from the pivot down
				if(div == 0){
					
				}
			}
			// divide col
			for(int j = 0; j < col.length; j++){
				if(j < i){
					col[j]=0;
				}else {
					col[j] = div == 0 ? 0 : col[j]/div;
				}
			}
			// set pivot of l to 1
			col[i] = 1;
			L.setCol(i, col);
			// apply to U
			for(int j = i+1; j < U.getNumRows(); j++){
				double [] row = U.getRow(j);
				U.addRowToRow(i, j, -1*row[i]/div);
			}
		}
		error = L.times(U).minus(A).getMaxNorm();
	}
	public Matrix getL(){
		return L;
	}
	public Matrix getU(){
		return U;
	}
	public double getError(){
		return error;
	}
	public Matrix solveFor(Matrix b){
		if(b.getNumCols() != 1){
			throw new RuntimeException("b needs to be a vector");
		}
		if(b.getNumRows() != A.getNumRows()){
			throw new RuntimeException("b and A must have the same amount of rows");
		}
		double [] y = new double[A.getNumRows()];
		for(int i = 0; i < A.getNumRows(); i++){
			y[i] = b.getRow(i)[0];
			for(int j = 0; j < i; j++){
				y[i] -= L.getRow(i)[j] * y[j];
			}
		}
		
		double [][] x = new double[1][y.length];
		for(int i = x[0].length -1; i >=0; i--){
			x[0][i] = y[i];
			for(int j = x[0].length-1; j >i; j--){
				x[0][i] -= U.getRow(i)[j] * x[0][j];
			}
			x[0][i] /= U.getRow(i)[i];
		}
		return new Matrix(x);
	}
}
 