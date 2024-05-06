package namedEntities.stats;

class Stat {
    private String name;
    private int occurrences;

    public Stat(String name){
        this.name = name;
        this.occurrences = 1;
    }

    public void incrementOccurrences() {
        this.occurrences++;
    }

    public String getName(){
        return this.name;
    }

    public void print(){
        System.out.println("        " + this.name + '(' + this.occurrences + ')');
    }
}