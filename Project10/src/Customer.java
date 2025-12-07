public class Customer {
    public String name;
    public int queueNo;
    public OrderStack order; // nested stack of items
    public Customer next;

    public Customer(String name, int queueNo) {
        this.name = name;
        this.queueNo = queueNo;
        this.order = new OrderStack();
        this.next = null;
    }

    public void printSummary() {
        System.out.println("Customer #" + queueNo + " - " + name);
        order.display();
    }
}
