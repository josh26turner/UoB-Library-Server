package spe.uoblibraryserver.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {
  
  @RequestMapping("/greeting")
  public String index(){
    return "UoB Library API Server\n";
  }
  
  @RequestMapping("/number/{number}")
  public String number(@PathVariable int number){
    return number+"\n";
  }
  
  @RequestMapping("/requester")
  public String requester() {
    return Requester.ip();
  }
}
