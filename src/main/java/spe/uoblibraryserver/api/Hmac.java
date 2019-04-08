package spe.uoblibraryserver.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

/**
 * Forms the relevant HMAC signatures for the request made by the server
 */
class Hmac {

  /**
   * Forms the message to be encrypted by the HMAC algorithm
   * @param time - time of request
   * @param nonce - random number
   * @param query - the URL parameters
   * @param publicKey - the respective public key to the one encrypting
   * @return - an unencrypted message
   */
  private static String getMessage(Long time, String nonce, String query, String publicKey) {
    return publicKey + "\n"
            + time + "\n"
            + nonce + "\n"
            + "\n"
            + "POST" + "\n"
            + "www.oclc.org" + "\n"
            + "443" + "\n"
            + "/wskey" + "\n"
            + query;
  }

  /**
   * Create a HMAC message with the SHA256 algorithm
   * @param message - what is the be encrypted
   * @param privateKey - the key with which to encrypt
   * @return - the encrypted message
   * @throws NoSuchAlgorithmException - thrown if the algorithm doesn't exist
   * @throws InvalidKeyException - thrown if the key is of the correct form
   */
  static String getHash(String message, String privateKey) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256 = Mac.getInstance("HmacSHA256");
    
    SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
    sha256.init(secretKey);
    
    Base64.Encoder encoder = Base64.getEncoder();
  
    return encoder.encodeToString(sha256.doFinal(message.getBytes()));
  }

  /**
   * Creates a header for an oauth request
   * @param query - the URL parameters
   * @param userID - the user making the request
   * @return - the header
   */
  static String getOAuthHeader(String query, String userID) {
    String [] oAuthKeys = Keys.getOAuthKeys();
    return header(oAuthKeys[1], oAuthKeys[0], query, userID);
  }

  /**
   * Creates a header for a checkout request
   * @param userID - the checkout user
   * @return - the header
   */
  static String getCheckoutHeader(String userID) {
    String [] checkoutKeys = Keys.getCheckoutKeys();
    return header(checkoutKeys[1], checkoutKeys[0], "", userID);
  }

  /**
   * Form a generic authorization header
   * @param publicKey - the public key for the request
   * @param privateKey - the private key for the request
   * @param query - the URL parameters
   * @param userID - the user making the request
   * @return - the authorization header
   */
  private static String header(String publicKey, String privateKey, String query, String userID) {
    Date date = new Date();
    String nonce = UoBLibrary.getNonce();
    long time = date.getTime() / 1000;
    String message = getMessage(time, nonce, query, publicKey);
    String hash = "";
    try {
      hash = getHash(message, privateKey);
    } catch (Exception ex){
      // This shouldn't be thrown as we have controlled the key and algorithm input
    }
    return "http://www.worldcat.org/wskey/v2/hmac/v1 " +
            "clientId=\"" + publicKey + "\", " +
            "timestamp=\"" + time + "\", " +
            "nonce=\"" + nonce + "\", " +
            "signature=\"" + hash + "\", " +
            "principalID=\"" + userID + "\", " +
            "principalIDNS=\"urn:oclc:platform:132607\"";
  }
}
