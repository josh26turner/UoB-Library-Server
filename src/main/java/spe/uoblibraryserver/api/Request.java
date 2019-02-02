package spe.uoblibraryserver.api;

class Request {
    static String formatRequest(String itemIdValue, String userId){
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
                "                <AgencyId>" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
                "            </ToAgencyId>\n" +
                "            <ApplicationProfileType ncip:Scheme=\"http://oclc.org/ncip/schemes/application-profile/platform.scm\">Version 2011</ApplicationProfileType>\n" +
                "        </InitiationHeader>\n" +
                "        <UserId>\n" +
                "            <AgencyId>128807</AgencyId>\n" +
                "            <UserIdentifierValue>" + userId + "</UserIdentifierValue>\n" +
                "        </UserId>\n" +
                "        <ItemId>\n" +
                "            <AgencyId>" + UoBLibrary.getRegistryId() + "</AgencyId>\n" +
                "            <ItemIdentifierValue>" + itemIdValue + "</ItemIdentifierValue>\n" +
                "        </ItemId>\n" +
                "    </CheckOutItem>\n" +
                "</NCIPMessage>\n";
    }
}
