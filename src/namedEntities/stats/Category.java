package namedEntities.stats;

public class Category {
    private String name;

    public Category(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void print(){
        System.out.println("Category: "+ this.name);
    }
}
