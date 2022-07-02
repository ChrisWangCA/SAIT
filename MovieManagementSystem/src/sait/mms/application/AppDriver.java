package sait.mms.application;

import sait.mms.managers.MovieManagementSystem;

import java.io.*;

//import sait.mms.managers.*;

/**
 * Class description: Entry class into program.
 *
 * @author Chris Wang
 * @version May 20, 2022
 */
public class AppDriver {

	/**
	 * Entry method into a program.
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException {
//		 Uncomment so JAR file can be generated.
		MovieManagementSystem mms = new MovieManagementSystem();
		mms.displayMenu();
	}

}
