package spe.uoblibraryserver.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * A class to store the data for the university to use in requests to WMS
 */
public class UoBLibrary {
  
  /**
   * To get the registry to put in body
   * @return - the uni registry ID in OCLC
   */
  public static String getRegistryId() {
    return "132607";
  }

  /**
   * Get the data center for use in the URL
   * @return - the data center
   */
  public static String getDataCenter() {
    return "EMEA";
  }

  /**
   * Make a nonce value for HMAC
   * @return - a nonce value
   */
  public static String getNonce() {
    Random random = new Random();

    int i = random.nextInt(Integer.MAX_VALUE);
    int j = random.nextInt(Integer.MAX_VALUE);

    return Integer.toString(i) + j;
  }
  
  /**
   * Returns the private key for access to NCIP on the WMS system
   * @return - the private key
   * @throws IOException - if the file doesn't exist
   */
  static String getPrivateKey() throws IOException {
    FileReader fileReader = new FileReader("src/main/resources/WMSSecretKey.key");
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    return bufferedReader.readLine();
  }
  
  /**
   * Returns the public key for access to NCIP on the WMS system
   * @return - the public key
   * @throws IOException - if the file doesn't exist
   */
  static String getPublicKey() throws IOException {
    FileReader fileReader = new FileReader("src/main/resources/WMSPublicKey.key");
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    return bufferedReader.readLine();
  }
}
