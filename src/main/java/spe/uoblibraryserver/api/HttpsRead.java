package spe.uoblibraryserver.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpsRead {
  public static String readResponse(HttpsURLConnection idManagementURLConnection) throws IOException {
    BufferedReader br =
            new BufferedReader(
                    new InputStreamReader(idManagementURLConnection.getInputStream()));

    String input;
    StringBuilder response = new StringBuilder();

    while ((input = br.readLine()) != null){
      response.append(input);
    }
    br.close();

    return response.toString();
  }

  private String getOAuthAccessToken(String authResponse) {
    String [] split1 = authResponse.split(":");
    String [] split2 = split1[1].split(",");
    return split2[0].replace("\"", "");
  }
}
