import com.sun.jdi.IntegerValue;

class ProductionRemoteControlCar implements RemoteControlCar, Comparable<ProductionRemoteControlCar> {
    int meters=0;
    Integer nbVictories=0;
    public void drive() {
        this.meters+=10;
    }

    public int getDistanceTravelled() {
        return this.meters;
    }

    public int getNumberOfVictories() {
        return this.nbVictories;
    }

    public void setNumberOfVictories(int numberOfVictories) {
        this.nbVictories=numberOfVictories;
    }

    @Override
    public int compareTo(ProductionRemoteControlCar o) {
        return o.nbVictories.compareTo(nbVictories);
    }
}
