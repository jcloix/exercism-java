import java.util.stream.Collectors;

public class AffineCipher {
    
    public String encode(String text, int coefficient1, int coefficient2){
        if(!areCoprime(26,coefficient1)) {
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
        }
        String encode = text.chars().filter(c->Character.isLetter(c) || Character.isDigit(c))
                .mapToObj(c->encodeLetter(c,coefficient1,coefficient2))
                .map(String::valueOf)
                .collect(Collectors.joining());
        return insertSpaceEvery5(encode);
    }

    public char encodeLetter(int c, int coefficient1, int coefficient2) {
        if(Character.isDigit(c)) return (char)c;
        int i = Character.isLowerCase(c) ? c -'a': c- 'A';
        int encode = (coefficient1*i + coefficient2) % 26;
        return (char) (encode+'a');
    }

    public String decode(String text, int coefficient1, int coefficient2){
        int modInverse = modInverse(coefficient1,26);
        String clean = text.replaceAll(" ","");
        return clean.chars().mapToObj(c->decodeLetter(c,modInverse,coefficient2))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public Character decodeLetter(int c, int modInverse, int coefficient2) {
        if(!Character.isLetter(c)) return (char)c;
        int y = c -'a';
        int decode = mod(modInverse*(y-coefficient2),26);
        return (char) ('a' + decode);
    }

    public int mod(int a, int m) {
        return ((a%m) + m) % m;
    }

    public static String insertSpaceEvery5(String input) {
        return input.replaceAll("(.{5})(?=.)", "$1 ");
    }

    public static boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public int modInverse(int a, int m) {
        int m0 = m, t = 0, x = 1;

        if(!areCoprime(m,a)) {
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
        }
        while (a > 1) {
            int q = a / m;
            int temp = m;

            // Apply Euclid's algorithm
            m = a % m;
            a = temp;

            temp = t;
            t = x - q * t;
            x = temp;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }
}