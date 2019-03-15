package spe.uoblibraryserver.api;

import org.xml.sax.SAXException;
import spe.uoblibraryserver.api.xml.Request;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

public class CheckOutRequest {
    private String
                accessToken,
                userID,
                itemID,
                location;

    public String getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getLocation() {
        return location;
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

    public void setLocation(String location) {
        this.location = location;
    }

    String checkOutRequest(String xml) {
        try {
            Request request = new Request(xml, this);

            UserAuth userAuth = new UserAuth();
            if (!userAuth.makeRequest(userID, accessToken)) return "Not authorized";

            String body = request.formatRequest(this);

            String auth = Hmac.getCheckoutHeader(UoBLibrary.getAdminUID());

            return makeRequest(auth, body);
            
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    
        return "";
    }

    private String makeRequest(String auth, String body) {
        try {
            URL checkoutURL = new URL("https://circ." + UoBLibrary.getDataCenter() + ".worldcat.org/ncip");
            HttpsURLConnection checkoutURLConnection = (HttpsURLConnection) checkoutURL.openConnection();

            checkoutURLConnection.setRequestProperty("Host", "circ." + UoBLibrary.getDataCenter() + ".worldcat.org");
            checkoutURLConnection.setRequestProperty("Authorization", auth);

            checkoutURLConnection.setRequestMethod("POST");
            checkoutURLConnection.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
            checkoutURLConnection.setRequestProperty("Accept", "application/xml");

            checkoutURLConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(checkoutURLConnection.getOutputStream());

            wr.writeBytes(body);
            wr.flush();wr.close();

            return readResponse(checkoutURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private String readResponse(HttpsURLConnection idManagementURLConnection) throws IOException {
        return HttpsRead.readResponse(idManagementURLConnection);
    }
}
