package spe.uoblibraryserver.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spe.uoblibraryserver.api.xml.Request;

@RestController
public class Mapper {
  @RequestMapping("/home")
  public String home() {
    return "Welcome";
  }
  
  @PostMapping("/checkout")
  public String checkout(@RequestBody String xml) {
    Request request = new Request(xml);

    //TODO add check for user authorised

    return request.formatRequest();
  }
}
