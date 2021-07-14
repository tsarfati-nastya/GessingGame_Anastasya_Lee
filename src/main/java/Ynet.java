
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ynet extends BaseRobot {
    static Map<String, Integer> map = new HashMap<>();
    public Ynet () throws IOException {
        super ("https://www.ynet.co.il/home/0,7340,L-8,00.html");
        Document web;
        web = Jsoup.connect(getRootWebsiteUrl()).get();
        siteUrl.add(web.getElementsByClass("slotTitle").get(0).child(0).attributes().get("href"));
        Element element = web.getElementsByClass("YnetMultiStripComponenta oneRow multiRows").get(0);
        for (Element textDiv : element.getElementsByClass("textDiv")) {
            siteUrl.add(textDiv.child(1).attributes().get("href"));
        }
        Element element1 = web.getElementsByClass("MultiArticleRowsManualComponenta").get(0);
        for (Element mediaItems : element.getElementsByClass("mediaItems")) {
            siteUrl.add(mediaItems.child(0).child(0).attributes().get("href"));
        }
        for (Element slotTitle_small : element.getElementsByClass("slotTitle small")) {
            siteUrl.add(slotTitle_small.child(0).attributes().get("href"));
        }
    }
    static ArrayList<String> siteUrl = new ArrayList<>();

    @Override
    public Map<String, Integer> getWordsStatistics() {
        Document web;
        for (String url : siteUrl) {
            String articleText = "";
            try {
                web = Jsoup.connect(url).get();
                articleText += web.getElementsByClass("mainTitle").text() + " ";
                articleText += web.getElementsByClass("subTitle").text() + " ";
                for (Element text_editor_paragraph_rtl : web.getElementsByClass("text_editor_paragraph rtl")) {
                   articleText += text_editor_paragraph_rtl.text() + " ";
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
            for (Element container : web.getElementsByClass("layoutContainer")) {
                for (Element title_small : container.getElementsByClass("slotTitle small")) {
                    if (title_small.text().contains(text)) {
                        count++;
                    }
                }
                for (Element title_medium : container.getElementsByClass("slotTitle medium")) {
                    if (title_medium.text().contains(text)) {
                        count++;
                    }
                }
            }
            if (web.getElementsByClass("slotSubTitle").get(0).text().contains(text)) {
                count++;
            }
            if (web.getElementsByClass("slotTitle").get(0).text().contains(text)) {
                count++;
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
                for (Element text_editor_paragraph_rtl : web.getElementsByClass("text_editor_paragraph rtl")) {
                    articleText += text_editor_paragraph_rtl.text() + " ";
                }
                if (articleText.split(" ").length > maxLength) {
                    maxLength = articleText.split(" ").length;
                    siteUrlTitle = web.getElementsByClass("mainTitle").text();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return siteUrlTitle;
    }
}
