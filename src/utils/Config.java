package utils;

// import namedEntities.heuristics.Heuristics;

public class Config {
    private boolean printFeed = false;
    private boolean computeNamedEntities = false;
    private boolean printHelp = false;
    private Boolean printStats = false;
    private String statsFormat;
    private String feedKey;
    private String heuristicName;

    public Config(boolean printFeed, boolean computeNamedEntities, boolean printHelp, boolean printStats, String statsFormat, String heuristicName, String feedKey) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.printHelp = printHelp;
        this.printStats = printStats;
        this.statsFormat = statsFormat;
        this.heuristicName = heuristicName;
        this.feedKey = feedKey;
    }

    public boolean getPrintFeed() {
        return this.printFeed;
    }

    public boolean getComputeNamedEntities() {
        return this.computeNamedEntities;
    }

    public boolean getPrintHelp() {
        return this.printHelp;
    }

    public boolean getPrintStats(){
        return this.printStats;
    }

    public String getStatsFormat(){
        return this.statsFormat;
    }

    public String getHeuristicName() {
        return this.heuristicName;
    }
    
    public String getFeedKey() {
        return this.feedKey;
    }

}
