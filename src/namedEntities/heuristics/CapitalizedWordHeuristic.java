package namedEntities.heuristics;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CapitalizedWordHeuristic implements Heuristics {

    public String getName() {
        return "Capitalized Word";
    }

    public List<String> extractCandidates(String text) {
        List<String> candidates = new ArrayList<>();

        List<String> pronounsEs = new ArrayList<>(Arrays.asList("Yo", "Tú", "Él", "Ella", "Usted", "Nosotros", "Nosotras", "Vosotros", "Vosotras", "Ellos", "Ellas", "Ustedes"));
        List<String> articlesEs = new ArrayList<>(Arrays.asList("El", "La", "Los", "Las", "Un", "Una", "Unos", "Unas"));
        List<String> pronounsEn = new ArrayList<>(Arrays.asList("I", "You", "He", "She", "It", "We", "You", "They"));
        List<String> articlesEn = new ArrayList<>(Arrays.asList("The", "A", "An"));

        text = text.replaceAll("[-+.^:,\"]", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{M}", "");

        Pattern pattern = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*");

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            candidates.add(matcher.group());
        }

        candidates.removeAll(pronounsEs);
        candidates.removeAll(pronounsEn);
        candidates.removeAll(articlesEs);
        candidates.removeAll(articlesEn);

        return candidates;
    }
}
