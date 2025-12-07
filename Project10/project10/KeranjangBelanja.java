public class KeranjangBelanja {
    Keranjang head, tail;

    public void tambah(Barang b, int qty) {
        Keranjang nodeBaru = new Keranjang(b, qty);
        if (head == null) {
            head = tail = nodeBaru;
        } else {
            tail.next = nodeBaru;
            nodeBaru.prev = tail;
            tail = nodeBaru;
        }
    }

    // [FITUR TAMBAHAN] Hapus barang & kembalikan stok
    public void hapus(String nama, Inventaris gudang) {
        Keranjang current = head;
        while (current != null) {
            if (current.barang.nama.equalsIgnoreCase(nama)) {
                // Balikin stok ke gudang dulu
                current.barang.stok += current.qty;

                // Logika Hapus Node DLL
                if (current == head && current == tail) {
                    head = tail = null;
                } else if (current == head) {
                    head = head.next;
                    head.prev = null;
                } else if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                System.out.println(">> " + nama + " dihapus dari keranjang. Stok dikembalikan.");
                return;
            }
            current = current.next;
        }
        System.out.println("Barang tidak ditemukan di keranjang.");
    }

    // Method bantu untuk mengembalikan semua stok jika batal belanja
    public void kembalikanSemuaStok() {
        Keranjang current = head;
        while(current != null) {
            current.barang.stok += current.qty;
            current = current.next;
        }
        head = tail = null; // Kosongkan list
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int hitungTotal() {
        int total = 0;
        Keranjang current = head;
        System.out.println("   --- Rincian Belanja ---");
        while (current != null) {
            int subtotal = current.barang.harga * current.qty;
            System.out.println("   - " + current.barang.nama + " (" + current.qty + "x) = Rp " + subtotal);
            total += subtotal;
            current = current.next;
        }
        return total;
    }
}