package namedEntities.stats;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private String name;
    private List<Stat> stats;

    public Topic(String name){
        this.name = name;
        this.stats = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public List<Stat> geStats() {
        return this.stats;
    }
    
    public void add(String stat){
        boolean r = false;
        for(Stat s : this.stats) {
            if (s.getName().equals(stat)){
                r = true;
                s.incrementOccurrences();
            }
        }
        if (!r) {
            this.stats.add(new Stat(stat));
        }
    }

    public void print(){
        System.out.println("Topic: "+ this.name);
        for (Stat s : this.stats){
            s.print();
        }
    }
}
