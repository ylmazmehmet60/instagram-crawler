/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datareporsitories;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author ASOS
 */
public class test {

    String cookie = "ig_did=3A738394-FBD8-4BFB-9326-E5ED4715CA18; mid=XfT2ZAALAAE0BOCqVfsBKfr3hSQx; fbm_124024574287414=base_domain=.instagram.com; csrftoken=Tw7g3GcnSzuV1KdHgOrvIYacKVoUgivc; shbid=2978; shbts=1582029980.3398502; ds_user_id=28467357140; sessionid=28467357140%3AZkUZTDkzfIYdix%3A4; fbsr_124024574287414=rADi6n_xly6ZQJ9II5QmUhbhbdMnh_7sJIJz6yTqh18.eyJ1c2VyX2lkIjoiNzMyNDEzNjIzIiwiY29kZSI6IkFRRDBodlByMjBqazdvSEN6bHEzS2VHMmctX1RGS19sMC1PNzN6dnI0Rk9WeWtqQXdXTW4xbVd6U2J1am1MbUhkeHZMUHZWVUVxek5zVkRXbDY1ekcxQ19GQi1nZkc2YlU2dG1iSXcwdjVOZGNtZll2UlVjLXp2S0pnRGpmZGVNdG1xcW9lT3BSbm1idzBDU20zVXpmLVROaUJLdzJiTGdPZkRtOGxFSzBRUXNSU3FZOXMyUG0wV190MnhOcXBfOWRyME42b2IzT3NZem4wUHZWbkgxSWdOeTRBZlJrT2JoTGdFQ3FOVTBacTZoMlo0RVc1OGZXS2FOUHgzQjF5am5EUVk3QzhKM090NU9LV2pWVUNvRDU3SzVqRFNENExkXzFHS0pIbVJsMVJIWUpwY0VWcWwtdlRzR3lFV2ZzcENQWURRIiwib2F1dGhfdG9rZW4iOiJFQUFCd3pMaXhuallCQUtvN2V2dUJCN1E3b0JXRU9nRTg0OTF5WkFSRlZnd2kwdzVuR0gwcG52dDk5VGZydzd2S1JwVXU5cGI3QU1uVG5OeE1idm43VEhuNk5ybmZlcFZ1eWFDZmlraFBuWkFwa2FiTFF3SGdOdVlCWDBaQmx0bmpvQmNKb2VPVktaQW96QW9qeVZFWkJaQmNuRmhjSk1INHJ0MmVnSnhkRE9VeHhRZk1UMDlEWkJmIiwiYWxnb3JpdGhtIjoiSE1BQy1TSEEyNTYiLCJpc3N1ZWRfYXQiOjE1ODIwMjk5ODB9; rur=ATN; urlgen=\"{\\\"88.247.154.62\\\": 9121}:1j42Gb:l5VynenX2lzI-hBdAkJ59U_A1QI\"";
    
    public static void main(String[] args) throws InterruptedException {

        String username = "kongresempozyum";

        test t = new test();
        userModel um = t.userProfile(username);
        String userId = um.getUserId();
        String followedCount = um.getFollowedCount();
        System.out.println("userId="+userId);
        System.out.println("followedCount="+followedCount);

        
        //takipçileri cıkar
        if (!followedCount.equals("0")) {
            ArrayList<userModel> followersArr = new ArrayList<>();

            String has_next_page = "false";
            String end_cursor = "";
            followersArr = t.followersList(userId, followersArr, has_next_page,end_cursor);

            for (int i = 0; i < followersArr.size(); i++) {
                System.out.println(followersArr.get(i).getFullName());
                System.out.println(followersArr.get(i).getUserName());
                System.out.println(followersArr.get(i).getProfilPicture());
                System.out.println("---------------------");
            }
        }else{
            System.out.println("Hiç takipçiniz yok");
        }

        //fotografları cıkar
        /*ArrayList<mediaModel> mediaArr = new ArrayList<>();
        mediaArr = t.userMedia(username, mediaArr);
        for (int i = 0; i < mediaArr.size(); i++) {
            System.out.println("getUserId=" + mediaArr.get(i).getUserId());
            System.out.println("getDisplayUrl=" + mediaArr.get(i).getDisplayUrl());
            System.out.println("getEdgeLikedByCount=" + mediaArr.get(i).getEdgeLikedByCount());
            System.out.println("getEdgeMediaToCaptionText=" + mediaArr.get(i).getEdgeMediaToCaptionText());
            System.out.println("getEdgeMediaToCommentCount=" + mediaArr.get(i).getEdgeMediaToCommentCount());
            System.out.println("-----------------------------");
        }*/

    }

