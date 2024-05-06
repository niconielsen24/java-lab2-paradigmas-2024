package namedEntities.heuristics;

public class HeuristicsFactory {
    public Heuristics createHeuristics(String name){
        switch(name){
            case "Capitalized Word":
                return new CapitalizedWordHeuristic();
            default:
                return null;
        }
    }
}