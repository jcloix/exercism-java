import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

class HandshakeCalculator {

    List<Signal> calculateHandshake(int number) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(number));
        AtomicInteger i = new AtomicInteger();
        List<Signal> signals = new ArrayList<>();
        sb.reverse().toString().chars().forEach(q-> {
            int index = i.getAndIncrement();
            if((char)q == '1' && index < 4) {
                signals.add(Signal.values()[index]);
            } else if((char)q == '1' && index == 4) {
                List<Signal> reversed = new ArrayList<>(signals.reversed());
                signals.clear();
                signals.addAll(reversed);
            }
        });
        return signals;
    }

}
