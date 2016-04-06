
public class LUFactorization {
	Matrix A;
	Matrix L;
	Matrix U;
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
			if(div == 0){
				for(int j = i; j < U.getNumRows(); j++){
					if(U.getRow(j)[0] != 0){
						U.swap(i,j);
						col = U.getCol(i);
						div = col[i];
						j = U.getNumRows();
					}
				}
			}
			for(int j = 0; j < col.length; j++){
				if(j < i){
					col[j]=0;
				}else {
					col[j] = div == 0 ? 0 : col[j]/div;
				}
			}
			col[i] = 1;
			L.setCol(i, col);
			for(int j = i+1; j < U.getNumRows(); j++){
				double [] row = U.getRow(j);
				U.addRowToRow(i, j, -1*row[i]/div);
			}
		}
	}
	public Matrix getL(){
		return L;
	}
	public Matrix getU(){
		return U;
	}

}
 