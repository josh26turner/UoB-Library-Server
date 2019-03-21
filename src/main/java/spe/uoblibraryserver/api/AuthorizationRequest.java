package spe.uoblibraryserver.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

class AuthorizationRequest {
  String makeRequest(String userID, String accessToken) {

    UserAuth userAuth = new UserAuth();
    if (!userAuth.makeRequest(userID, accessToken)) return "406 - Not authorized";

    UserManagementRequest userManagementRequest = new UserManagementRequest();

    String authResponse = oAuthRequest(userID, userManagementRequest);

    String token = getOAuthAccessToken(authResponse);

    return  idManagementRequest(token);


  }

  /**
   * Makes the request for an auth token from WMS
   * @param userID - the user who is going to be read
   * @param userManagementRequest - the request object that is being used
   * @return - the response from WMS
   */
  private String oAuthRequest(String userID, UserManagementRequest userManagementRequest) {
    String authHeader = userManagementRequest.formOAuthRequestHeader(userID);

    try {
      URL accessTokenURL = new URL("https://authn.sd00.worldcat.org/oauth2/accessToken?grant_type=client_credentials&authenticatingInstitutionId=132607&contextInstitutionId=132607&scope=SCIM:read_self");

      HttpsURLConnection accessTokenURLConnection = (HttpsURLConnection) accessTokenURL.openConnection();

      accessTokenURLConnection.setRequestMethod("POST");

      accessTokenURLConnection.setRequestProperty("Authorization", authHeader);

//      //Exponential backoff
//      Random rand = new Random();
//      for (int i = 0; accessTokenURLConnection.getResponseCode() != 200; i++) {
//        int vary = rand.nextInt(3);
//        wait((int) Math.pow(2, i) + vary);
//      }

      return readResponse(accessTokenURLConnection);

      //URL idManagementURL = new URL("https://" + UoBLibrary.getRegistryId() + ".share.worldcat.org/idaas/scim/v2/Me");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Error";
  }

  private String idManagementRequest(String accessToken) {
    try {
      URL idManagementURL = new URL("https://" + UoBLibrary.getRegistryId() + ".share.worldcat.org/idaas/scim/v2/Me?principalIDNS=urn%3oclc%3platform%3132607");

      HttpsURLConnection idManagementURLConnection = (HttpsURLConnection) idManagementURL.openConnection();

      idManagementURLConnection.setRequestProperty("Host", "132607.share.worldcat.org");

      idManagementURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken);

      idManagementURLConnection.setRequestMethod("GET");

      return readResponse(idManagementURLConnection);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Error";
  }

  private String readResponse(HttpsURLConnection idManagementURLConnection) throws IOException {
    return HttpsRead.readResponse(idManagementURLConnection);
  }

  private String getOAuthAccessToken(String authResponse) {
    String [] split1 = authResponse.split(":");
    String [] split2 = split1[1].split(",");
    return split2[0].replace("\"", "");
  }
}
