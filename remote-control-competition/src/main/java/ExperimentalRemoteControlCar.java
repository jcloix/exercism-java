public class ExperimentalRemoteControlCar implements RemoteControlCar {
    int meters=0;
    public void drive() {
        this.meters+=20;
    }

    public int getDistanceTravelled() {
        return this.meters;
    }
}
