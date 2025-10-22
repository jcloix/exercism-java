public enum TravelMethod {
    WALKING("by walking"),
    HORSEBACK("on horseback");
    String shortName;
    TravelMethod(String shortName) {
        this.shortName=shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
