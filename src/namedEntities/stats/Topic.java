package namedEntities.stats;

public class Topic {
    private String name;

    public Topic(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void print(){
        System.out.println("Topic: "+ this.name);
    }
}
