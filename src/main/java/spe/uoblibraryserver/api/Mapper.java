package spe.uoblibraryserver.api;

import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import spe.uoblibraryserver.api.xml.Request;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
public class Mapper {
  @RequestMapping("/home")
  public String home() {
    return "Welcome";
  }
  
  @PostMapping("/checkout")
  public String checkout(@RequestBody String xml) {
    Request request = null;
    try {
      request = new Request(xml);
    } catch (IOException | SAXException | ParserConfigurationException e) {
      ErrorResponse.erroneousXML(xml);
      return "Error in XML\n";
    }
  
    //TODO add check for user authorised

    return request.formatRequest();
  }
  
  @PostMapping("/auth/{userID}")
  public String auth(@PathVariable String userID) {
    return userID;
  }
}
