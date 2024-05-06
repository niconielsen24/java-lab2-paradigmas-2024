package namedEntities.stats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import namedEntities.NamedEntity;

public class Stats {
    public static List<Category> getCatStats(List<NamedEntity> entities){
        List<Category> categories = new ArrayList<>();
        Set<String> catNames = new HashSet<>();

        for (NamedEntity e : entities) {
            catNames.add(e.getCategory());
        }

        for(String s :catNames) {
            categories.add(new Category(s));
        }

        for (NamedEntity e : entities) {
            for(Category c : categories) {
                if (c.getName().equals(e.getCategory())) {
                    c.add(e.getLabel());
                }
            }
        }

        return categories;
    }

    public static List<Topic> getTopicStats(List<NamedEntity> entities){
        List<Topic> topics = new ArrayList<>();
        Set<String> topicNames = new HashSet<>();

        for (NamedEntity entity : entities) {
            for(String topic : entity.getTopic()){
                topicNames.add(topic);
            }
        }

        for(String s : topicNames){
            topics.add(new Topic(s));
        }

        for(NamedEntity e : entities){
            for(Topic t : topics){
                for (String s : e.getTopic()) {
                    if (t.getName().equals(s)) {
                        t.add(e.getLabel());
                    }
                }
            }
        }

        return topics;
    }
}
