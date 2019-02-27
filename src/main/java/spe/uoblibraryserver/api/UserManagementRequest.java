package spe.uoblibraryserver.api;

class UserManagementRequest {
  String formOAuthRequestHeader(String userID) {
    String query = "authenticatingInstitutionId=132607\ncontextInstitutionId=132607\ngrant_type=client_credentials\nscope=SCIM%3Aread_self";
    String s = Hmac.getOAuthHeader("POST", query, userID);
    //System.out.println(s);
    return s;
  }
}
