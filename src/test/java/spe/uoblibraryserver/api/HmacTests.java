package spe.uoblibraryserver.api;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class HmacTests {
  @Test
  public void basicHmac(){
    Date date = new Date();
    System.out.println(date.getTime());
    Hmac hmac = new Hmac();
    
    try {
      String message = UoBLibrary.getPublicKey() + "\n"
              + "1361408273" + "\n"
              + "981333313127278655903652665637" + "\n"
              + "\n"
              + "GET" + "\n"
              + "www.oclc.org" + "\n"
              + "443" + "\n"
              + "/wskey" + "\n"
              + "inst=128807" + "\n";
      
      assertEquals("5oFM60Bl8a60rVW7zM+nw/hysbur80ci3ycgOKVczNE=", hmac.getHash(message));
    } catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
      e.printStackTrace();
    }
  }
}
