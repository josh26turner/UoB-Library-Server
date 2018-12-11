package spe.uoblibraryserver.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class Requester {
  static String ip(){
    try {
      URL url = new URL("http://ipinfo.io/ip");
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
    
      if (httpURLConnection.getResponseCode() == 200) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
      
        StringBuilder stringBuffer = new StringBuilder();
      
        while ((inputLine = reader.readLine()) != null)
          stringBuffer.append(inputLine);
      
        reader.close();
        httpURLConnection.disconnect();
      
        return stringBuffer.toString()+"\n";
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return "Bad URL\n";
    } catch (ProtocolException e) {
      e.printStackTrace();
      return "Bad Protocol\n";
    } catch (IOException e) {
      e.printStackTrace();
      return "Bad Something\n";
    }
    return "WAT\n";
  }
}
