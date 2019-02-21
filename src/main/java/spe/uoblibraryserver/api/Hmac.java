package spe.uoblibraryserver.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
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

//    message = "jdfRzYZbLc8HZXFByyyLGrUqTOOmkJOAPi4tAN0E7xI3hgE2xDgwJ7YPtkwM6W3ol5yz0d0JHgE1G2Wa" + "\n"
//            + "1361408273" + "\n"
//            + "981333313127278655903652665637" + "\n"
//            + "\n"
//            + "GET" + "\n"
//            + "www.oclc.org" + "\n"
//            + "443" + "\n"
//            + "/wskey" + "\n"
//            + "inst=128807" + "\n";
  }

  static String getHash(String message, String secret) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
    Mac sha256 = Mac.getInstance("HmacSHA256");
    
    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    sha256.init(secretKey);
    
    Base64.Encoder encoder = Base64.getEncoder();
  
    return encoder.encodeToString(sha256.doFinal(message.getBytes()));
  }

  static String getHeader(String httpMethod, String query) {
    Date date = new Date();
    String nonce = UoBLibrary.getNonce();
    long time = date.getTime() / 1000;
    String message = getMessage(httpMethod, time, nonce, query);
    System.out.println(message);
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
            "principalID=\"fe6e89f3-ff59-4158-8980-44e38bfe6d0e\", " +
            "principalIDNS=\"urn:oclc:platform:132607\"";
  }
}
