package sait.mms.runners;

import sait.mms.problemdomain.*;

import sait.mms.problemdomain.Movie;

/**
 * Runner class for implementing the Movie class.
 * DO NOT modify any of the System.out.* lines of code in this file.
 * @author Chris Wang
 * @version May 20, 2022
 */
public class MovieRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Create Movie object:");
		/*
		 * Creates a Movie object using a user-defined constructor.
		 */
		int duration = 125;
		String title = "Tron: Legacy";
		int year = 2010;
		
		Movie movie = new Movie(duration, title, year);
		//
		System.out.println("===========================");
		System.out.println();
		
		System.out.println("Call movie.getDuration():");
		// Expected output: 125
		System.out.println(movie.getDuration());
		//
		System.out.println("===========================");
		System.out.println();
		
		System.out.println("Call movie.getTitle():");
		// Expected output: Tron: Legacy
		System.out.println(movie.getTitle());
		//
		System.out.println("===========================");
		System.out.println();
		
		System.out.println("Call movie.getYear():");
		// Expected output: 2010
		System.out.println(movie.getYear());
		//
		System.out.println("===========================");
		System.out.println();
		
		System.out.println("Call movie.formatForFile():");
		// Expected output: 125,Tron: Legacy,2010
		System.out.println(movie.formatForFile());
		//
		System.out.println("===========================");
		System.out.println();
		
		System.out.println("Call movie.toString():");
		// Expected output: 125		Tron: Legacy	2010
		System.out.println(movie);
		//
		System.out.println("===========================");
		System.out.println();
	}

}
