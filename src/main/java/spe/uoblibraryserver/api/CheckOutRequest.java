package spe.uoblibraryserver.api;

import org.xml.sax.SAXException;
import spe.uoblibraryserver.api.xml.Request;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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
    
    String checkOutRequest(String xml) {
        try {
            Request request = new Request(xml, this);
            String body = request.formatRequest(this);
    
            String auth = Hmac.getCheckoutHeader(userID);
            
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    
        return "";
    }
}
