package utils;

import java.util.List;

public class DictionaryData implements Comparable<String>{
    private String label;
    private String category;
    private List<String> topics;
    private List<String> keywords;

    public DictionaryData(String label, String category, List<String> topics, List<String> keywords) {
        this.label = label;
        this.category = category;
        this.topics = topics;
        this.keywords = keywords;
    }

    public String getLabel() {
        return label;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTopics() {
        return topics;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void print(){
        System.out.println("Label: " + label);
        System.out.println("Category: " + category);
        System.out.println("Topics: ");
        for (String topic : topics) {
            System.out.println("    " + topic);
        }
        System.out.println("Keywords: ");
        for (String keyword : keywords) {
            System.out.println("    " + keyword);
        }
    }

    @Override
    public int compareTo(String other) {
        return this.label.compareTo(other);
    }
} 