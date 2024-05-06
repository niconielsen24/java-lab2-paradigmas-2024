import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import feed.Article;
import feed.FeedParser;
import namedEntities.heuristics.*;
import namedEntities.stats.Category;
import namedEntities.stats.Stats;
import namedEntities.stats.Topic;
import namedEntities.*;
import utils.Config;
import utils.DictionaryData;
import utils.DictionaryDataComparator;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;

public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        List<DictionaryData> dictionaryDataArray = new ArrayList<>();
        try {
            dictionaryDataArray = JSONParser.parseJsonDictionaryDatas("src/data/dictionary.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);

        run(config, feedsDataArray, dictionaryDataArray);
    }

    private static void run(Config config, List<FeedsData> feedsDataArray, List<DictionaryData> dictionaryDataArray) {

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }

        if (dictionaryDataArray == null || dictionaryDataArray.size() == 0){
            System.out.println("No dictionary data found");
            return;
        }

        List<Article> allArticles = new ArrayList<>();
        if (!(config.getFeedKey() == null)) {
            for(FeedsData feed : feedsDataArray){
                if(feed.getLabel().equals(config.getFeedKey())){
                    try {
                        allArticles.addAll(FeedParser.parseXML(FeedParser.fetchFeed(feed.getUrl())));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }    
        } else {
            for(FeedsData feed : feedsDataArray){
                try {
                    allArticles.addAll(FeedParser.parseXML(FeedParser.fetchFeed(feed.getUrl())));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }  

        if (config.getPrintFeed() || !config.getComputeNamedEntities()) {
            System.out.println("Printing feed(s) ");
            for (Article ar : allArticles)
                ar.print();
        }

        if (config.getPrintHelp()) {
            printHelp(feedsDataArray);
        }

        if (config.getComputeNamedEntities()) {

            System.out.println("Computing named entities using " + config.getHeuristicName() + " heuristic");

            String entitieString = "";
            List<String> candidates = new ArrayList<>();
            List<NamedEntity> namedEntities = new ArrayList<>();
            HeuristicsFactory factory = new HeuristicsFactory();
            Heuristics heuristic = factory.createHeuristics(config.getHeuristicName());

            for (Article ar : allArticles) {
                entitieString += ar.getTitle();
                entitieString += ar.getDescription();
                entitieString += ar.getPubDate();
            }

            candidates = heuristic.extractCandidates(entitieString);
            Collections.sort(dictionaryDataArray,new DictionaryDataComparator());

            int index = 0;
            for (String candidate : candidates) {
                index = Collections.binarySearch(dictionaryDataArray, candidate);
                if (index >= 0) {
                    namedEntities.add(
                        new NamedEntity(
                            candidate, 
                            dictionaryDataArray.get(index).getTopics(), 
                            dictionaryDataArray.get(index).getCategory(), 
                            dictionaryDataArray.get(index).getKeywords()
                            ));
                }
            }

            System.out.println("\nStats: ");
            if (config.getPrintStats()) {
                switch (config.getStatsFormat()) {
                    case "cat":
                        List<Category> cats = Stats.getCatStats(namedEntities);
                        for (Category cat : cats) {
                            cat.print();
                        }
                        break;
                    case "topic":
                        List<Topic> topics = Stats.getTopicStats(namedEntities);
                        for (Topic topic : topics) {
                            topic.print();
                        }
                        break;
                    default:
                        List<Category> catsdef = Stats.getCatStats(namedEntities);
                        for (Category cat : catsdef) {
                            cat.print();
                        }
                        break;
                }
            } else {
                List<Category> cats = Stats.getCatStats(namedEntities);
                for (Category cat : cats) {
                    cat.print();
                }
            }
            System.out.println("-".repeat(80));
        }
    }

    private static void printHelp(List<FeedsData> feedsDataArray) {
        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with");
        System.out.println("                                       the specified key");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");
        // TODO: Print the available heuristics with the following format
        System.out.println("                                       <name>: <description>");
        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats");
        System.out.println("                                       topic: Topic-wise stats");
    }

}
