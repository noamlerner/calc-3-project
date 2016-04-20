package main;

import main.PowerMethod.PowerMethodErr;
import main.PowerMethod.EigenEstimate;

public class CLI {

	public static void main(String[] args) {
		try {
			switch(args[0]) {
				case "lu_fact":
					System.out.println(lu_fact(args));
					break;
				case "qr_fact_house":
					System.out.println(qr_fact_house(args));
					break;
				case "qr_fact_givens":
					System.out.println(qr_fact_givens(args));
					break;
				case "solve_lu":
					System.out.println(solve_lu(args));
					break;
				case "solve_qr_house":
					System.out.println(solve_qr_house(args));
					break;
				case "solve_qr_givens":
					System.out.println(solve_qr_givens(args));
					break;
				case "jacobi_iter":
					System.out.println(jacobi_iter(args));
					break;
				case "gs_iter":
					System.out.println(gs_iter(args));
					break;
				case "power_method":
					System.out.println(power_method(args));
					break;
				case "help":
				case "":
				case "h":
					System.out.println(help());
					break;
				default:
					invalidString();
					break;
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("type help for a list of options and the arguments required");
			System.exit(1);
		}
	}

	public static void check_arg_length(String [] args, int argLength){
		if(args.length != argLength){
			throw new Error("You did not pass in the correct amount of arguments for the command you chose");
		}
	}

	private static String lu_fact(String [] args) throws Exception{
        check_arg_length(args, 2);
		Matrix A = DatParser.matrix_from_path(args[1]);
		LUFactorization lu = new LUFactorization(A);
		return "A:\n"+A+"L:\n" + lu.getL() + "\nU:\n" + lu.getU() + "\nerror:\n"+lu.getError();
	}

	private static String qr_fact_house(String[] args) throws Exception {
        check_arg_length(args, 2);
        Matrix matrix = DatParser.matrix_from_path(args[1]);
        QrDecomp decomp = HouseHolders.qr_fact_house(matrix).unwrap();
        return "A:\n"+matrix+"Q:\n" + decomp.q + "\nR:\n" + decomp.r;
	}

	private static String qr_fact_givens(String[] args) throws Exception {
        check_arg_length(args, 2);
        Matrix matrix = DatParser.matrix_from_path(args[1]);
        QrDecomp decomp = HouseHolders.qr_fact_house(matrix).unwrap();
        return "A:\n"+matrix+"Q:\n" + decomp.q + "\nR:\n" + decomp.r;
	}

	private static String solve_lu(String [] args) throws Exception{
        check_arg_length(args, 2);
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		LUSolver lus = new LUSolver();
		Vector x = lus.solve(Ab);
		return "Ab:\n"+Ab+"Solution: \n" + x;
	}

	private static String solve_qr_house(String[] args) throws Exception {
        check_arg_length(args, 2);
        Matrix matrix = DatParser.matrix_from_path(args[1]);
        Vector b = matrix.getSubColumn(matrix.getNumCols() - 1, matrix.getNumRows());
        double[][] system_data = new double[matrix.getNumRows()][matrix.getNumRows()];
        for (int i = 0; i < matrix.getNumRows(); i += 1) {
            for (int j = 0; j < matrix.getNumRows(); j += 1) {
                system_data[i][j] = matrix.data[i][j];
            }
        }
        Matrix system = new Matrix(system_data);
        Solver solver = new HouseHolderSolver();
        Vector solution = solver.solve(system, b);
        return "Ab:\n"+matrix+"Solution:\n" + solution;
	}

	private static String solve_qr_givens(String[] args) throws Exception {
        check_arg_length(args, 2);
        Matrix matrix = DatParser.matrix_from_path(args[1]);
        Vector b = matrix.getSubColumn(matrix.getNumCols() - 1, matrix.getNumRows());
        double[][] system_data = new double[matrix.getNumRows()][matrix.getNumRows()];
        for (int i = 0; i < matrix.getNumRows(); i += 1) {
            for (int j = 0; j < matrix.getNumRows(); j += 1) {
                system_data[i][j] = matrix.data[i][j];
            }
        }
        Matrix system = new Matrix(system_data);
        Solver solver = new GivensSolver();
        Vector solution = solver.solve(system, b);
        return "Ab:\n"+matrix+"Solution:\n" + solution;
	}

	private static String jacobi_iter(String [] args) throws Exception{
        check_arg_length(args, 5);
		double epsilon = Double.parseDouble(args[3]);
		int M = Integer.parseInt(args[4]);
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		Matrix u = DatParser.matrix_from_path(args[2]);
		JacobiIterative ji = new JacobiIterative();
		ji.iterate(Ab, u, epsilon, M);
		return "Ab:\n"+Ab+"Solution: \n" + ji.getSolution() + "\nIterations: " + ji.getIterations() + "\nError: " + ji.getError();
	}

	private static String gs_iter(String [] args) throws Exception{
        check_arg_length(args, 5);
		double epsilon = Double.parseDouble(args[3]);
		int M = Integer.parseInt(args[4]);
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		Matrix u = DatParser.matrix_from_path(args[2]);
		GaussSeidelIterative gs = new GaussSeidelIterative();
		gs.iterate(Ab, u, epsilon, M);
		return "Ab:\n"+Ab+"Solution: \n" + gs.getSolution() + "\nIterations: " + gs.getIterations() + "\nError: " + gs.getError();
	}

	private static String power_method(String[] args) throws Exception {
        check_arg_length(args, 6);
        Matrix matrix = DatParser.matrix_from_path(args[1]);
        Vector guess = DatParser.vector_from_path(args[2]);
        Vector auxiliary = DatParser.vector_from_path(args[3]);
        double epsilon = Double.parseDouble(args[4]);
        int iterations = Integer.parseInt(args[5]);
        Result<EigenEstimate, PowerMethodErr> result = PowerMethod.power_method(matrix, guess, auxiliary, epsilon, iterations);
        if (result.is_err()) {
            throw new Exception(result.unwrap_err().toString());
        }
        EigenEstimate estimate = result.unwrap();
        return "A:\n"+matrix+"Iterations: " + estimate.iteration + "\n" +
               "Reached epsilon before max iterations: " + estimate.timedOut +
               "Eigenvalue: " + estimate.value + "\n" +
               "Eigen Vector:\n" + estimate.vector + "\n";
	}

	private static String help(){
		String filePathToMatrix = "a filepath to a file containing a matrix";
		String filePathToU = "a filepath to a file containing the initial vector";
		String epsIter = "a small positive tolerance number\n\t\ta positive integer "
				+ "for the maximum number of iterations";
		return "usage: the first argument needs to be the method you want to use followed by the commands."
				+ "The possible commands along with their paramters are:"
				+ "\ncommands: lu_fact qr_fact_house qr_fact_givens \n\t arguments:\n\t\t" + filePathToMatrix
				+ "\ncommands: solve_qr_house solve_lu solve_qr_givens \n\t arguments:\n\t\t" + filePathToMatrix
				+ "\n\t\t"+filePathToU+"\n\t\t" + epsIter
				+ "\ncommands:power_method \n\t arguments:\n\t\t" + filePathToMatrix
				+ "\n\t\t" + filePathToU + "\n\t\ta filepath to a file containing the auxilary vector"
				+ "\n\t\t" + epsIter;
	}

	private static void invalidString(){
		throw new Error("The command you passed in was invalid");
	}
}
