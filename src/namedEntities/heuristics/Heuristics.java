package namedEntities.heuristics;

import java.util.List;

/**
 * Heuristic
 */
public interface Heuristics {
    String getName();
    List<String> extractCandidates(String text);
}