import java.sql.*;
import java.util.ArrayList;
// can be deleted ???
public class MovieDatabaseManager {
    private myJDBC jdbc;

    public MovieDatabaseManager(myJDBC jdbc) {
        this.jdbc = jdbc;
    }

    public ArrayList<Movie> fetchMovies(int locationID) {
        String query = null;
        ArrayList<Movie> movies = new ArrayList<>();
        PreparedStatement statement = null;


        try {
            if (locationID == -1){
                query = "SELECT * FROM MOVIE";
                statement = jdbc.dbConnect.prepareStatement(query);

            } else{
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

    public ArrayList<Movie> fetchMovies(String search ) {
        ResultSet results;
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            Statement myStmt = jdbc.dbConnect.createStatement();
            results = myStmt.executeQuery("select M.MovieID, M.Title\n" +
                    "from MOVIE as M\n" +
                    "where LOCATE(LOWER('" + search + "'), LOWER(M.Title)) > 0;");

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
