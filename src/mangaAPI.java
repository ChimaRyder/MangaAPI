import netscape.javascript.JSObject;

import javax.management.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.*;

public class mangaAPI {
    URL url;
    HttpURLConnection con;

    public mangaAPI() throws MalformedURLException {
        url = new URL("https://api.mangadex.org/manga");

    }

    public String randomManga() throws IOException{
        try {
            System.out.println("connecting to MangaDex API...");
            URL localurl = new URL(url.toString() + "/random");
            con = (HttpURLConnection) localurl.openConnection();
            System.out.println("working : " + con.getResponseCode());
        }catch (Exception e) {
            System.err.println("Smthn went wrong :P : " + con.getConnectTimeout());
        }

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer res = new StringBuffer();
            while((line = bf.readLine()) != null) {
                res.append(line);
            }
            bf.close();
            con.disconnect();

            JSONObject mangaInfo = new JSONObject(new JSONObject(new JSONObject(res.toString()).getJSONObject("data").toString()).getJSONObject("attributes").toString());
            JSONObject title = new JSONObject(mangaInfo.getJSONObject("title").toString());
            return title.getString("en");
        } catch (IOException ie) {
            System.err.println("lmao what");
        }

        return "error reading file :P";
    }

    public void searchManga(String title) throws IOException {
        title = title.replaceAll("\\s", "+");

        try {
            System.out.println("connecting to MangaDex API...");
            URL localurl = new URL(url.toString() + "?title=" + title);
            con = (HttpURLConnection) localurl.openConnection();
            System.out.println("working : " + con.getResponseCode());
        }catch (Exception e) {
            System.err.println("Smthn went wrong :P : " + con.getConnectTimeout());
        }

        System.out.println("---------\n");

        try {
            System.out.println("Searching Manga...");
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer res = new StringBuffer();
            while((line = bf.readLine()) != null) {
                res.append(line);
            }
            bf.close();
            con.disconnect();

            JSONArray mangaresults = new JSONArray(new JSONObject(res.toString()).getJSONArray("data"));

            System.out.println("RESULTS: \n\n");

            for (int i = 0; i < mangaresults.length(); i++) {
                JSONObject mangaInfo = new JSONObject(mangaresults.get(i).toString());
                JSONObject attributes = mangaInfo.getJSONObject("attributes");
                JSONObject resultitles = attributes.getJSONObject("title");
                JSONObject resultdescription = attributes.getJSONObject("description");

                if (resultitles.has("en")) {
                    System.out.println(resultitles.getString("en"));
                } else {
                    System.out.println(resultitles.getString("ja"));
                }
                if (resultdescription.has("en")) {
                    System.out.println(resultdescription.getString("en"));
                }
                System.out.println("\n--------------------------------------------------------------------\n");

            }

        } catch (IOException ie) {
            System.err.println("lmao what");
        }
    }
}
