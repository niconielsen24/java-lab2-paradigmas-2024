package utils;

import java.util.List;

import namedEntities.stats.Category;
import namedEntities.stats.Topic;

public class DictionaryData {
    private String label;
    private Category category;
    private List<Topic> topics;
    private List<String> keywords;

    public DictionaryData(String label, Category category, List<Topic> topics, List<String> keywords) {
        this.label = label;
        this.category = category;
        this.topics = topics;
        this.keywords = keywords;
    }

    public String getLabel() {
        return this.label;
    }

    public Category getCategory() {
        return this.category;
    }

    public List<Topic> getTopics() {
        return this.topics;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public void print() {
        System.out.println("Label: " + this.label);
        System.out.println("Category: " + this.category);
        System.out.println("Topics: ");
        for (Topic topic : this.topics) {
            System.out.println("    " + topic.getName());
        }
        System.out.println("Keywords: ");
        for (String keyword : this.keywords) {
            System.out.println("    " + keyword);
        }
    }
}