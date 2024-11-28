import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieTheatreController {

    private myJDBC jdbc;

    public MovieTheatreController(myJDBC myJDBC) {
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

    public ArrayList<Seat> fetchSeats(int showtimeID) {
        String query = "SELECT seatID, seatRow, seatColumn, Available, RUReserved FROM SEAT WHERE Showtime = ?";
        ArrayList<Seat> seats = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, showtimeID);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int seatID = results.getInt("seatID");
                char seatRow = results.getString("seatRow").charAt(0);  // Assuming seatRow is a single character
                int seatColumn = results.getInt("seatColumn");
                boolean isAvailable = results.getBoolean("Available");
                boolean isRUReserved = results.getBoolean("RUReserved");

                Seat seat = new Seat(seatID, seatRow, seatColumn, isRUReserved, isAvailable);
                seats.add(seat);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
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

    public ArrayList<Showtime> fetchShowtimes(Location location, Movie movie) {
        ArrayList<Showtime> showtimes = new ArrayList<>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String query = "SELECT * FROM SHOWTIME WHERE TheatreID = ? AND MovieID = ?";
        try {
            PreparedStatement statement = jdbc.dbConnect.prepareStatement(query);
            statement.setInt(1, location.getLocationID());
            statement.setInt(2, movie.getMovieID());
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int showtimeID = results.getInt("ShowtimeID");
                Date showDate = results.getDate("ShowDate");
                Time showtime = results.getTime("Showtime");
                int totalRUSeats = results.getInt("TotalRUSeats");
                int totalOUSeats = results.getInt("TotalOUSeats");
                int availableRUSeats = results.getInt("AvailableRUSeats");
                int availableOUSeats = results.getInt("AvailableOUSeats");

                System.out.println(showtimeID);
                String showDateString = (showDate != null) ? dateFormatter.format(showDate) : "";
                String showtimeString = (showtime != null) ? timeFormatter.format(showtime) : "";


                Showtime toAdd = new Showtime(showtimeID, movie, showDateString, showtimeString, location,
                        totalRUSeats, totalOUSeats, availableRUSeats, availableOUSeats);
                showtimes.add(toAdd);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return showtimes;

    }

}
