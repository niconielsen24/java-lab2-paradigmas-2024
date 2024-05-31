package namedEntities.stats;

import java.util.HashMap;
import java.util.Map;

public class TopicStat implements Stat{
    private Topic name;
    private HashMap<String, Integer> dict;

    public TopicStat(Topic name){
        this.name = name;
        this.dict = new HashMap<>();
    }

    public void add(String entity){
        if (!dict.containsKey(entity)) {
            dict.put(entity, 1);
        } else {
            dict.put(entity, dict.get(entity)+1);
        }
    }

    public String getName(){
        return this.name.getName();
    }

    public HashMap<String, Integer> getDict(){
        return this.dict;
    }

    public void print(){
        System.out.println("Topic : " + this.name.getName());
        for (Map.Entry<String, Integer> entry : this.dict.entrySet()){
            System.out.println("        " + entry.getKey() + '(' + entry.getValue() + ')');
        }
    }
}
