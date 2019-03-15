package spe.uoblibraryserver.api;

import org.apache.commons.logging.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    CheckOutRequest checkOutRequest = new CheckOutRequest();
    return checkOutRequest.checkOutRequest(xml);
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
