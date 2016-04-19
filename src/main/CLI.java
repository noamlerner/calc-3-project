package main;

public class CLI {
	public static void main(String[] args) {
		try{		
			switch(args[0]){
				case "lu_fact":
					check_arg_length(args, 2);
					System.out.println(lu_fact(args));
					break;
				case "qr_fact_house":
					System.out.println(qr_fact_house());
					break;
				case "qr_fact_givens":
					System.out.println(qr_fact_givens());
					break;
				case "solve_lu":
					check_arg_length(args, 2);
					System.out.println(solve_lu(args));
					break;
				case "solve_qr_house":
					System.out.println(solve_qr_house());
					break;
				case "solve_qr_givens":
					System.out.println(solve_qr_givens());
					break;
				case "jacobi_iter":
					check_arg_length(args, 5);
					System.out.println(jacobi_iter(args));
					break;
				case "gs_iter":
					check_arg_length(args, 5);
					System.out.println(gs_iter(args));
					break;
				case "power_method":
					System.out.println(power_method());
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
		Matrix A = DatParser.matrix_from_path(args[1]);
		LUFactorization lu = new LUFactorization(A);
		return "L:\n" + lu.getL() + "\nU:\n" + lu.getU() + "\nerror:\n"+lu.getError();
	}
	private static String qr_fact_house(){
		return "";
	}
	private static String qr_fact_givens(){
		return "";
	}
	private static String solve_lu(String [] args) throws Exception{
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		LUSolver lus = new LUSolver();
		Vector x = lus.solve(Ab);
		return "Solution: \n" + x;
	}
	private static String solve_qr_house(){
		return "";
	}
	private static String solve_qr_givens(){
		return "";
	}
	private static String jacobi_iter(String [] args) throws Exception{
		double epsilon = Double.parseDouble(args[3]);
		int M = Integer.parseInt(args[4]);
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		Matrix u = DatParser.matrix_from_path(args[2]);
		JacobiIterative ji = new JacobiIterative();
		ji.iterate(Ab, u, epsilon, M);
		return "Solution: \n" + ji.getSolution() + "\nIterations\n" + ji.getIterations() + "\nError\n" + ji.getError();
	}
	private static String gs_iter(String [] args) throws Exception{
		double epsilon = Double.parseDouble(args[3]);
		int M = Integer.parseInt(args[4]);
		Matrix Ab = DatParser.matrix_from_path(args[1]);
		Matrix u = DatParser.matrix_from_path(args[2]);
		GaussSeidelIterative gs = new GaussSeidelIterative();
		gs.iterate(Ab, u, epsilon, M);
		return "Solution: \n" + gs.getSolution() + "\nIterations\n" + gs.getIterations() + "\nError\n" + gs.getError();
	}
	private static String power_method(){
		return "";
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
