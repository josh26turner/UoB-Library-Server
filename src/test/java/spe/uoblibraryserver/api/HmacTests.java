package spe.uoblibraryserver.api;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class HmacTests {
  @Test
  public void basicHmac(){
    try {
      String message = "jdfRzYZbLc8HZXFByyyLGrUqTOOmkJOAPi4tAN0E7xI3hgE2xDgwJ7YPtkwM6W3ol5yz0d0JHgE1G2Wa" + "\n"
              + "1361408273" + "\n"
              + "981333313127278655903652665637" + "\n"
              + "\n"
              + "GET" + "\n"
              + "www.oclc.org" + "\n"
              + "443" + "\n"
              + "/wskey" + "\n"
              + "inst=128807" + "\n";
      
      assertEquals("5O6SRig58wqm6gqEu3oSODVte6Albon9CCvNrZHCoys=", Hmac.getHash(message, "UYnwZbmvf3fAXCEa0JryLQ=="));
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }
  }


  /**
   * Not a test....
   */
  @Test
  public void hmacGen(){
      System.out.println(Hmac.getHeader("POST", "authenticatingInstitutionId=132607\ncontextInstitutionId=132607\ngrant_type=client_credentials\nscope=SCIM%3Aread_self"));
  }





}
