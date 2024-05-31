package namedEntities.heuristics;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.DictionaryData;
import utils.JSONParser;

public class DictMatcherHeuristic implements Heuristics {

    @Override
    public String getName() {
        return "Dict Matcher";
    }

    @Override
    public List<String> extractCandidates(String text) {
        List<String> candidates = new ArrayList<>();

        text = text.replaceAll("[-+.^:,\"]", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{M}", "");

        Pattern pattern = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*");

        Matcher matcher = pattern.matcher(text);

        List<DictionaryData> dict = new ArrayList<>();
        
        try {
            dict = JSONParser.parseJsonDictionaryDatas("src/data/dictionary.json");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not find \"src/data/dictionary.json\", make sure the file \"dictionary.json\" exists and/or is correctly named.");
            System.exit(1);
        }

        while (matcher.find()) {
            for (DictionaryData d: dict) {
                for (String key : d.getKeywords()){
                    if(matcher.group().equals(key)){
                        candidates.add(matcher.group());
                    }
                }
            }
        }

        return candidates;
    }
}
