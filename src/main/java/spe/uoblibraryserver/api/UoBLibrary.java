package spe.uoblibraryserver.api;

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
    return "sd02";
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
   */
  static String getPrivateKey() {
    return Keys.getCheckoutSecretKey();
  }
  
  /**
   * Returns the public key for access to NCIP on the WMS system
   * @return - the public key
   */
  static String getPublicKey() {
    return Keys.getCheckoutPubicKey();
  }
}
