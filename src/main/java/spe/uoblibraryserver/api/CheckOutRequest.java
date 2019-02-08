package spe.uoblibraryserver.api;

public class CheckOutRequest {
    private String accessToken;
    private String userID;
    private String itemID;

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

}
