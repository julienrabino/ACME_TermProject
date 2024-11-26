
public class Seat {
    // Attributes
    private int seatID;
    private char seatRow;
    private int seatCol;
    private boolean isAnRUSeat;
    private boolean isAvailable;
    public Seat(int seatID, char seatRow, int seatCol, boolean isAnRUSeat, boolean isAvailable) {
        this.seatID = seatID;
        this.seatRow = seatRow;
        this.seatCol = seatCol;
        this.isAnRUSeat = isAnRUSeat;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public char getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(char seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(int seatCol) {
        this.seatCol = seatCol;
    }

    public boolean isAnRUSeat() {
        return isAnRUSeat;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean getAvailable(){
        return isAvailable;
    }
}


