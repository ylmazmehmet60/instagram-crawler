/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datareporsitories;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


/**
 *
 * @author ASOS
 */
public class followtest {

    String cookie = "ig_did=3A738394-FBD8-4BFB-9326-E5ED4715CA18; mid=XfT2ZAALAAE0BOCqVfsBKfr3hSQx; fbm_124024574287414=base_domain=.instagram.com; shbid=8514; shbts=1577685635.6741197; rur=PRN; fbsr_124024574287414=9epJf6tB71IB9lANHwqUtzVErrrXWrWTGlb_rQ5ThI4.eyJ1c2VyX2lkIjoiNzMyNDEzNjIzIiwiY29kZSI6IkFRQ0Z0NFNxX1JMaDlCaXcxNjVKZVJKZVZ1RXdRT0NzcDFrX0YxVDFTSXliQS0xVUtqY1NBUGdkNmEyNHY5ZnB0R2VEZDRWYXJmYkdXZjFiRHMwcWpiUmVsVkNXV0hpX0xCTUhnU0REWTJSQS1INHlFZHZBZWFmMnhTMVR6S3I3MmZQZ0lDOW5tMFd1dnJJYlhLWDg5WndTT2tiRzJaV2x3Ym1WYk1tazZVNlVJX1JyTzFPVHMzcW0tbHczTTZoYVlvN3M1SF9IUlhxQTVBWmxNOFJpR0FBRF80Vk14bFdoTERINXRLYmxzTm52WHRBbGVlYm1EQllNekNBblkzejc3b05ZOTJONDNWanVMSnFVdHhoQ1VYX0ZlWVdWTmowbXU5NEVwQ1RVSzU1YTlwYTMxVTd1YnF3ekZKNHZyblpqenFBIiwib2F1dGhfdG9rZW4iOiJFQUFCd3pMaXhuallCQUxKWFdqMW52NDVUREdaQ012TzRGVk1nYU90MHphQnh5VWJDcTJDWkNZdkZzWkM1TkNhZ1hRcFlZY0NKSldHWkJUaXBYNFhtcXo0V1c5dVpBRENNUm1LWEQyblpCaEtDTlhwNnhQUm1xTFN5Q0p3Q01DaUczYXNaQWl5Z3VwY3cwbzlaQWxIeEdrenc4OE9aQjUwaXpWU1pCMGhKb1lwNlVmNGxoaVpDT25VUFpDdzciLCJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImlzc3VlZF9hdCI6MTU3NzgyMDQ3N30; csrftoken=HQ47E7SrNKABQ9lWVsEpOSPNF6eR40oH; ds_user_id=3149067609; sessionid=3580039893%3AdR59IN6oAq5Wm4%3A6; urlgen=\"{\\\"88.226.232.135\\\": 9121}:1imNFE:GWpH647yRjEyT3Y8j5thPTxmw9E\"";
    
    public static void main(String[] args) {
        
        //sakın çalıştırma :)
        
        /*followtest ft = new followtest();
        String JSON = ft.requestJS("https://www.instagram.com/web/friendships/10091493667/follow/");
        System.out.println(JSON);*/
    }

    public String requestJS(String url) {

        String donus = "";
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "keep-alive");
            //con.setRequestProperty("Host", "ieeexplore.ieee.org");
            con.setRequestProperty("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            con.setRequestProperty("authority", "www.instagram.com");
             con.setRequestProperty("method", "POST");
            con.setRequestProperty("content-type", "application/json;charset=UTF-8");
            con.setRequestProperty("Cookie", cookie);
            con.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            donus = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donus;
    }
}
