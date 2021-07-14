import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mako extends BaseRobot {
    static Map<String, Integer> map = new HashMap<>();
    static ArrayList<String> siteUrl = new ArrayList<>();
    public Mako () throws IOException {
        super ("https://www.mako.co.il/");
        Document web;
        web = Jsoup.connect(getRootWebsiteUrl()).get();
        String url;
        for (Element slider_image_inside : web.getElementsByClass("slider_image_inside")) {
            url = slider_image_inside.child(0).attributes().get("href");
            siteUrl.add("https://www.mako.co.il" + url);
        }
        for (Element newsPortal : web.getElementsByClass("neo_ordering scale_image horizontal news").get(0).getElementsByClass("verticalOuter")) {
            url = newsPortal.child(0).child(0).child(1).child(0).attributes().get("href");
            siteUrl.add("https://www.mako.co.il" + url);
        }
    }

    public static void main(String[] args)  {

    }

    @Override
    public Map<String, Integer> getWordsStatistics() {

        return map;
    }

    @Override
    public int countInArticlesTitles(String text) {
        return 0;
    }

    @Override
    public String getLongestArticleTitle() {
        return null;
    }
}
