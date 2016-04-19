package main;

public class CLI {
	public static void main(String[] args) {
		try{		
			switch(args[0]){
				case "lu_fact":
					System.out.println(lu_fact());
					break;
				case "qr_fact_house":
					System.out.println(qr_fact_house());
					break;
				case "qr_fact_givens":
					System.out.println(qr_fact_givens());
					break;
				case "solve_lu":
					System.out.println(solve_lu());
					break;
				case "solve_qr_house":
					System.out.println(solve_qr_house());
					break;
				case "solve_qr_givens":
					System.out.println(solve_qr_givens());
					break;
				case "jacobi_iter":
					System.out.println(jacobi_iter());
					break;
				case "gs_iter":
					System.out.println(gs_iter());
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
					System.err.println(invalidString());
					break;
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
	private static String lu_fact(){
		return "";
	}
	private static String qr_fact_house(){
		return "";
	}
	private static String qr_fact_givens(){
		return "";
	}
	private static String solve_lu(){
		return "";
	}
	private static String solve_qr_house(){
		return "";
	}
	private static String solve_qr_givens(){
		return "";
	}
	private static String jacobi_iter(){
		return "";
	}
	private static String gs_iter(){
		return "";
	}
	private static String power_method(){
		return "";
	}
	private static String help(){
		return "";
	}
	private static String invalidString(){
		return "";
	}
}
