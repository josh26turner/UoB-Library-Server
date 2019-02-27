package spe.uoblibraryserver.api;

import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import spe.uoblibraryserver.api.xml.Request;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;

@RestController
public class Mapper {

  /**
   *
   * @return - Welcome
   */
  @RequestMapping("/home")
  public String home() {
    return "Welcome";
  }

  /**
   * The function handling POST requests for checking out books
   * @param xml - the body of the POST request
   * @return - The conformation/rejection of the request from WMS
   */
  @PostMapping("/checkout")
  public String checkout(@RequestBody String xml) {
    Request request;
    try {
      request = new Request(xml);
      
//      String message = Hmac.getMessage("POST");
      
//      String auth = Hmac.getHash(message);
      
      //TODO add check for user authorised
  
      request.formatRequest();
  
      String userID = request.getCheckOutRequest().getUserID();
      String response = "<ns1:NCIPMessage xmlns:ns2=\"http://oclc.org/WCL/ncip/2011/extensions\" xmlns:ns1=\"http://www.niso.org/2008/ncip\" ns1:version=\"2.0\">\n" +
              "<ns1:CheckOutItemResponse>\n" +
              "<ns1:ItemId>\n" +
              "<ns1:AgencyId>132607</ns1:AgencyId>\n" +
              "<ns1:ItemIdentifierValue>151468622X</ns1:ItemIdentifierValue>\n" +
              "</ns1:ItemId>\n" +
              "<ns1:UserId>\n" +
              "<ns1:AgencyId>132607</ns1:AgencyId>\n" +
              "<ns1:UserIdentifierValue>" + userID + "</ns1:UserIdentifierValue>\n" +
              "</ns1:UserId>\n" +
              "<ns1:DateDue>2019-02-04T23:59:59Z</ns1:DateDue>\n" +
              "<ns1:RenewalCount>1</ns1:RenewalCount>\n" +
              "<ns1:ItemOptionalFields>\n" +
              "<ns1:BibliographicDescription>\n" +
              "<ns1:Author>Julie Schumacher</ns1:Author>\n" +
              "<ns1:BibliographicRecordId>\n" +
              "<ns1:BibliographicRecordIdentifier>959276078</ns1:BibliographicRecordIdentifier>\n" +
              "<ns1:BibliographicRecordIdentifierCode ns1:Scheme=\"http://www.niso.org/ncip/v2_0/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm\">OCLC</ns1:BibliographicRecordIdentifierCode>\n" +
              "</ns1:BibliographicRecordId>\n" +
              "<ns1:PublicationDate>2017</ns1:PublicationDate>\n" +
              "<ns1:Publisher>Chicago, Ill. : University of Chicago Press,</ns1:Publisher>\n" +
              "<ns1:Title>Doodling for academics : a coloring and activity book /</ns1:Title>\n" +
              "<ns1:Language ns1:Scheme=\"http://lcweb.loc.gov/standards/iso639-2/bibcodes.html\">eng</ns1:Language>\n" +
              "<ns1:MediumType>Book</ns1:MediumType>\n" +
              "</ns1:BibliographicDescription>\n" +
              "</ns1:ItemOptionalFields>\n" +
              "</ns1:CheckOutItemResponse>\n" +
              "</ns1:NCIPMessage>";
      
      return response;
    } catch (IOException | SAXException | ParserConfigurationException e) {
      ErrorResponse.erroneousXML(xml);
      return "Error XML\n";
    }
//    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//      e.printStackTrace();
//    }
//    return "Problem?";
  }
  
  /**
   * The function handling authorisation requests from the app
   * @param userID - the ID of the user being checked
   * @return - Something saying whether the user is authenticated or not
   */
  @GetMapping("/auth/{userID}")
  public String auth(@PathVariable String userID, @RequestHeader("Authorization") String accessToken) {
    AuthorizationRequest authorizationRequest = new AuthorizationRequest();
    return authorizationRequest.makeRequest(userID, accessToken);
  }
}
