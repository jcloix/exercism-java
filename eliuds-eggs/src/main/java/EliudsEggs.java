public class EliudsEggs {
    public int eggCount(int number) {
        return (int)Integer.toBinaryString(number)
                .chars()
                .filter(c -> c == '1')
                .count();
    }
}
