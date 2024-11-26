import java.util.ArrayList;

public class Movie {
    private String title;
    private String genre;
    private int movieID;
    private ArrayList<Showtime> showtimes;

    public Movie(int id, String title, String genre){
        this.movieID = id;
        this.title = title;
        this.genre = genre;
        this.showtimes = new ArrayList<>();

    }

    @Override
    public String toString(){
        return title;
    }


    public void removeShowtime(Showtime showtime){
        Showtime toRemove = null;
        for (Showtime current: this.showtimes){
            if (current.getShowtimeID() == showtime.getShowtimeID()){
                toRemove = current;
                break;
            }
        }

        if (toRemove != null){
            this.showtimes.remove(toRemove);
        }
    }
    //setters
    public void setGenre(String genre) {
        this.genre = genre;
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
