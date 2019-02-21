package spe.uoblibraryserver.api;

import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class HmacTests {
  @Test
  public void basicHmac(){
    try {
      System.out.println(Hmac.getHash(Hmac.getMessage("GET")));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }
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
      
      assertEquals("5oFM60Bl8a60rVW7zM+nw/hysbur80ci3ycgOKVczNE=", Hmac.getHash(message));
    } catch (NoSuchAlgorithmException | InvalidKeyException | IOException e) {
      e.printStackTrace();
    }
  }
}
