package namedEntities;

import java.util.ArrayList;
import java.util.List;

import namedEntities.stats.Category;
import namedEntities.stats.Topic;

public class NamedEntityBuilder {
    static String label;
    static List<Topic> topics;
    static Category category;
    static List<String> keywords;

    public NamedEntityBuilder() {
        label = "EMPTY";
        topics = new ArrayList<>();
        category = new Category("OTHER");
        keywords = new ArrayList<>();
    }

    public NamedEntityBuilder withLabel(String l) {
        label = l;
        return this;
    }

    public NamedEntityBuilder withTopics(List<Topic> t) {
        topics = t;
        return this;
    }

    public NamedEntityBuilder withCategory(Category c) {
        category = c;
        return this;
    }

    public NamedEntityBuilder withKeywords(List<String> k) {
        keywords = k;
        return this;
    }

    public NamedEntity build() {
        return new NamedEntity(label, topics, category, keywords);
    }
}