    public ArrayList<mediaModel> userMedia(String username, ArrayList<mediaModel> mediaArr) {
        String url = "https://www.instagram.com/" + username + "/";

        // String url2 = "https://www.instagram.com/graphql/query/?query_hash=e769aa130647d2354c40ea6a439bfc08&variables=%7B%22id%22%3A%226596731848%22%2C%22first%22%3A12%2C%22after%22%3A%22"++"%3D%3D%22%7D";
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").cookie("Cookie", cookie).get();
            String html = doc.html();
            String _sharedData = html.substring(html.indexOf("window._sharedData = {") + 21, html.indexOf("};</script>") + 1);
            JsonObject jsonObject = new JsonParser().parse(_sharedData).getAsJsonObject();
            JsonObject entry_data = (JsonObject) jsonObject.get("entry_data");
            JsonArray ProfilePage = new JsonParser().parse(entry_data.getAsJsonArray("ProfilePage").toString()).getAsJsonArray();
            for (int i = 0; i < ProfilePage.size(); i++) {
                JsonObject inside = (JsonObject) ProfilePage.get(i);
                JsonObject graphql = (JsonObject) inside.get("graphql");
                JsonObject user = (JsonObject) graphql.get("user");
                String userId = user.get("id").toString().replace("\"", "");

                mediaArr = parserUserMediaData(user, userId, mediaArr);
                //recurisive yap
                //instagramdan end cursor yapılmış bi link al 
                //profili açınca gelen jsonu json parsee yapıştır 
                // prfildeki graphql bizde data olmalı 
            }
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mediaArr;
    }

    public ArrayList<mediaModel> parserUserMediaData(JsonObject JSON, String userId, ArrayList<mediaModel> mediaArr) {

        JsonObject edge_owner_to_timeline_media = (JsonObject) JSON.get("edge_owner_to_timeline_media");

        String followedCount = edge_owner_to_timeline_media.get("count").toString().replace("\"", "");
        if (!followedCount.equals("0")) { //eger foto yoksa 0 mı gelecek bilmiyorum
            JsonObject page_info = (JsonObject) edge_owner_to_timeline_media.get("page_info");

            String has_next_page = page_info.get("has_next_page").toString().replace("\"", "");
            String end_cursor = page_info.get("end_cursor").toString().replace("\"", "");
            end_cursor = end_cursor.substring(0, end_cursor.length() - 2);

            //mediaArr = parserUserMediaData(edge_owner_to_timeline_media,userId, mediaArr);
        }

        JsonArray edges = new JsonParser().parse(edge_owner_to_timeline_media.getAsJsonArray("edges").toString()).getAsJsonArray();
        for (int i = 0; i < edges.size(); i++) {
            JsonObject inside = (JsonObject) edges.get(i);
            JsonObject node = (JsonObject) inside.get("node");
            String displayUrl = node.get("display_url").toString().replace("\"", "");

            String edgeLikedByCount = null;
            try {
                JsonObject edgeLikedBy = (JsonObject) node.get("edge_liked_by");
                edgeLikedByCount = edgeLikedBy.get("count").toString().replace("\"", "");
            } catch (Exception e) {
                edgeLikedByCount = "0";
            }

            String edgeMediaToCommentCount = null;
            try {
                JsonObject edgeMediaToComment = (JsonObject) node.get("edge_media_to_comment");
                edgeMediaToCommentCount = edgeMediaToComment.get("count").toString().replace("\"", "");
            } catch (Exception e) {
                edgeMediaToCommentCount = "0";
            }

            String edgeMediaToCaptionText = null;
            try {
                JsonObject edgeMediaToComment = (JsonObject) node.get("edge_media_to_caption");
                JsonArray insideArr = new JsonParser().parse(edgeMediaToComment.getAsJsonArray("edges").toString()).getAsJsonArray();
                for (int j = 0; j < insideArr.size(); j++) {
                    JsonObject insideObj = (JsonObject) insideArr.get(i);
                    JsonObject nodeObj = (JsonObject) insideObj.get("node");
                    edgeMediaToCaptionText = nodeObj.get("text").toString().replace("\"", "");
                }
            } catch (Exception e) {
                edgeMediaToCaptionText = "";
            }

            mediaModel mm = new mediaModel();
            mm.setUserId(userId);
            mm.setDisplayUrl(displayUrl);
            mm.setEdgeLikedByCount(edgeLikedByCount);
            mm.setEdgeMediaToCommentCount(edgeMediaToCommentCount);
            mm.setEdgeMediaToCaptionText(edgeMediaToCaptionText);
            mediaArr.add(mm);

            downloadIMG(displayUrl);
            // output here
        }

        return mediaArr;
    }

