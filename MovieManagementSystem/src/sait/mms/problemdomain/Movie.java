package sait.mms.problemdomain;

/**
 * @author Chris Wang
 * @version May 20, 2022
 */

public class Movie {

    private int duration;
    private String title;
    private int year;

    public Movie() {
    }

    public Movie(int duration, String title, int year) {
        this.duration = duration;
        this.title = title;
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }


    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%d\t\t%s\t%d",duration,title,year);
    }

    public String formatForFile() {
        String format = String.format("%d,%s,%d",duration,title,year);
        return format;
    }
}
