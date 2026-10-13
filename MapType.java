public enum MapType {
    GENERATED("generated"), 
    MAP("designed");
    
    private final String name;
    MapType(String displayname){
        this.name = displayname;
    }
    public String getName(){
        return name;
    }
}
