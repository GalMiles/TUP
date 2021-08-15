package wikipediaAPI;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class wikiAPIManager {

    public static String getAttractionDescriptionFromWiki(String attractionName) throws IOException {

        String newAttractionName = attractionName.replace(" ", "_");
        String url = "https://en.wikipedia.org/w/api.php?action=query&titles=" + newAttractionName + "&prop=extracts&format=json";

        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String lines = reader.lines().collect(Collectors.joining());

        String descriptionHtml = lines.split("\"extract\":\"")[1];
        String descriptionText = Jsoup.parse(descriptionHtml).wholeText();
        descriptionText = descriptionText.replaceAll("\\\\n", "\n");

        String[] fourFirstSentences = descriptionText.split("[.]", 5);
        fourFirstSentences[0] = fourFirstSentences[0].replaceAll("\n", "");
        reader.close();

        return(fourFirstSentences[0].trim() + ".\n" + fourFirstSentences[1].trim() + ".\n" + fourFirstSentences[2].trim() + ".\n" + fourFirstSentences[3].trim() + ".\n");

    }
}
