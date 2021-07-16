import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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


    @Override
    public Map<String, Integer> getWordsStatistics() throws IOException {
        for (String s : siteUrl) {
            Document web;
            web = Jsoup.connect(s).get();
            String words = "";
            words += (web.getElementsByTag("h1").text());
            words +=(web.getElementsByTag("h2").text());
            words +=(web.getElementsByTag("h3").text());
            words +=(web.getElementsByTag("h4").text());
            words +=(web.getElementsByTag("p").text());
            String[] wordsArray = words.split(" ");
            for (String word : wordsArray) {
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
    public int countInArticlesTitles(String text) throws IOException {
        Document mako = Jsoup.connect(getRootWebsiteUrl()).get();
        int count = 0;
        for (Element spanElements : mako.getElementsByTag("span")) {
            for (Element title : spanElements.getElementsByAttributeValue("data-type", "title")) {
                if (title.text().contains(text)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String getLongestArticleTitle() throws IOException {
        Document article;
        String longestArticleTitle = "";
        int longest = 0;
        for (String site : siteUrl) {
            article = Jsoup.connect(site).get();
            //title
            String title = article.getElementsByTag("h1").get(0).text();
            //article body
            StringBuilder siteTextBuilder = new StringBuilder();
            Element articleBody = article.getElementsByClass("article-body").get(0);
            for (Element p : articleBody.getElementsByTag("p")) {
                siteTextBuilder.append(p.text());
            }
            if (longest < siteTextBuilder.length()) {
                longest = siteTextBuilder.length();
                longestArticleTitle = title;
            }
        }
        return longestArticleTitle;
    }
}
