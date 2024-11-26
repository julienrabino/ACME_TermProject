public class UserDatabaseManager {
    private myJDBC jdbc;

    UserDatabaseManager(myJDBC db){
        this.jdbc = db;
    }

    int getIDFromUsrName(String search) {

    }

    public RegisteredUser fetchUser(int id){
        // TO DO:
        // query db to find user's details
        // must use db info to make an RU Object, which should be returned
        return null;
    }
}
