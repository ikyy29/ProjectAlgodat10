public class OrderStack {
    private OrderItem top;

    public OrderStack() {
        top = null;
    }

    // push an item into the order
    public void push(ItemNode item, int qty) {
        OrderItem oi = new OrderItem(item, qty);
        oi.next = top;
        top = oi;
    }

    // pop (remove and return) top OrderItem
    public OrderItem pop() {
        if (top == null) return null;
        OrderItem t = top;
        top = top.next;
        t.next = null;
        return t;
    }

    // peek top without removing
    public OrderItem peek() {
        return top;
    }

    // total amount
    public double total() {
        double s = 0;
        OrderItem cur = top;
        while (cur != null) {
            s += cur.subtotal();
            cur = cur.next;
        }
        return s;
    }

    // display ordered items (top -> bottom)
    public void display() {
        System.out.println("   Items in order (top -> bottom):");
        if (top == null) { System.out.println("   (kosong)"); return; }
        OrderItem cur = top;
        int i = 1;
        while (cur != null) {
            System.out.printf("   %d) %s x%d = Rp%.0f%n", i, cur.item.name, cur.qty, cur.subtotal());
            cur = cur.next; i++;
        }
        System.out.printf("   Total: Rp%.0f%n", total());
    }
}
