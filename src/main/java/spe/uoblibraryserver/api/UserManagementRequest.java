package spe.uoblibraryserver.api;

class UserManagementRequest {

  /**
   * Forms a request for getting the user info
   * @param userID - the user
   * @return - the authorization header
   */
  String formOAuthRequestHeader(String userID) {
    String query = "authenticatingInstitutionId=132607\ncontextInstitutionId=132607\ngrant_type=client_credentials\nscope=SCIM%3Aread_self\n";
    String s = Hmac.getOAuthHeader(query, userID);
    //System.out.println(s);
    return s;
  }
}
