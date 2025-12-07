public class OrderItem {
    public ItemNode item;   // link to inventory item (snapshot)
    public int qty;
    public OrderItem next;

    public OrderItem(ItemNode item, int qty) {
        this.item = item;
        this.qty = qty;
        this.next = null;
    }

    public double subtotal() {
        return item.price * qty;
    }
}
