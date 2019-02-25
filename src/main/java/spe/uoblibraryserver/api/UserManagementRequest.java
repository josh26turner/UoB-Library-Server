package spe.uoblibraryserver.api;

class UserManagementRequest {
  String formRequestHeader(String userID) {
    String query = "authenticatingInstitutionId=132607\ncontextInstitutionId=132607\ngrant_type=client_credentials\nscope=SCIM%3Aread_self";
    String s = Hmac.getAuthHeader("POST", query, userID);
    //System.out.println(s);
    return s;
  }
}
