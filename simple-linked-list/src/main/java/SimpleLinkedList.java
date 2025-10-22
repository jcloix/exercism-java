import java.lang.reflect.Array;
import java.util.NoSuchElementException;

class SimpleLinkedList<T> {
    SimpleNode head;
    SimpleNode queue;
    int size=0;
    SimpleLinkedList() {
    }

    SimpleLinkedList(T[] values) {
        for(T value : values) {
            push(value);
        }
    }

    void push(T value) {
        SimpleNode next = new SimpleNode(value);
        if(head == null) {
            this.head = next;
            this.queue = next;
        } else {
            next.prev = queue;
            this.queue.next=next;
            this.queue=next;
        }
        size++;
    }

    T pop() {
        if(this.head==null) {
            throw new NoSuchElementException();
        }
        T val = this.queue.value;
        if(size() == 1) {
            this.head=null;
            this.queue=null;
        } else {
            this.queue = this.queue.prev;
            this.queue.next=null;
        }
        size--;
        return val;
    }

    void reverse() {
        SimpleNode it = this.queue;
        this.queue = this.head;
        this.head = it;
        while(it != null) {
            SimpleNode tmp = it.next;
            it.next = it.prev;
            it.prev = tmp;
            it=it.next;
        }
    }

    T[] asArray(Class<T> clazz) {
        reverse();
        T[] results = (T[])Array.newInstance(clazz,size());
        SimpleNode it = this.head;
        int i=0;
        while(it!=null) {
            results[i++]=it.value;
            it=it.next;
        }
        return results;
    }

    int size() {
        return size;
    }

    class SimpleNode {
        T value;
        SimpleNode next;
        SimpleNode prev;
        public SimpleNode(T value) {
            this.value = value;
        }
    }
}
