#######################
# RUNNING THE PROGRAM #
#######################

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!                IMPORTANT                 !
! You must run these commands using Java 8 !
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

To run the program, type this into your command prompt from the root of the project:
	`java -jar cli.jar'
	(Note, this will not run anything because no arguments are passed in, arguments are addressed below.)

To see a list of commands and their arguments type:
	'java -jar cli.jar help'

Each command name matches the function names from the pdf:
	e.x. "solve_lu" matches "solve_lu" from the pdf.
To run any command: `java -jar cli.jar <command_name> <args seperated by spaces>'
	e.x.`java -jar cli.jar lu_fact A.dat' 
	in this example, A.dat is a dat file containing a matrix. The file is located in the same directory as the jar file.

There are in additional 3 commands:
	`java -jar cli.jar hilbert'
	`java -jar cli.jar iterative_convergance'
	`java -jar cli.jar power_convergence'
Each of these will provide the output for the corresponding problem as described in the pdf
As well, gnuplot compatible data ouput has been stored in the data folder, these can be used to reconstruct any graph in the project.
#############
# DAT files #
#############

Any "dat" file asked for is a matrix where each line is a row of the matrix.
Each row is a comma or space seperated list of double precision floating
point elements of the matrix.

#############
# Analysis  #
#############
All of the analysis are in the analysis folder.
