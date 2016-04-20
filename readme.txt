#######################
# RUNNING THE PROGRAM #
#######################

To run the program, from the root of the project:
   `java -jar cli.jar'

To see a list of commands and their arguments:
   `java -jar cli.jar help'

Each command name matches the function names from the pdf:
	 e.x. "solve_lu" matches "solve_lu" from the pdf.
	 To run any command:
	 `java -jar cli.jar <command_name> <args seperated by spaces>'
	 	e.x.    `java -jar cli.jar lu_fact A.dat' 
	 		in this example, A.dat is a dat file containing a matrix. The file is located in the same directory as the jar file.

There are in additional 3 commands:
	`java -jar cli.jar hilbert'
	`java -jar cli.jar iterative_convergance'
	`java -jar cli.jar power_convergence'
#############
# DAT files #
#############

Any "dat" file asked for is a matrix where each line is a row of the matrix.
Each row is a comma or space seperated list of double precision floating
point elements of the matrix.
