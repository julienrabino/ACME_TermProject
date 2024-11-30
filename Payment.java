public class Payment {
    private int paymentID;
    private int RUID;
    private String fName;
    private String lName;
    private String cardNum;
    private String expiryDate;
    private String securityCode;

    public Payment(int paymentID, int RUID, String fName, String lName, String cardNum, String expiryDate, String securityCode) {
        this.paymentID = paymentID;
        this.RUID = RUID; // maybe just set RUID to -1 if theyre not an RU
        this.fName = fName;
        this.lName = lName;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
    }
    public Payment(int RUID, String fName, String lName, String cardNum, String expiryDate, String securityCode) {
        //this.paymentID = paymentID;
        this.RUID = RUID; // maybe just set RUID to -1 if theyre not an RU
        this.fName = fName;
        this.lName = lName;
        this.cardNum = cardNum;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
    }

    @Override
    public String toString(){
        return cardNum;
    }

    // Getters
    public int getPaymentID() {
        return paymentID;
    }

    public int getRUID() {
        return RUID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    // Setters
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setRUID(int RUID) {
        this.RUID = RUID;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

}
