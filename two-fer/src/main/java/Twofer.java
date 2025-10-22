public class Twofer {
    public String twofer(String name) {
        return String.format("One for %s, one for me.",name==null || name.isEmpty()? "you":name);
    }
}
