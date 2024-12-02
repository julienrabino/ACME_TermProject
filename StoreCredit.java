public class StoreCredit {
    private int creditID;
    private int RUID;
    private String email;
    private String expiryDate;
    private double amount;


    public StoreCredit(int creditID, int RUID, String email, double amount, String expiryDate) {
        this.creditID = creditID;
        this.amount = amount;
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
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        return "<html>" +
                "Email: " + email + "<br>" +
                "Expiry Date: " + expiryDate + "<br>" +
                "Amount: " + amount +
                "</html>";
    }

}
