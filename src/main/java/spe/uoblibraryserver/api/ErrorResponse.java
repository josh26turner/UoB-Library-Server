package spe.uoblibraryserver.api;

import java.io.*;
import java.util.Date;

public class ErrorResponse {
  public ErrorResponse(String userID, String itemID) {
    Date date = new Date();
    String error = date.toString() + ", user: " + userID + " taking out: " + itemID + "\n";
    
    try {
      BufferedWriter errorLog = new BufferedWriter(
              new FileWriter("src/main/resources/ErrorResponse.log", true)
      );
      
      errorLog.write(error);
      errorLog.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("An error occurred adding the error to the log ?????");
    }
  }
}
