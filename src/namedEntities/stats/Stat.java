package namedEntities.stats;

import java.util.HashMap;

public interface Stat {
    public void add(String entity);

    public String getName();

    public HashMap<String, Integer> getDict();

    public void print();
}
