import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class CircularBuffer<T> {
    Queue<T> buffer;
    int initialSize;
    CircularBuffer(final int size) {
        this.buffer = new PriorityQueue<>(size);
        this.initialSize = size;
    }

    T read() throws BufferIOException {
        if(buffer.isEmpty()) throw new BufferIOException("Tried to read from empty buffer");
        return buffer.poll();
    }

    void write(T data) throws BufferIOException {
        if(buffer.size() == initialSize) throw new BufferIOException("Tried to write to full buffer");
        buffer.add(data);
    }

    void overwrite(T data) {
        if(buffer.size() == initialSize) {
            buffer.remove();
        }
        buffer.add(data);
    }

    void clear() {
        buffer.clear();
    }

}