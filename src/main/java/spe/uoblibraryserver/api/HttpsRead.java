package spe.uoblibraryserver.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class HttpsRead {
  static String readResponse(HttpsURLConnection URLConnection) throws IOException {
    BufferedReader br =
            new BufferedReader(
                    new InputStreamReader(URLConnection.getInputStream()));

    String input;
    StringBuilder response = new StringBuilder();

    while ((input = br.readLine()) != null){
      response.append(input);
    }
    br.close();

    return response.toString();
  }
}
