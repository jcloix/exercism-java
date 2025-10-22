import java.util.*;

public class SplitSecondStopwatch {
    private State state = State.READY;
    List<String> times = new ArrayList<>();
    String currentTime = "00:00:00";
    public void start() {
        if(!state.canStart()) {
            throw new IllegalStateException("cannot start an already running stopwatch");
        }
        state = State.RUNNING;
    }

    public void stop() {
        if(!state.canStop()) {
            throw new IllegalStateException("cannot stop a stopwatch that is not running");
        }
        state = State.STOPPED;
    }

    public void reset() {
        if(!state.canReset()) {
            throw new IllegalStateException("cannot reset a stopwatch that is not stopped");
        }
        state = State.READY;
        times.clear();
        currentTime="00:00:00";
    }

    public void lap() {
        if(!state.canLap()) {
            throw new IllegalStateException("cannot lap a stopwatch that is not running");
        }
        times.add(currentTime);
        currentTime="00:00:00";

    }

    public String state() {
        return this.state.getLabel();
    }

    public String currentLap() {
        return currentTime;
    }

    public String total() {
        return addTimes(Arrays.asList(addTimes(times),currentTime));
    }

    public java.util.List<String> previousLaps() {
        return times;
    }

    public void advanceTime(String timeString) {
        if(State.RUNNING.equals(this.state)) {
            currentTime = addTimes(Arrays.asList(currentTime,timeString));
        }
    }

    private String addTimes(List<String> times) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        for(String time : times) {
            String[] split = time.split(":");
            seconds+= Integer.parseInt(split[2]);
            if(seconds>59) {
                minutes++;
                seconds = seconds % 60;
            }
            minutes+= Integer.parseInt(split[1]);
            if(minutes>59) {
                hours++;
                minutes = minutes % 60;
            }
            hours+= Integer.parseInt(split[0]);
        }
        return String.format("%02d:%02d:%02d",hours,minutes,seconds);
    }

}

enum State {
    READY("ready"),
    RUNNING("running"),
    STOPPED("stopped");
    private String label;

    State(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean canStart() {
        return Arrays.asList(READY,STOPPED).contains(this);
    }

    public boolean canStop() {
        return Objects.equals(RUNNING, this);
    }

    public boolean canLap() {
        return Objects.equals(RUNNING, this);
    }

    public boolean canReset() {
        return Objects.equals(STOPPED, this);
    }
}