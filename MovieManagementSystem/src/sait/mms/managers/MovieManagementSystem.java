package sait.mms.managers;

import sait.mms.problemdomain.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Chris Wang
 * @version May 20, 2022
 * Class description:
 * This movie management system has 4 functions,
 * 1. add movie,
 * 2. search movie according to year,
 * 3. generate movie information randomly,
 * 4. save data to file and exit
 */


public class MovieManagementSystem {
    //Movie information file path
    public static final String TEXT_FILE = "res/movies.txt";
    //Collect file data into array
    ArrayList<Movie> movieList = new ArrayList<>();
    Movie movies;


    //This method reads out the contents of the file and add it to the ArrayList
    public void loadMovies() throws FileNotFoundException {
        FileReader fr = new FileReader(TEXT_FILE);
        Scanner fileReader = new Scanner(fr);
        //Determine if the load is repeated
        if (movieList.size() == 0) {
            while (fileReader.hasNextLine()) {
                String movie = fileReader.nextLine();
                if (!movie.equals("")) {
                    int duration = Integer.parseInt(movie.split(",")[0]);
                    String title = movie.split(",")[1];
                    int year = Integer.parseInt(movie.split(",")[2]);
                    movies = new Movie(duration, title, year);
                    movieList.add(movies);
                }
            }
        }
    }


    //This method receives 3 parameters, then add them to the ArrayList
    public void addMovie(int duration, String title, int year) {
        if (duration < 0) {
            System.out.println("Error: The movie duration should be any number greater than zero.");
            return;
        }

        if (title.equals("")) {
            System.out.println("Error: The movie title should have at least one character.");
            return;
        }

        if (year <= 0) {
            System.out.println("Error: The release year should be any number greater than zero.");
            return;
        }

        movies = new Movie(duration, title, year);
        movieList.add(movies);
        System.out.println("Saving movies... ");
        System.out.println("Added movie to the data file.");

    }

    // This method receives an int number and finds the movie information based on the received year
    public void generateMoviesInYear(int year) {

        int minutes = 0;
        System.out.println("Movie List");
        System.out.printf("%s\t%s\t%s\n", "Duration", "Year", "Title");
        for (int i = 0; i < movieList.size(); i++) {
            int movieDuration = movieList.get(i).getDuration();
            int movieYear = movieList.get(i).getYear();
            String movieTitle = movieList.get(i).getTitle();
            if (movieYear == year) {
                System.out.printf("%d\t\t\t%d\t%s\n", movieDuration, movieYear, movieTitle);
                minutes += movieDuration;
            }
        }
        System.out.printf("Total duration: %d minutes\n", minutes);
    }

    //This method receives a number and randomly reads movie information from the ArrayList based on the received number
    public void generateRandomMovies(int num) {

        int minutes = 0;
        System.out.println("Movie List");
        System.out.printf("%s\t%s\t%s\n", "Duration", "Year", "Title");
        for (int i = 0; i < num; i++) {
            //Define a Random object to generate random numbers
            Random random = new Random();
            int n = random.nextInt(movieList.size());
            int movieDuration = movieList.get(n).getDuration();
            int movieYear = movieList.get(n).getYear();
            String movieTitle = movieList.get(n).getTitle();
            System.out.printf("%d\t\t\t%d\t%s\n", movieDuration, movieYear, movieTitle);
            minutes += movieDuration;
        }
        System.out.printf("Total duration: %d minutes\n", minutes);
    }

    //This method reads out the contents of the ArrayList and writes them to a file in a fixed form
    public void writeMoviesToFile() throws IOException {
        PrintWriter pw = new PrintWriter(TEXT_FILE);
        for (int i = 0; i < movieList.size(); i++) {
            Movie movie = movieList.get(i);
            pw.write(movie.getDuration() + "," + movie.getTitle() + "," + movie.getYear() + "\n");
            pw.flush();
        }
        pw.close();

    }

    //This method presents the menu to the user and receives the user's selection
    public void displayMenu() throws IOException {
        System.out.println(); // Adjust spacing
        //Loading file contents to ArrayList
        loadMovies();
        Scanner input = new Scanner(System.in);

        System.out.println("Movie Management system");
        System.out.println("1\tAdd New Movie and Save");
        System.out.println("2\tGenerate List of Movies Released in a Year");
        System.out.println("3\tGenerate List of Random Movies");
        System.out.println("4\tSave & Exit");
        System.out.println();
        System.out.print("Enter an option: ");
        int choice = input.nextInt();


        //Determine the route to run based on user input
        switch (choice) {
            //If the user enters 1, the user will be asked to enter the movie information and write it to the array.
            case 1:
                System.out.print("Enter duration: ");
                int duration = input.nextInt();
                input.nextLine();
                System.out.print("Enter movie title: ");
                String title = input.nextLine();
                System.out.print("Enter year: ");
                int year = input.nextInt();
                addMovie(duration, title, year);
//                writeMoviesToFile();
                displayMenu();
                //Inputting 2 will ask the user to enter a year and call the generateMoviesInYear method to generate a list of movies.
            case 2:
                System.out.print("Enter in year:");
                int year1 = input.nextInt();
                generateMoviesInYear(year1);
                displayMenu();
                //Input 3 will call the generateRandomMovies method to generate random movie information.
            case 3:
                System.out.print("Enter number of movies:");
                choice = input.nextInt();
                if (choice > 0 && choice < movieList.size()) {
                    generateRandomMovies(choice);
                } else {
                    System.out.println("The number is incorrect");
                }
                displayMenu();

                //Input 4 will write the data in the ArrayList to a file
            case 4:
                writeMoviesToFile();
                System.out.println("Saving movies... ");
                System.out.println("Movie list saved successfully!");
                System.out.println("Goodbye!");
                System.exit(0);

                //If user input any other number will run default.
            default:
                System.out.println("Invalid option! Please try again.");
                displayMenu();

        }

    }

}
