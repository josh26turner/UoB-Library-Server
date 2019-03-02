package spe.uoblibraryserver.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

class Hmac {
  
  private static String getMessage(Long time, String nonce, String query, String publicKey) {
    return publicKey + "\n"
            + time + "\n"
            + nonce + "\n"
            + "\n"
            + "POST" + "\n"
            + "www.oclc.org" + "\n"
            + "443" + "\n"
            + "/wskey" + "\n"
            + query + "\n";
  }

  static String getHash(String message, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256 = Mac.getInstance("HmacSHA256");
    
    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    sha256.init(secretKey);
    
    Base64.Encoder encoder = Base64.getEncoder();
  
    return encoder.encodeToString(sha256.doFinal(message.getBytes()));
  }

  static String getOAuthHeader(String query, String userID) {
    Date date = new Date();
    String nonce = UoBLibrary.getNonce();
    long time = date.getTime() / 1000;
    String message = getMessage(time, nonce, query, Keys.getOAuthPublicKey());
    //System.out.println(message);
    String hash = "";
    try {
      hash = getHash(message, Keys.getOAuthSecretKey());
    } catch (Exception ex){
      // ???
    }
    return "http://www.worldcat.org/wskey/v2/hmac/v1 " +
            "clientId=\"" + Keys.getOAuthPublicKey() + "\", " +
            "timestamp=\"" + time + "\", " +
            "nonce=\"" + nonce + "\", " +
            "signature=\"" + hash + "\", " +
            "principalID=\"" + userID + "\", " +
            "principalIDNS=\"urn:oclc:platform:132607\"";
  }
  
  static String getCheckoutHeader(String userID) {
    Date date = new Date();
    String nonce = UoBLibrary.getNonce();
    long time = date.getTime() / 1000;
    String message = getMessage(time, nonce, "", Keys.getCheckoutPubicKey());
    
    String hash = "";
    try {
      hash = getHash(message, Keys.getCheckoutSecretKey());
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
    return "http://www.worldcat.org/wskey/v2/hmac/v1 " +
            "clientId=\"" + Keys.getCheckoutPubicKey() + "\", " +
            "timestamp=\"" + time + "\", " +
            "nonce=\"" + nonce + "\", " +
            "signature=\"" + hash + "\", " +
            "principalID=\"" + userID + "\", " +
            "principalIDNS=\"urn:oclc:platform:132607\"";
  }
}
