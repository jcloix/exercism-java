public class JedliksToyCar {
    int meters = 0;
    int battery = 100;
    public static JedliksToyCar buy() {
        return new JedliksToyCar();
    }

    public String distanceDisplay() {
        return String.format("Driven %d meters",this.meters);
    }

    public String batteryDisplay() {
        if (this.battery == 0) return "Battery empty";
        return String.format("Battery at %d%%",this.battery);
    }

    public void drive() {
        if(this.battery==0) return;
        this.meters+=20;
        this.battery-=1;
    }
}
