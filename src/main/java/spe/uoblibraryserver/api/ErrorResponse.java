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
    String error = date.toString() + ", user: " + userID + " taking out: " + itemID;
    
    writeToLog(error);
  }
  
  /**
   * Writes 404 messages to the file
   * @return - a 404 error message
   */
  public static String error404() {
    Date date = new Date();
    String error = date.toString() + " Error 404";
  
    writeToLog(error);
    System.out.println("404 error");
    
    return error;
  }

  /**
   * When an erroneous body is sent to the checkout URL
   * @param xml - the body of the POST method
   */
  public static void erroneousXML(String xml) {
    Date date = new Date();
    String error = date.toString() + " Erroneous XML: ";

    writeToLog(error + xml);
  }

  /**
   * Writing an error to the log file
   * @param record - The error to record in the log file
   */
  private static void writeToLog(String record) {
    File file = new File("ErrorResponse.log");
    
    try {
      file.createNewFile();
      
      BufferedWriter errorLog = new BufferedWriter(
              new FileWriter("ErrorResponse.log", true)
      );

      errorLog.write(record + "\n");
      errorLog.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
