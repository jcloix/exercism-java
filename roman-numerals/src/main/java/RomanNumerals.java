import java.util.List;
import java.util.Map;

class RomanNumerals {

    List<String> RomansLetter = List.of("M","D","C","L","X","V","I");
    StringBuilder roman = new StringBuilder();
    RomanNumerals(int number) {
        roman.append(RomansLetter.getFirst().repeat(number/1000));
        int i=2;
        int remaining = number%1000;
        int divide = 100;
        while(i < RomansLetter.size()) {
            int tmp = remaining/divide;
            remaining = remaining%divide;
            switch (tmp) {
                case 4->roman.append(RomansLetter.get(i)).append(RomansLetter.get(i-1));
                case 9->roman.append(RomansLetter.get(i)).append(RomansLetter.get(i-2));
                default -> {
                    if(tmp>4) {
                        roman.append(RomansLetter.get(i-1)).append((RomansLetter.get(i).repeat(tmp-5)));
                    } else {
                        roman.append(RomansLetter.get(i).repeat(tmp));
                    }
                }
            }
            divide/=10;
            i+=2;
        }
    }

    String getRomanNumeral() {
        return roman.toString();
    }

}
