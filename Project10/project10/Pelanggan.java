public class Pelanggan {
    String nama;
    KeranjangBelanja keranjang;
    Pelanggan next = null; 

    public Pelanggan(String nama, KeranjangBelanja keranjang) {
        this.nama = nama;
        this.keranjang = keranjang;
    }
}