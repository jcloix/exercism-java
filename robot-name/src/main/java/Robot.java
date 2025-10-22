import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Robot {
    private static final Set<String> usedNames = new HashSet<>();
    private static final Random random = new Random();

    private String name;

    public Robot() {
        name = generateUniqueName();
    }

    public String getName() {
        if (name == null) {
            name = generateUniqueName();
        }
        return name;
    }

    public void reset() {
        name = generateUniqueName();
    }

    private static String generateUniqueName() {
        String newName;
        do {
            newName = generateRandomName();
            // Ensure thread-safe access to the global set
            synchronized (usedNames) {
                if (!usedNames.contains(newName)) {
                    usedNames.add(newName);
                    break;
                }
            }
        } while (true);
        return newName;
    }

    private static String generateRandomName() {
        char first = (char) ('A' + random.nextInt(26));
        char second = (char) ('A' + random.nextInt(26));
        int number = random.nextInt(1000); // 000–999
        return String.format("%c%c%03d", first, second, number);
    }
}