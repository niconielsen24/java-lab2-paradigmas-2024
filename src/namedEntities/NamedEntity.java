package namedEntities;

import java.util.*;

public class NamedEntity {
    private String label;
    private List<String> topics;
    private String category;
    private List<String> keywords;
    // private int occurrences = 0;

    public NamedEntity(String label, List<String> topics, String category, List<String> keywords) {
        this.label = label;
        this.topics = topics;
        this.category = category;
        this.keywords = keywords;
        // this.occurrences++;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getTopic() {
        return topics;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    // public int getOccurrences() {
        // return occurrences;
    // }

    // public void incrementOccurrences() {
        // this.occurrences++;
    // }

    // public void decrementOccurrences() {
        // this.occurrences--;
    // }

    public void print() {
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
        // System.out.println("Occurrences: " + occurrences);
    }
}