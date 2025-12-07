public class ItemNode {
    public int id;             // id barang unik
    public String name;        // nama barang
    public double price;       // harga per unit
    public int stock;          // stok tersedia
    public ItemNode next;      // pointer linked list

    public ItemNode(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.next = null;
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | Rp%.0f | stok:%d", id, name, price, stock);
    }
}
