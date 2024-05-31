package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import namedEntities.stats.Category;
import namedEntities.stats.Topic;

public class JSONParser {

    static public List<FeedsData> parseJsonFeedsData(String jsonFilePath) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        List<FeedsData> feedsList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            String url = jsonObject.getString("url");
            String type = jsonObject.getString("type");
            feedsList.add(new FeedsData(label, url, type));
        }
        return feedsList;
    }

    static public List<DictionaryData> parseJsonDictionaryDatas(String jsonFilePath) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        List<DictionaryData> dictionaryList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            Category category = new Category(jsonObject.getString("Category"));
            JSONArray topics = jsonObject.getJSONArray("Topics");
            List<Topic> topicList = new ArrayList<>();
            for (int j = 0; j < topics.length(); j++) {
                topicList.add(new Topic(topics.getString(j)));
            }
            JSONArray keywords = jsonObject.getJSONArray("keywords");
            List<String> keywordList = new ArrayList<>();
            for (int j = 0; j < keywords.length(); j++) {
                keywordList.add(keywords.getString(j));
            }
            dictionaryList.add(new DictionaryData(label, category, topicList, keywordList));
        }
        return dictionaryList;
    }

}
