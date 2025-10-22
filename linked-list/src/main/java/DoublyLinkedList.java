class DoublyLinkedList<T> {
    private Element<T> head;
    private Element<T> queue;

    void push(T value) {
        Element<T> el = new Element<>(value,null,head);
        if(head!=null) {
            // link the old head to the newly to be head.
            head.prev=el;
        }
        if(queue==null) {
            queue=el;
        }
        head=el;
    }

    T pop() {
        Element<T> el = head;
        head = head.next;
        if(head != null) {
            head.prev = null;
        }
        return el.value;
    }

    void unshift(T value) {
        Element<T> el = new Element<>(value,queue,null);
        if(queue!=null) {
            // link the old queue to the newly to be queue.
            queue.next=el;
        }
        if(head==null) {
            head=el;
        }
        queue=el;

    }

    T shift() {
        Element<T> el = queue;
        queue = queue.prev;
        if(queue!= null) {
            queue.next = null;
        }
        return el.value;
    }

    private static final class Element<T> {
        private final T value;
        private Element<T> prev;
        private Element<T> next;

        Element(T value, Element<T> prev, Element<T> next) {
            this.value=value;
            this.prev=prev;
            this.next=next;
        }
    }
}
