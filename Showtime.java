public class Showtime {
    private int showtimeID;
    private Movie movie;
    private String time;
    private String date;

    private Location location;




    public Showtime(int showtimeID, Movie movie, String date, String time, Location location){
        this.showtimeID = showtimeID;
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.location = location;
    }



    @Override
    public String toString(){
        return time;
    }
    //getters
    public String getDate(){ return date;}
    public int getShowtimeID() {
        return showtimeID;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }


    //setters

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
