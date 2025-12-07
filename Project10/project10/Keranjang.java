public class Keranjang {
    Barang barang;
    int qty;
    Keranjang next;
    Keranjang prev;

    Keranjang(Barang barang, int qty) {
        this.barang = barang;
        this.qty = qty;
    }
}