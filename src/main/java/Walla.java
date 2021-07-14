import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Walla extends BaseRobot {
    static Map<String, Integer> map = new HashMap<>();
    static ArrayList<String> siteUrl = new ArrayList<>();

    public Walla() throws IOException {
        super("https://www.walla.co.il/");
        Document web;
        web = Jsoup.connect(getRootWebsiteUrl()).get();
        siteUrl.add(web.getElementsByClass("with-roof with-timer").get(0).child(0).attributes().get("href"));
        for (Element newsPortal : web.getElementsByClass("main-taste").get(0).getElementsByTag("a")) {
            siteUrl.add(newsPortal.attributes().get("href"));
        }

    }

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Document web;
        for (String url : siteUrl) {
            String articleText = "";
            try {
                web = Jsoup.connect(url).get();
                articleText += web.getElementsByTag("h1").get(0).text() + " ";
                articleText += web.getElementsByClass("item-main-content").get(0).child(0).child(1).text() + " ";
                for (Element textNews : web.getElementsByClass("css-onxvt4")) {
                    articleText += textNews.child(0).child(0).text() + " ";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] words = articleText.split(" ");
            for (String word : words) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }

            }
        }
        return map;
    }

    @Override
    public int countInArticlesTitles(String text) {
        Document web;
        int count = 0;
        try {
            web = Jsoup.connect(getRootWebsiteUrl()).get();
            String mainTitle = web.getElementsByClass("with-roof with-timer").get(0).getElementsByTag("h2").get(0).text();
            if (mainTitle.contains(text)){
                count ++;
            }
            String subTitle = web.getElementsByClass("with-roof with-timer").get(0).getElementsByTag("p").get(0).text();
            if (subTitle.contains(text)){
                count ++;
            }
            for (Element tisersTitle : web.getElementsByClass("main-taste").get(0).getElementsByTag("h3")) {
            if (tisersTitle.text().contains(text)){
                count ++;
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public String getLongestArticleTitle() {
        String siteUrlTitle = " ";
        int maxLength = -1;
        for (String url : siteUrl) {
            String articleText = " ";
            try {
                Document web = Jsoup.connect(url).get();
                for (Element textNews : web.getElementsByClass("css-onxvt4")) {
                    articleText += textNews.child(0).child(0).text() + " ";
                }
                if (articleText.split(" ").length > maxLength) {
                    maxLength = articleText.split(" ").length;
                    siteUrlTitle = web.getElementsByTag("h1").get(0).text();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return siteUrlTitle;
    }
}
