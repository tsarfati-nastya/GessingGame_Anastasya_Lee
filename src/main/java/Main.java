import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (!(choice > 0 && choice <= 3)) {
            System.out.println("list of website:\n" +
                    "1. Walla\n" +
                    "2. Mako\n" +
                    "3. Ynet");
            System.out.println("which web would you like to scan?");
            choice = scanner.nextInt();
        }
        BaseRobot site;
        switch (choice) {
            case 1:
                site = new Walla();
                break;
            case 2:
                site = new Mako();
                break;
            default:
                site = new Ynet();
                break;
        }
        System.out.println("what is the word that appears in the site?");
        System.out.println("hint: " + site.getLongestArticleTitle());
        int points = 0;
        Map<String, Integer> map = site.getWordsStatistics();
        for (int i = 1; i < 6; i++) {
            System.out.println("guess " + i);
            points += map.getOrDefault(scanner.next(), 0);
        }
        scanner.nextLine();
        System.out.println("which text should be in the main site?");
        String guess = scanner.nextLine();
        System.out.println("number of times?");
        int times = scanner.nextInt();
        if (Math.abs(site.countInArticlesTitles(guess) - times) <= 2) {
            points += 250;
        }
        System.out.println("your points are " + points);


    }
}
