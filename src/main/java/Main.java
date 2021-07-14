import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Document web;
        ArrayList<String> siteUrl = new ArrayList<>();
        web = Jsoup.connect("https://www.walla.co.il/").get();
      /*  System.out.println(web.getElementsByClass("with-roof ").get(0).child(0).attributes().get("href"));  //קישור לכתבה הראשית*/
        for (Element element : web.getElementsByClass("main-taste").get(0).getElementsByTag("a")) {
            siteUrl.add(element.attributes().get("href"));
        }
        for (String url : siteUrl) {
        web = Jsoup.connect(url).get();

        }



    }
}
