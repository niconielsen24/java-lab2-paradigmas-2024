package namedEntities;

import java.util.*;

import namedEntities.stats.Category;
import namedEntities.stats.Topic;

public class NamedEntity {
    private String label;
    private List<Topic> topics;
    private Category category;
    private List<String> keywords;

    NamedEntity(String label, List<Topic> topics, Category category, List<String> keywords) {
        this.label = label;
        this.topics = topics;
        this.category = category;
        this.keywords = keywords;
    }

    public String getLabel() {
        return label;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public Category getCategory() {
        return category;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void print() {
        System.out.println("Label: " + this.label);
        System.out.println("Category: " + this.category.getName());
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