    public void downloadIMG(String displayUrl) {
        String generateName = displayUrl.substring(displayUrl.lastIndexOf("/") + 1, displayUrl.indexOf(".jpg"));
        Response resultImageResponse;
        try {
            resultImageResponse = Jsoup.connect(displayUrl).cookie("Cookie", cookie).ignoreContentType(true).execute();
            FileOutputStream out = (new FileOutputStream(new java.io.File(generateName + ".jpg")));
            out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
            out.close();
        } catch (IOException ex) {
            System.out.println("Resmi Kaydededi" + generateName);
        }
    }

    public ArrayList<userModel> followersList(String userId, ArrayList<userModel> followersArr, String has_next_page, String end_cursor) throws InterruptedException {

        String mainUrl = null;
        if (has_next_page.equals("false")) {
            mainUrl = "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=%7B%22id%22%3A%22" + userId + "%22%2C%22include_reel%22%3Atrue%2C%22fetch_mutual%22%3Atrue%2C%22first%22%3A24%7D";
        } else if (has_next_page.equals("true")) {
            mainUrl = "https://www.instagram.com/graphql/query/?query_hash=c76146de99bb02f6415203be841dd25a&variables=%7B%22id%22%3A%22" + userId + "%22%2C%22include_reel%22%3Atrue%2C%22fetch_mutual%22%3Afalse%2C%22first%22%3A12%2C%22after%22%3A%22" + end_cursor + "%3D%3D%22%7D";
        }
        


        String JSON = requestJS(mainUrl);
        JsonObject jsonObject = new JsonParser().parse(JSON).getAsJsonObject();
        JsonObject data = (JsonObject) jsonObject.get("data");
        JsonObject user = (JsonObject) data.get("user");
        JsonObject edge_followed_by = (JsonObject) user.get("edge_followed_by");
        JsonObject page_info = (JsonObject) edge_followed_by.get("page_info");
        has_next_page = page_info.get("has_next_page").toString().replace("\"", "");
        end_cursor = page_info.get("end_cursor").toString().replace("\"", "");
        end_cursor = end_cursor.substring(0, end_cursor.length() - 2);
        followersArr = parseFollowData(edge_followed_by, followersArr);
        if (has_next_page.equals("true")) {
            Thread.sleep(1500); //COK FAZLA ISTEK OLURSA BANLIYO INSTAGRAM , AMA SAYIYI BIRAZ DUSUREREK DENEYEBİLİRSİN
            System.out.println("Devam Ediyor...");
            followersList(userId, followersArr, has_next_page, end_cursor);
        }

        return followersArr;
        //System.out.println(JSON);   
    }

