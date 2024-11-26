import java.sql.*;
import java.util.ArrayList;

public class MovieTheatreController {

    private myJDBC jdbc;

    public MovieTheatreController(myJDBC myJDBC){
        this.jdbc = myJDBC;
    }



    public void displayMovies(Location location) {
        ArrayList<Movie> allMovies = fetchMovies(location.getLocationID());
        if (allMovies.isEmpty()) {
            System.out.println("No movies available at this location.");
            return;
        }

        System.out.println("Movies at location: " + location.getName());
        for (Movie movie : allMovies) {
            System.out.println("Movie ID: " + movie.getMovieID());
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Genre: " + movie.getGenre());
        }
    }



    public void displayShowtimes(Location location) {
    }

    public void addShowtime(int id, Movie movie, String time, Location location, int numRUSeats, int numOUSeats) {
    }

    public void removeShowtime(Showtime showtime) {
    }



    public int produceMovieID() {
        return 0;
    }

    public void addMovie(int id, String title, String genre) {
    }

    public void removeMovie(Movie movie) {
    }

    public int produceShowtimeID() {
        return 0;
    }

    public ArrayList<Location> getMovieLocations(Movie movie) {
        int movieID = movie.getMovieID();
        String query = null;
        ArrayList<Location> locations = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            query = "select DISTINCT T.TheatreID, T.TheatreName, T.Address\n" +
                    "from THEATRE AS T, MOVIE AS M, SHOWTIME AS S\n" +
                    "where S.TheatreID = T.TheatreID and S.MovieID = M.MovieID and M.MovieID = ? ;";
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, movieID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int locationID = results.getInt("TheatreID");
                String address = results.getString("Address");
                String name = results.getString("TheatreName");
                Location theatre = new Location(address, name, locationID);
                locations.add(theatre);
                //System.out.println(name);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
    public ArrayList<Movie> fetchMovies(int locationID) {
        String query = null;
        ArrayList<Movie> movies = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            if (locationID == -1) {
                query = "SELECT * FROM MOVIE";
                statement = jdbc.dbConnect.prepareStatement(query);
            } else {
                query = "SELECT * FROM MOVIE WHERE MovieID IN (" +
                        "SELECT MovieID FROM SHOWTIME WHERE TheatreID = ?)";
                statement = jdbc.dbConnect.prepareStatement(query);
                statement.setInt(1, locationID);
            }

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int movieID = results.getInt("MovieID");
                String title = results.getString("Title");
                String genre = results.getString("Genre");

                Movie movie = new Movie(movieID, title, genre);
                movies.add(movie);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> fetchMovies(String search) {
        ResultSet results;
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            Statement myStmt = jdbc.dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT M.MovieID, M.Title, M.Genre " +
                    "FROM MOVIE AS M WHERE LOCATE(LOWER('" + search + "'), LOWER(M.Title)) > 0;");

            while (results.next()) {
                int movieID = results.getInt("MovieID");
                String title = results.getString("Title");
                String genre = results.getString("Genre");
                Movie movie = new Movie(movieID, title, genre);
                movies.add(movie);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return movies;
    }


}
