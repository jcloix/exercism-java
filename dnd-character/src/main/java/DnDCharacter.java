import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

class DnDCharacter {
    Map<String,Integer> abilities = new HashMap<>();

    int ability(List<Integer> scores) {
        int min = scores.stream().min(Integer::compareTo).orElse(0);
        return scores.stream().mapToInt(Integer::intValue).sum() - min;
    }

    int ability(List<Integer> scores, String name) {
        if(abilities.containsKey(name)) return abilities.get(name);
        int min = scores.stream().min(Integer::compareTo).orElse(0);
        int ability= scores.stream().mapToInt(Integer::intValue).sum() - min;
        abilities.put(name,ability);
        return ability;
    }

    List<Integer> rollDice() {
        RandomGenerator r = RandomGenerator.getDefault();
        List<Integer> dices = new ArrayList<>();
        dices.add(r.nextInt(1,6));
        dices.add(r.nextInt(1,6));
        dices.add(r.nextInt(1,6));
        dices.add(r.nextInt(1,6));
        return dices;
    }


    int modifier(int input) {
        return (int) Math.floor((double)(input - 10 ) /2);
    }

    int getStrength() {
        return ability(rollDice(),"s");
    }

    int getDexterity() {
        return ability(rollDice(),"d");
    }

    int getConstitution() {
        return ability(rollDice(),"c");
    }

    int getIntelligence() {
        return ability(rollDice(),"i");
    }

    int getWisdom() {
        return ability(rollDice(),"w");
    }

    int getCharisma() {
        return ability(rollDice(),"ch");
    }

    int getHitpoints() {
        return 10 + modifier(getConstitution());
    }
}
