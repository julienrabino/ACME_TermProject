import java.util.ArrayList;

public class Location {
    private String address;
    private String locationName;
    private int locationID;
    private ArrayList<Movie> movies;
    
    public Location(String address, String locationName, int locationID){
        this.address = address;
        this.locationName = locationName;
        this.locationID = locationID;
        movies = new ArrayList<Movie>();
    }

    @Override
    public String toString(){
        return locationName;
    }

    //setters
    public void setAddress(String address) {
        this.address = address;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //getters
    public String getAddress() {
        return address;
    }
    public int getLocationID() {
        return locationID;
    }
    public ArrayList<Movie> getMovies() {
        return movies;
    }
    public String getName() {
        return this.locationName;
    }

    //more functions
    public boolean addMovie(Movie movie) {
        Movie toAdd = null;

        // check if the movie already exists in the list
        // checking by ID to prevent duplicate Hard Copies of a movie

        for (Movie current : this.movies) {
            if (current.getMovieID() == movie.getMovieID()) {
                toAdd = current;
                break;
            }
        }

        if (toAdd == null) {
            // if the movie does not exist, add it
            this.movies.add(movie);
            return true; // movie was added

        } else {
            System.out.println("Movie with ID " + movie.getMovieID() + " already exists.");
            return false;
        }
    }






}
