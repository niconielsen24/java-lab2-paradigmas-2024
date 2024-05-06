package feed;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.util.ArrayList;

public class FeedParser {

    public static List<Article> parseXML(String xmlData) {
        List<Article> articles = new ArrayList<>();

        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
        Document doc = builder.parse(input);

        NodeList itemList = doc.getElementsByTagName("item");
        for (int i = 0; i < itemList.getLength(); i++) {
            Element item = (Element) itemList.item(i);
            String title = getTextValue(item, "title");
            String description = getTextValue(item, "description");
            String pubDate = getTextValue(item, "pubDate");
            String link = getTextValue(item, "link");
            articles.add(new Article(title, description, pubDate, link));
        }
    } catch (Exception e) {
        e.printStackTrace();
        return articles;
    }
        return articles;
    }

    private static String getTextValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        }
        return "";
    }

    public static String fetchFeed(String feedURL) throws MalformedURLException, IOException, Exception {

        @SuppressWarnings("deprecation")
        URL url = new URL(feedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        
        connection.setRequestProperty("User-agent", "lab_paradigmas_2024_grupo_49");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new Exception("HTTP error code: " + status);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        }
    }
}
