import java.util.Scanner;

public class App {
    // FIX TYPO: Scanner
    static Scanner scanner = new Scanner(System.in);
    static Inventaris gudang = new Inventaris();
    static AntrianKasir kasir = new AntrianKasir();

    public static void main(String[] args) {
        // Seeding Data
        gudang.tambah("Indomie", 3500, 50);
        gudang.tambah("Aqua", 3000, 100);
        gudang.tambah("Roti", 12000, 20);
        gudang.tambah("Susu", 6000, 30);

        boolean running = true;
        while (running) {
            System.out.println("\n=== SISTEM MINIMARKET JAVA ===");
            System.out.println("1. Login Manager (Tree)");
            System.out.println("2. Login Pembeli (Double LL)");
            System.out.println("3. Login Kasir (Queue)");
            System.out.println("4. Keluar");
            System.out.print("Pilih Menu: ");
            
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                scanner.nextLine(); 

                switch (menu) {
                    case 1: menuManager(); break;
                    case 2: menuPembeli(); break;
                    case 3: menuKasir(); break;
                    case 4: running = false; break;
                    default: System.out.println("Menu salah.");
                }
            } else {
                System.out.println("Input harus angka.");
                scanner.next();
            }
        }
    }

    static void menuManager() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MANAGER MENU (TREE) ---");
            System.out.println("1. Lihat Semua Barang");
            System.out.println("2. Tambah Barang");
            System.out.println("3. Edit Barang");
            System.out.println("4. Hapus Barang");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            int p = scanner.nextInt();
            scanner.nextLine();

            switch (p) {
                case 1:
                    System.out.println("\nDAFTAR BARANG (Sorted by Name):");
                    gudang.tampilkanSemua();
                    break;
                case 2:
                    System.out.print("Nama: ");
                    String nama = scanner.nextLine();
                    System.out.print("Harga: ");
                    int harga = scanner.nextInt();
                    System.out.print("Stok: ");
                    int stok = scanner.nextInt();
                    gudang.tambah(nama, harga, stok);
                    break;
                case 3:
                    System.out.print("Nama Barang yg diedit: ");
                    String editNama = scanner.nextLine();
                    Barang b = gudang.cari(editNama);
                    if (b != null) {
                        System.out.print("Harga Baru: ");
                        b.harga = scanner.nextInt();
                        System.out.print("Stok Baru: ");
                        b.stok = scanner.nextInt();
                        System.out.println("Data berhasil diupdate.");
                    } else System.out.println("Barang tidak ditemukan.");
                    break;
                case 4:
                    System.out.print("Nama Barang yg dihapus: ");
                    String hapusNama = scanner.nextLine();
                    gudang.hapus(hapusNama);
                    System.out.println("Barang dihapus.");
                    break;
                case 5: back = true; break;
            }
        }
    }

    static void menuPembeli() {
        System.out.print("\nMasukkan Nama Anda: ");
        String namaPembeli = scanner.nextLine();
        KeranjangBelanja keranjang = new KeranjangBelanja();
        
        boolean belanja = true;
        while (belanja) {
            System.out.println("\n--- Halo " + namaPembeli + " ---");
            System.out.println("1. Lihat Barang");
            System.out.println("2. Cari & Beli (Search Tree)");
            System.out.println("3. Cek Keranjang (Total Harga)");
            System.out.println("4. Hapus Item dari Keranjang"); // MENU BARU
            System.out.println("5. Checkout / Selesai");
            System.out.print("Pilih: ");
            int p = scanner.nextInt();
            scanner.nextLine();

            switch (p) {
                case 1: gudang.tampilkanSemua(); break;
                case 2:
                    System.out.print("Cari nama barang: ");
                    String cari = scanner.nextLine();
                    Barang b = gudang.cari(cari);
                    if (b != null) {
                        System.out.println("Ditemukan: " + b.nama + " | Harga: " + b.harga + " | Stok: " + b.stok);
                        System.out.print("Beli berapa? ");
                        int qty = scanner.nextInt();
                        if (qty <= b.stok && qty > 0) {
                            keranjang.tambah(b, qty);
                            b.stok -= qty; // Stok berkurang sementara
                            System.out.println("Masuk keranjang!");
                        } else {
                            System.out.println("Stok tidak cukup atau input salah!");
                        }
                    } else {
                        System.out.println("Barang tidak ada.");
                    }
                    break;
                case 3:
                    if (keranjang.isEmpty()) System.out.println("Keranjang kosong.");
                    else keranjang.hitungTotal();
                    break;
                case 4:
                    // IMPLEMENTASI MENU HAPUS
                    if (keranjang.isEmpty()) {
                        System.out.println("Keranjang kosong.");
                    } else {
                        keranjang.hitungTotal();
                        System.out.print("Ketik nama barang yang mau dihapus: ");
                        String namaHapus = scanner.nextLine();
                        keranjang.hapus(namaHapus, gudang);
                    }
                    break;
                case 5:
                    if (!keranjang.isEmpty()) {
                        System.out.print("Konfirmasi Checkout? (y/n): ");
                        String confirm = scanner.next();
                        if (confirm.equalsIgnoreCase("y")) {
                            kasir.enqueue(namaPembeli, keranjang);
                            belanja = false;
                        } else {
                            // LOGIKA PENGEMBALIAN STOK (Supaya tidak hilang)
                            keranjang.kembalikanSemuaStok();
                            System.out.println("Dibatalkan. Stok dikembalikan ke gudang.");
                            belanja = false;
                        }
                    } else {
                        System.out.println("Anda belum belanja.");
                        belanja = false;
                    }
                    break;
            }
        }
    }

    static void menuKasir() {
        System.out.println("\n--- MENU KASIR (QUEUE) ---");
        if (kasir.isEmpty()) {
            System.out.println("Belum ada antrian.");
            return;
        }
        System.out.println("1. Proses Antrian Terdepan");
        System.out.println("2. Kembali");
        System.out.print("Pilih: ");
        int p = scanner.nextInt();
        scanner.nextLine();

        if (p == 1) {
            Pelanggan current = kasir.dequeue();
            if (current != null) {
                System.out.println("\n==========================");
                System.out.println("STRUK PEMBELIAN: " + current.nama);
                System.out.println("==========================");
                int total = current.keranjang.hitungTotal();
                System.out.println("--------------------------");
                System.out.println("TOTAL BAYAR: Rp " + total);
                System.out.println("==========================\n");
            }
        }
    }
}