    public ArrayList<userModel> parseFollowData(JsonObject JSON, ArrayList<userModel> followersList) {
        JsonArray edges = JSON.getAsJsonArray("edges");

        for (int i = 0; i < edges.size(); i++) {
            JsonObject element = (JsonObject) edges.get(i);
            JsonObject node = (JsonObject) element.get("node");
            String id = node.get("id").toString().replace("\"", "");
            String username = node.get("username").toString().replace("\"", "");
            String fullName = node.get("full_name").toString().replace("\"", "");
            String profilePicUrl = node.get("profile_pic_url").toString().replace("\"", "");

            userModel um = new userModel();
            um.setFullName(fullName);
            um.setUserName(username);
            um.setProfilPicture(profilePicUrl);
            um.setUserId(id);

            followersList.add(um);

        }

        return followersList;
    }

    public userModel userProfile(String username) {
        String url = "https://www.instagram.com/" + username + "/";
        String userId = "";
        String followedCount = "";
        String followCount = "";
        String fullName = "";
        String profilPicture = "";
        String profilPictureHD = "";
        String userName = "";
        String isPrivate = "";
        String mediaCount = "";

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").cookie("Cookie", cookie).get();
            String html = doc.html();
            String _sharedData = html.substring(html.indexOf("window._sharedData = {") + 21, html.indexOf("};</script>") + 1);
            JsonObject jsonObject = new JsonParser().parse(_sharedData).getAsJsonObject();
            JsonObject entry_data = (JsonObject) jsonObject.get("entry_data");
            JsonArray ProfilePage = new JsonParser().parse(entry_data.getAsJsonArray("ProfilePage").toString()).getAsJsonArray();
            for (int i = 0; i < ProfilePage.size(); i++) {
                JsonObject inside = (JsonObject) ProfilePage.get(i);
                JsonObject graphql = (JsonObject) inside.get("graphql");
                JsonObject user = (JsonObject) graphql.get("user");
                userId = user.get("id").toString().replace("\"", "");

                try {
                    JsonObject edge_followed_by = (JsonObject) user.get("edge_followed_by");
                    followedCount = edge_followed_by.get("count").toString().replace("\"", "");
                } catch (Exception e) {
                    followedCount = "0";
                }

                try {
                    JsonObject edge_follow = (JsonObject) user.get("edge_follow");
                    followCount = edge_follow.get("count").toString().replace("\"", "");
                } catch (Exception e) {
                    followCount = "0";
                }

                try {
                    fullName = user.get("full_name").toString().replace("\"", "");
                } catch (Exception e) {
                    fullName = "0";
                }
                try {
                    profilPicture = user.get("profile_pic_url").toString().replace("\"", "");
                } catch (Exception e) {
                    profilPicture = "0";
                }

                /* try {
                    profilPictureHD = user.get("profile_pic_url_hd").toString().replace("\"", "");
                } catch (Exception e) {
                    profilPictureHD="0";
                }*/
                try {
                    userName = user.get("username").toString().replace("\"", "");
                } catch (Exception e) {
                    userName = "0";
                }

                try {
                    isPrivate = user.get("is_private").toString().replace("\"", "");
                } catch (Exception e) {
                    isPrivate = "0";
                }

                try {
                    JsonObject edge_owner_to_timeline_media = (JsonObject) user.get("edge_owner_to_timeline_media");
                    mediaCount = edge_owner_to_timeline_media.get("count").toString().replace("\"", "");
                } catch (Exception e) {
                    mediaCount = "0";
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

        userModel um = new userModel();
        um.setUserName(userName);
        um.setFollowCount(followCount);
        um.setFollowedCount(followedCount);
        um.setIsPrivate(isPrivate);
        um.setFullName(fullName);
        um.setMediaCount(mediaCount);
        um.setUserId(userId);
        um.setProfilPicture(profilPicture);

        return um;
    }

    public String requestJS(String url) {

        String donus = "";
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("Connection", "keep-alive");
            //con.setRequestProperty("Host", "ieeexplore.ieee.org");
            con.setRequestProperty("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
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
