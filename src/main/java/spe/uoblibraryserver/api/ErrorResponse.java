package spe.uoblibraryserver.api;

import java.io.*;
import java.util.Date;

/**
 * Pushes errors into a log file
 * src/main/resources/ErrorResponse.log
 */
public class ErrorResponse {
  
  /**
   * When an unauthorised users try to check out a book
   * @param userID - the user
   * @param itemID - the book
   */
  public void authError(String userID, String itemID) {
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
  
  /**
   * Writes 404 messages to the file
   * @return - a 404 error message
   */
  static String error404() {
    Date date = new Date();
    String error = date.toString() + " Error 404\n";
  
    try {
      BufferedWriter errorLog = new BufferedWriter(
              new FileWriter("src/main/resources/ErrorResponse.log", true)
      );
    
      errorLog.write(error);
      errorLog.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("404 error");
    }
    
    return error;
  }
}
