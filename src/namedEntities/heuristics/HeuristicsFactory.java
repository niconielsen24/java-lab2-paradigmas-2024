package namedEntities.heuristics;

/**
    * Creates a specific heuristic based on the given name.
    */
public class HeuristicsFactory {
    /**
    * Creates a specific heuristic based on the given name.
    *
    * @param name the name of the heuristic to create
    * @return the created heuristic, or null if no matching heuristic is found
    */
    public Heuristics createHeuristics(String name){
        switch(name){
            case "Capitalized Word":
                return new CapitalizedWordHeuristic();
            case "Only Messi":
                return new OnlyMessiHeuristic();
            case "Dict Matcher":
                return new DictMatcherHeuristic();
            case "Bigger Than 3":
                return new BiggerThan3Heuristic();
            default:
                return null;
        }
    }
}