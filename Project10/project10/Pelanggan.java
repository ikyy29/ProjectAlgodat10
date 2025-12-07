public class Pelanggan {
    String nama;
    KeranjangBelanja keranjang;
    Pelanggan next; 

    public Pelanggan(String nama, KeranjangBelanja keranjang) {
        this.nama = nama;
        this.keranjang = keranjang;
    }
}