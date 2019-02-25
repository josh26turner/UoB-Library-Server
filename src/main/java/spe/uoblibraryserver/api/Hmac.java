package spe.uoblibraryserver.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

class Hmac {
  
  static String getMessage(String httpMethod, Long time, String nonce, String query) {
    return UoBLibrary.getPublicKey() + "\n"
            + time + "\n"
            + nonce + "\n"
            + "\n"
            + httpMethod + "\n"
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

  static String getAuthHeader(String httpMethod, String query, String userID) {
    Date date = new Date();
    String nonce = UoBLibrary.getNonce();
    long time = date.getTime() / 1000;
    String message = getMessage(httpMethod, time, nonce, query);
    //System.out.println(message);
    String hash = "";
    try {
      hash = getHash(message, UoBLibrary.getPrivateKey());
    } catch (Exception ex){
      // ???
    }
    return "http://www.worldcat.org/wskey/v2/hmac/v1 " +
            "clientId=\"" + UoBLibrary.getPublicKey() + "\", " +
            "timestamp=\"" + time + "\", " +
            "nonce=\"" + nonce + "\", " +
            "signature=\"" + hash + "\", " +
            "principalID=\"" + userID + "\", " +
            "principalIDNS=\"urn:oclc:platform:132607\"";
  }
}
