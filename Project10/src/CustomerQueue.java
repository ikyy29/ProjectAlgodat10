public class CustomerQueue {
    private Customer head;
    private Customer tail;
    private int nextQueueNo = 1;

    public CustomerQueue() {
        head = tail = null;
    }

    // enqueue: add customer at end, return created Customer
    public Customer enqueue(String name) {
        Customer c = new Customer(name, nextQueueNo++);
        if (tail == null) { head = tail = c; return c; }
        tail.next = c; tail = c; return c;
    }

    // dequeue: remove front and return
    public Customer dequeue() {
        if (head == null) return null;
        Customer c = head;
        head = head.next;
        if (head == null) tail = null;
        c.next = null;
        return c;
    }

    public boolean isEmpty() { return head == null; }

    // peek front
    public Customer peek() { return head; }

    // display queue
    public void display() {
        System.out.println("=== CUSTOMER QUEUE ===");
        if (head == null) { System.out.println("(kosong)"); return; }
        Customer cur = head;
        while (cur != null) {
            System.out.println(" #" + cur.queueNo + " - " + cur.name);
            cur = cur.next;
        }
    }
}
