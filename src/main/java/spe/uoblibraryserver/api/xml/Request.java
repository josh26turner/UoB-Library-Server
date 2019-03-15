package spe.uoblibraryserver.api.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import spe.uoblibraryserver.api.CheckOutRequest;
import spe.uoblibraryserver.api.ErrorResponse;
import spe.uoblibraryserver.api.UoBLibrary;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Request {

  /**
   *
   * @param xml - The body of the HTTP POST for checking out a book
   * @throws IOException - An error in the XML
   * @throws SAXException - An error in the XML
   * @throws ParserConfigurationException - An error in the XML
   */
  public Request(String xml, CheckOutRequest checkOutRequest) throws IOException, SAXException, ParserConfigurationException {
    Document document = XMLParser.parse(xml);

    Node doc = document.getChildNodes().item(0);

    NodeList nodeList = doc.getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i ++) {
      Node item = nodeList.item(i);

      switch (item.getNodeName()) {
        case "userId":
          checkOutRequest.setUserID(item.getChildNodes().item(0).getNodeValue());
          break;

        case "accessToken":
          checkOutRequest.setAccessToken(item.getChildNodes().item(0).getNodeValue());
          break;

        case "itemId":
          checkOutRequest.setItemID(item.getChildNodes().item(0).getNodeValue());
          break;

        case "location":
          checkOutRequest.setLocation(item.getChildNodes().item(0).getNodeValue());

        default:
          ErrorResponse.erroneousXML(xml);
      }
    }
  }

  /**
   *
   * @return - The XML to form the body of the post request to WMS system to checkout a book
   */
  public String formatRequest(CheckOutRequest checkOutRequest){
    String userId = checkOutRequest.getUserID();
    String itemIdValue = checkOutRequest.getItemID();
    String location = checkOutRequest.getLocation();

    return "<?xml version=\"1.0\"?>\n" +
            "<NCIPMessage \n" +
            "    xmlns=\"http://www.niso.org/2008/ncip\" \n" +
            "    xmlns:ncip=\"http://www.niso.org/2008/ncip\" \n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
            "    ncip:version=\"http://www.niso.org/schemas/ncip/v2_01/ncip_v2_01.xsd\" \n" +
            "    xsi:schemaLocation=\"http://www.niso.org/2008/ncip http://www.niso.org/schemas/ncip/v2_01/ncip_v2_01.xsd\">\n" +
            "    <CheckOutItem>\n" +
            "        <InitiationHeader>\n" +
            "            <FromAgencyId>\n" +
            "                <AgencyId ncip:Scheme=\"http://oclc.org/ncip/schemes/agencyid.scm\">" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
            "            </FromAgencyId>\n" +
            "            <ToAgencyId>\n" +
            "                <AgencyId>" + location + "</AgencyId>\n" +
            "            </ToAgencyId>\n" +
            "            <ApplicationProfileType ncip:Scheme=\"http://oclc.org/ncip/schemes/application-profile/platform.scm\">Version 2011</ApplicationProfileType>\n" +
            "        </InitiationHeader>\n" +
            "        <UserId>\n" +
            "            <AgencyId>" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
            "            <UserIdentifierValue>" + userId + "</UserIdentifierValue>\n" +
            "        </UserId>\n" +
            "        <ItemId>\n" +
            "            <AgencyId>" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
            "            <ItemIdentifierValue>" + itemIdValue + "</ItemIdentifierValue>\n" +
            "        </ItemId>\n" +
            "        <ItemElementType>" + "description" + "</ItemElementType>\n" +
            "    </CheckOutItem>\n" +
            "</NCIPMessage>\n";
  }
}

