import java.util.ArrayList;

public class Movie {
    private String title;
    private String genre;
    private int movieID;
    private String releaseDate;
    private ArrayList<Showtime> showtimes;

    public Movie(int id, String title, String genre, String releaseDate){
        this.movieID = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.showtimes = new ArrayList<>();

    }

    @Override
    public String toString(){
        return title;
    }



    //setters
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setReleaseDate(String date) {
        this.releaseDate = date;
    }
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //getters
    public String getGenre() {
        return genre;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public int getMovieID() {
        return movieID;
    }
    public ArrayList<Showtime> getShowtimes() {
        return showtimes;
    }
    public String getTitle() {
        return title;
    }
    


}
