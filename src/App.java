import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import feed.*;
import namedEntities.heuristics.*;
import namedEntities.stats.*;
import namedEntities.*;
import utils.*;

public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(
                    "Could not find \"src/data/feeds.json\", make sure the file \"feeds.json\" exists and/or is correctly named.");
            System.exit(1);
        }
        List<DictionaryData> dictionaryDataArray = new ArrayList<>();
        try {
            dictionaryDataArray = JSONParser.parseJsonDictionaryDatas("src/data/dictionary.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(
                    "Could not find \"src/data/dictionary.json\", make sure the file \"dictionary.json\" exists and/or is correctly named.");
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);

        try {
            run(config, feedsDataArray, dictionaryDataArray, HeuristicUtils.getHeuristics());
        } catch (MalformedURLException e) {
            System.err.println("Failed to fetch feed/s due to malformed URL");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Something went wrong during an IO operation");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void run(Config config, List<FeedsData> feedsDataArray, List<DictionaryData> dictionaryDataArray,
            List<String> heuristicNames) throws Exception {

        if (config.getPrintHelp()) {
            Help.printHelp(feedsDataArray, heuristicNames);
            System.exit(0);
        }

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            throw new IllegalArgumentException("No feeds data found");
        }

        if (dictionaryDataArray == null || dictionaryDataArray.size() == 0) {
            throw new IllegalArgumentException("No dictionary data found");
        }

        if (config.getPrintStats() &&
                !(config.getStatsFormat().equals("cat") ||
                config.getStatsFormat().equals("topic") ||
                config.getStatsFormat() == null)) {
            throw new IllegalArgumentException(
                "Invalid stat format : " + config.getStatsFormat() + ", for more information use -h or --help"
                );
        }

        if (config.getFeedKey() != null) {
            List<Boolean> bools = feedsDataArray.stream()
                    .map(e -> e.getLabel().equals(config.getFeedKey()))
                    .collect(Collectors.toList());
            Boolean shouldThrow = !bools.stream()
                    .reduce(false, (a, b) -> a || b);

            if (shouldThrow) {
                throw new IllegalArgumentException(
                    "Invalid feed key : " + config.getFeedKey() + ", please provide a valid Feed Key, for more information use -h or --help"
                    );
            }
        }

        List<Article> allArticles = new ArrayList<>();
        if (!(config.getFeedKey() == null)) {
            for (FeedsData feed : feedsDataArray) {
                if (feed.getLabel().equals(config.getFeedKey())) {
                    allArticles.addAll(FeedParser.parseXML(FeedParser.fetchFeed(feed.getUrl())));
                }
            }
        } else {
            for (FeedsData feed : feedsDataArray) {
                allArticles.addAll(FeedParser.parseXML(FeedParser.fetchFeed(feed.getUrl())));
            }
        }

        if (config.getPrintFeed() || !config.getComputeNamedEntities()) {
            System.out.println("Printing feed(s) ");
            allArticles.forEach(Article::print);
        }

        if (config.getComputeNamedEntities()) {
            HeuristicsFactory factory = new HeuristicsFactory();
            Heuristics heuristic = factory.createHeuristics(config.getHeuristicName());
            if (heuristic == null) {
                throw new Exception(
                    "No heuristic available named " + config.getHeuristicName() + ", please provide a valid Heuristic, for more information use -h or --help"
                    );
            }
            System.out.println("Computing named entities using " + config.getHeuristicName() + " heuristic");

            final StringBuilder entityStringBuilder = new StringBuilder();
            allArticles.forEach(art -> {
                entityStringBuilder.append(art.getTitle())
                        .append(art.getDescription());
            });
            String entityString = entityStringBuilder.toString();

            List<String> candidates = heuristic.extractCandidates(entityString);
            NamedEntityBuilder namedEntityBuilder = new NamedEntityBuilder();
            List<NamedEntity> namedEntities = new ArrayList<>();

            for (DictionaryData dictionaryData : dictionaryDataArray) {
                for (String candidate : candidates) {
                    if (dictionaryData.getKeywords().contains(candidate)) {
                        namedEntityBuilder.withLabel(dictionaryData.getLabel());
                        namedEntityBuilder.withCategory(dictionaryData.getCategory());
                        namedEntityBuilder.withKeywords(dictionaryData.getKeywords());
                        namedEntityBuilder.withTopics(dictionaryData.getTopics());
                        namedEntities.add(namedEntityBuilder.build());
                    }
                }
            }

            System.out.println("\nStats: ");
            if (config.getPrintStats() && !config.getStatsFormat().equals("cat")) {
                List<TopicStat> topicStats = Stats.getTopicStats(namedEntities);
                topicStats.forEach(TopicStat::print);
            } else {
                List<CatStat> catStats = Stats.getCatStats(namedEntities);
                catStats.forEach(CatStat::print);
            }
            System.out.println("-".repeat(80));
        }
    }
}
