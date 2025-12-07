import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Inventory inv = new Inventory();
        // seed inventory with sample items
        inv.insert("Nasi Goreng", 15000, 20);
        inv.insert("Mie Goreng", 12000, 15);
        inv.insert("Es Teh", 5000, 50);
        inv.insert("Kopi Tubruk", 8000, 30);

        CustomerQueue queue = new CustomerQueue();
        OrgTree org = new OrgTree();
        org.buildDefault();

        System.out.println("=== SISTEM KANTIN (Demo) ===");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nPilih peran: 1=Manager 2=Pegawai 3=Tamu/Antrian 4=Admin Org 0=Exit");
            System.out.print("> ");
            String role = sc.nextLine().trim();
            switch (role) {
                case "1": // Manager: CRUD inventory + view reports + sorting
                    managerMenu(sc, inv);
                    break;
                case "2": // Pegawai: serve customers, create orders
                    employeeMenu(sc, inv, queue);
                    break;
                case "3": // Tamu: join queue
                    System.out.print("Nama customer: ");
                    String cname = sc.nextLine().trim();
                    Customer c = queue.enqueue(cname);
                    System.out.println("Terdaftar sebagai antrian ke #" + c.queueNo);
                    break;
                case "4":
                    adminOrgMenu(sc, org);
                    break;
                case "0":
                    exit = true; break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        System.out.println("Sistem dimatikan.");
        sc.close();
    }

    // Manager menu: CRUD inventory + sorting
    private static void managerMenu(Scanner sc, Inventory inv) {
        while (true) {
            System.out.println("\n-- MANAGER MENU --");
            System.out.println("1) Tampilkan inventory");
            System.out.println("2) Tambah item");
            System.out.println("3) Update item (by id)");
            System.out.println("4) Hapus item (by id)");
            System.out.println("5) Sort (Bubble by price) asc/desc");
            System.out.println("6) Sort (Selection by name) asc/desc");
            System.out.println("7) Search linear by name");
            System.out.println("8) Search binary by id (requires sort by id)");
            System.out.println("0) Kembali");
            System.out.print("> ");
            String m = sc.nextLine().trim();
            if (m.equals("0")) return;
            try {
                switch (m) {
                    case "1":
                        inv.display(); break;
                    case "2":
                        System.out.print("Nama: "); String n = sc.nextLine();
                        System.out.print("Harga: "); double p = Double.parseDouble(sc.nextLine());
                        System.out.print("Stok: "); int s = Integer.parseInt(sc.nextLine());
                        ItemNode added = inv.insert(n, p, s);
                        System.out.println("Ditambahkan: " + added);
                        break;
                    case "3":
                        System.out.print("ID update: "); int uid = Integer.parseInt(sc.nextLine());
                        ItemNode f = inv.findById(uid);
                        if (f == null) { System.out.println("ID tidak ditemukan."); break; }
                        System.out.print("Nama baru ("+f.name+"): "); String nn = sc.nextLine();
                        System.out.print("Harga baru ("+f.price+"): "); double pp = Double.parseDouble(sc.nextLine());
                        System.out.print("Stok baru ("+f.stock+"): "); int ss = Integer.parseInt(sc.nextLine());
                        inv.update(uid, nn, pp, ss);
                        System.out.println("Diupdate."); break;
                    case "4":
                        System.out.print("ID hapus: "); int did = Integer.parseInt(sc.nextLine());
                        boolean ok = inv.delete(did);
                        System.out.println(ok ? "Terhapus." : "ID tidak ditemukan.");
                        break;
                    case "5":
                        List<ItemNode> list1 = inv.toList();
                        System.out.print("Asc? (y/n): "); boolean asc = sc.nextLine().trim().equalsIgnoreCase("y");
                        long st = System.nanoTime();
                        SortingSearching.bubbleSort(list1, asc);
                        long en = System.nanoTime();
                        inv.rebuildFromList(list1);
                        System.out.println("Selesai. Waktu: " + ((en - st)/1_000_000.0) + " ms");
                        break;
                    case "6":
                        List<ItemNode> list2 = inv.toList();
                        System.out.print("Asc by name? (y/n): "); boolean ascn = sc.nextLine().trim().equalsIgnoreCase("y");
                        long st2 = System.nanoTime();
                        SortingSearching.selectionSort(list2, ascn);
                        long en2 = System.nanoTime();
                        inv.rebuildFromList(list2);
                        System.out.println("Selesai. Waktu: " + ((en2 - st2)/1_000_000.0) + " ms");
                        break;
                    case "7":
                        System.out.print("Cari nama: "); String tn = sc.nextLine();
                        List<ItemNode> lsearch = inv.toList();
                        int idx = SortingSearching.linearSearchByName(lsearch, tn);
                        if (idx >= 0) System.out.println("Ditemukan: " + lsearch.get(idx));
                        else System.out.println("Tidak ditemukan.");
                        break;
                    case "8":
                        List<ItemNode> lb = inv.toList();
                        SortingSearching.sortById(lb);
                        System.out.print("Cari ID: "); int tid = Integer.parseInt(sc.nextLine());
                        int pos = SortingSearching.binarySearchById(lb, tid);
                        System.out.println(pos >= 0 ? "Found: " + lb.get(pos) : "Not found");
                        break;
                    default:
                        System.out.println("Pilihan invalid"); break;
                }
            } catch (Exception ex) {
                System.out.println("Error input: " + ex.getMessage());
            }
        }
    }

    // Employee menu: serve customer queue, record order
    private static void employeeMenu(Scanner sc, Inventory inv, CustomerQueue queue) {
        while (true) {
            System.out.println("\n-- PEGAWAI MENU --");
            System.out.println("1) Lihat antrian");
            System.out.println("2) Panggil customer (dequeue) dan buat order");
            System.out.println("3) Tampilkan inventory");
            System.out.println("0) Kembali");
            System.out.print("> ");
            String c = sc.nextLine().trim();
            if (c.equals("0")) return;
            switch (c) {
                case "1":
                    queue.display(); break;
                case "2":
                    if (queue.isEmpty()) { System.out.println("Antrian kosong."); break; }
                    Customer cust = queue.dequeue();
                    System.out.println("Serving " + cust.name + " (#" + cust.queueNo + ")");
                    boolean ordering = true;
                    while (ordering) {
                        inv.display();
                        System.out.print("Masukkan item id (0 untuk selesai): ");
                        int id = Integer.parseInt(sc.nextLine());
                        if (id == 0) break;
                        ItemNode it = inv.findById(id);
                        if (it == null) { System.out.println("ID tidak ditemukan."); continue; }
                        System.out.print("Qty: "); int q = Integer.parseInt(sc.nextLine());
                        if (q > it.stock) { System.out.println("Stok tidak cukup (stok: " + it.stock + ")"); continue; }
                        cust.order.push(it, q);
                        it.stock -= q; // reduce inventory
                        System.out.println("Ditambahkan ke order.");
                    }
                    System.out.println("Order selesai. Ringkasan:");
                    cust.printSummary();
                    System.out.println("Simpan pembayaran? (y/n)"); // simplified
                    String pay = sc.nextLine().trim();
                    if (pay.equalsIgnoreCase("y")) {
                        System.out.printf("Customer %s membayar Rp%.0f. Terima kasih.%n", cust.name, cust.order.total());
                    } else {
                        System.out.println("Order dibatalkan. Mengembalikan stok...");
                        // restore stok by popping all items
                        OrderItem oi;
                        while ((oi = cust.order.pop()) != null) {
                            oi.item.stock += oi.qty;
                        }
                    }
                    break;
                case "3":
                    inv.display(); break;
                default:
                    System.out.println("Pilihan invalid"); break;
            }
        }
    }

    // Admin org menu
    private static void adminOrgMenu(Scanner sc, OrgTree org) {
        while (true) {
            System.out.println("\n-- ORG ADMIN MENU --");
            System.out.println("1) Tampilkan struktur (preorder)");
            System.out.println("2) Tampilkan struktur (level-order)");
            System.out.println("3) Tambah staff ke manager (auto slot)");
            System.out.println("4) Swap member");
            System.out.println("5) Print summary");
            System.out.println("0) Kembali");
            System.out.print("> ");
            String a = sc.nextLine().trim();
            if (a.equals("0")) return;
            switch (a) {
                case "1":
                    org.preOrder(org.root); break;
                case "2":
                    org.levelOrder(); break;
                case "3":
                    System.out.print("Nama staff: "); String s = sc.nextLine();
                    OrgNode newN = new OrgNode(s, "Staff", 2025, 20, 3);
                    boolean added = org.root.addChild(newN);
                    System.out.println(added ? "Staff ditambahkan." : "Slot penuh pada manager.");
                    break;
                case "4":
                    System.out.print("Member A: "); String ma = sc.nextLine();
                    System.out.print("Member B: "); String mb = sc.nextLine();
                    boolean swapped = org.swapMember(ma, mb);
                    System.out.println(swapped ? "Swap berhasil." : "Salah satu anggota tidak ditemukan.");
                    break;
                case "5":
                    org.printSummary(); break;
                default:
                    System.out.println("Pilihan invalid"); break;
            }
        }
    }
}
