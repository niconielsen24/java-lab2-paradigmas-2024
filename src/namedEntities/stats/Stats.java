package namedEntities.stats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import namedEntities.NamedEntity;

public class Stats {
    public static List<CatStat> getCatStats(List<NamedEntity> entities) {
        Set<String> catNames = new HashSet<>();
        List<CatStat> stats = new ArrayList<>();

        for (NamedEntity e : entities) {
            catNames.add(e.getCategory().getName());
        }

        for (String cat : catNames) {
            stats.add(new CatStat(new Category(cat)));
        }

        for (NamedEntity e : entities) {
            for (Stat st : stats) {
                if (st.getName().equals(e.getCategory().getName())) {
                    st.add(e.getLabel());
                }
            }
        }

        return stats;
    }

    public static List<TopicStat> getTopicStats(List<NamedEntity> entities) {
        Set<String> topicNames = new HashSet<>();
        List<TopicStat> stats = new ArrayList<>();

        for (NamedEntity e : entities) {
            for (Topic topic : e.getTopics()) {
                topicNames.add(topic.getName());
            }
        }

        for (String topic : topicNames) {
            stats.add(new TopicStat(new Topic(topic)));
        }

        for (NamedEntity e : entities) {
            for (Topic topic : e.getTopics()) {
                for (Stat st : stats) {
                    if (st.getName().equals(topic.getName())) {
                        st.add(e.getLabel());
                    }
                }
            }
        }

        return stats;
    }
}
