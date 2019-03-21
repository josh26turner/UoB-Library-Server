package spe.uoblibraryserver.api;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

class UserAuth {
  Boolean makeRequest(String userID, String accessToken) {
    try {
      URL meResourceURL = new URL("https://bub.share.worldcat.org/ncip/circ-patron");

      HttpsURLConnection meResourceURLConnection = (HttpsURLConnection) meResourceURL.openConnection();

      meResourceURLConnection.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");
      meResourceURLConnection.setRequestProperty("Host", "bub.share.worldcat.org");
      meResourceURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken + ", principalID=\"" + userID + "\", principalIDNS=\"urn:oclc:platform:132607\"");

      meResourceURLConnection.setRequestMethod("POST");

      meResourceURLConnection.setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(meResourceURLConnection.getOutputStream());
      wr.writeBytes(getBody(userID));
      wr.flush();wr.close();

      if (meResourceURLConnection.getResponseCode() == 200) return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  private String getBody(String userID) {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<NCIPMessage \n" +
            "    xmlns=\"http://www.niso.org/2008/ncip\" \n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
            "    xmlns:ncip=\"http://www.niso.org/2008/ncip\" \n" +
            "    xmlns:ns2=\"http://oclc.org/WCL/ncip/2011/extensions\" \n" +
            "    xsi:schemaLocation=\"http://www.niso.org/2008/ncip http://www.niso.org/schemas/ncip/v2_01/ncip_v2_01.xsd\" \n" +
            "    ncip:version=\"http://www.niso.org/schemas/ncip/v2_01/ncip_v2_01.xsd\">\n" +
            "    <LookupUser>\n" +
            "        <InitiationHeader>\n" +
            "            <FromAgencyId>\n" +
            "                <AgencyId ncip:Scheme=\"http://oclc.org/ncip/schemes/agencyid.scm\">" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
            "            </FromAgencyId>\n" +
            "            <ToAgencyId>\n" +
            "                <AgencyId ncip:Scheme=\"http://oclc.org/ncip/schemes/agencyid.scm\">" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
            "            </ToAgencyId>\n" +
            "            <ApplicationProfileType ncip:Scheme=\"http://oclc.org/ncip/schemes/application-profile/wcl.scm\">Version 2011</ApplicationProfileType>\n" +
            "        </InitiationHeader>\n" +
            "        <UserId>\n" +
            "            <UserIdentifierValue>" + userID + "</UserIdentifierValue>\n" +
            "        </UserId>\n" +
            "        <Ext>\n" +
            "            <ns2:ResponseElementControl>\n" +
            "                <ns2:ElementType ncip:Scheme=\"http://worldcat.org/ncip/schemes/v2/extensions/elementtype.scm\">Account Details</ns2:ElementType>\n" +
            "                <ns2:StartElement> 1</ns2:StartElement>\n" +
            "                <ns2:MaximumCount>10</ns2:MaximumCount>\n" +
            "                <ns2:SortField ncip:Scheme=\"http://worldcat.org/ncip/schemes/v2/extensions/accountdetailselementtype.scm\">Accrual Date</ns2:SortField>\n" +
            "                <ns2:SortOrderType ncip:Scheme=\"http://worldcat.org/ncip/schemes/v2/extensions/sortordertype.scm\">Ascending</ns2:SortOrderType>\n" +
            "            </ns2:ResponseElementControl>\n" +
            "        </Ext>\n" +
            "    </LookupUser>\n" +
            "</NCIPMessage>";
  }
}
