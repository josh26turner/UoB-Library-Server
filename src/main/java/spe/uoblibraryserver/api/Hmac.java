package spe.uoblibraryserver.api;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

class Hmac {
  
  String getMessage(String httpMethod) throws IOException{
    Date date = new Date();
    
    return UoBLibrary.getPublicKey() + "\n"
            + date.getTime() + "\n"
            + "981333313127278655903652665637" + "\n"
            + "\n"
            + httpMethod + "\n"
            + "www.oclc.org" + "\n"
            + "443" + "\n"
            + "/wskey" + "\n"
            + "inst=" + UoBLibrary.getRegistryId() + "\n";
  }
  
  String getHash(String message) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
    Mac sha256 = Mac.getInstance("HmacSHA256");
    
    String secret = UoBLibrary.getPrivateKey();
    
    SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    sha256.init(secretKey);
    
    Base64.Encoder encoder = Base64.getEncoder();
  
    return encoder.encodeToString(sha256.doFinal(message.getBytes()));
  }
}
