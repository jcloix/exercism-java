import java.util.Arrays;
import java.util.stream.IntStream;

class BirdWatcher {
    private final int[] birdsPerDay;

    public BirdWatcher(int[] birdsPerDay) {
        this.birdsPerDay = birdsPerDay.clone();
    }

    public int[] getLastWeek() {
        return birdsPerDay;
    }

    public int getToday() {
        return birdsPerDay[birdsPerDay.length-1];
    }

    public void incrementTodaysCount() {
        birdsPerDay[birdsPerDay.length-1]+=1;
    }

    public boolean hasDayWithoutBirds() {
        return Arrays.stream(birdsPerDay).anyMatch(q->q==0);
    }

    public int getCountForFirstDays(int numberOfDays) {
        return IntStream.range(0, Math.min(numberOfDays, 7)).map(q->birdsPerDay[q]).sum();
    }

    public int getBusyDays() {
        return (int)Arrays.stream(birdsPerDay).filter(q->q>4).count();
    }
}
