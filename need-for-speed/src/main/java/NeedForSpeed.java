class NeedForSpeed {
    int meters=0;
    int battery=100;
    int batteryDrain;
    int speed;
    NeedForSpeed(int speed, int batteryDrain) {
        this.speed=speed;
        this.batteryDrain=batteryDrain;
    }

    public boolean batteryDrained() {
        return this.battery < this.batteryDrain;
    }

    public int distanceDriven() {
        return this.meters;
    }

    public void drive() {
        if(this.batteryDrained()) return;
        this.battery-=this.batteryDrain;
        this.meters+=this.speed;
    }

    public static NeedForSpeed nitro() {
        return new NeedForSpeed(50,4);
    }
}

class RaceTrack {
    int distance;
    RaceTrack(int distance) {
        this.distance=distance;
    }

    public boolean canFinishRace(NeedForSpeed car) {
        return car.speed*(100/car.batteryDrain) >= distance;
    }
}
