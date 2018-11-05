package uniparthenope.srmobile;


public class SRconstantAPI{
    public static final String API_KEY = "sr_api_key";
    public static final String HOME = "https://salesreporter.ddns.net";
    public static final String API = "https://salesreporter.ddns.net/api";
    public static final String LOGIN = "https://salesreporter.ddns.net/api/login";
    public static final String REGISTER = "https://salesreporter.ddns.net/api/register";
    public static final String MIDDLE_EBAY = "https://salesreporter.ddns.net/middle/ebay";
    public static final String ADD_STORE ="https://salesreporter.ddns.net/api/user/1/ecommerce/1/add_stores";
    public static final String GET_REPORT = "https://salesreporter.ddns.net/api/get_report/%s?start_date=%s&end_date=%s";

    public static String ConstructDataApiFormat(int d, int m, int y){

        String date = String.valueOf(y);
        if(m < 10)
            date = date + '0' + String.valueOf(m);
        else
            date = date +  String.valueOf(m);

        if(d < 10)
            date = date + '0' + String.valueOf(d);
        else
            date = date +  String.valueOf(d);

        return date;
    }

}
