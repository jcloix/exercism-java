public class Lasagna {
    // TODO: define the 'expectedMinutesInOven()' method
    public int expectedMinutesInOven() {
        return 40;
    }
    // TODO: define the 'remainingMinutesInOven()' method
    public int remainingMinutesInOven(int spentMinutes) {
        return expectedMinutesInOven() - spentMinutes;
    }
    // TODO: define the 'preparationTimeInMinutes()' method
    public int preparationTimeInMinutes(int nbLayers) {
        return nbLayers*2;
    }

    // TODO: define the 'totalTimeInMinutes()' method
    public int totalTimeInMinutes(int nbLayers, int spentMinutes) {
        return preparationTimeInMinutes(nbLayers) + spentMinutes;
    }
}
