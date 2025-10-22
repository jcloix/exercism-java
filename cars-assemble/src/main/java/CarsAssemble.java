public class CarsAssemble {
    static int productionRate=221;
    public double productionRatePerHour(int speed) {

        switch(speed){
            case 1,2,3,4:return speed*productionRate;
            case 5,6,7,8:return speed*productionRate*0.9;
            case 9:return speed*productionRate*0.8;
            default:return speed*productionRate*0.77;
        }
    }

    public int workingItemsPerMinute(int speed) {
        return (int)productionRatePerHour(speed)/60;
    }
}
