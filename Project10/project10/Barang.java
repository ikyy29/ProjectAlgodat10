public class Barang {
    String nama;
    int harga;
    int stok;

    Barang(String nama, int harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String toString() {
        return String.format("%-15s | Rp %-7d | Stok: %d", nama, harga, stok);
    }
}