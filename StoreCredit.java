public class StoreCredit {
    private int creditID;
    private int RUID;
    private String email;
    private String expiryDate;


    public StoreCredit(int creditID, int RUID, String email, String expiryDate) {
        this.creditID = creditID;
        this.RUID = RUID;
        this.email = email;
        this.expiryDate = expiryDate;
    }


    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public int getRUID() {
        return RUID;
    }

    public void setRUID(int RUID) {
        this.RUID = RUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "StoreCredit{" +
                "creditID=" + creditID +
                ", RUID=" + RUID +
                ", email='" + email + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
