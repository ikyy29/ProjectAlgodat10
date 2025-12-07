public class Inventaris {
    Tree root;

    public void tambah(String nama, int harga, int stok) {
        root = tambahRekursif(root, new Barang(nama, harga, stok));
    }

    private Tree tambahRekursif(Tree current, Barang b) {
        if (current == null) return new Tree(b);
        
        if (b.nama.compareToIgnoreCase(current.data.nama) < 0) {
            current.left = tambahRekursif(current.left, b);
        } else if (b.nama.compareToIgnoreCase(current.data.nama) > 0) {
            current.right = tambahRekursif(current.right, b);
        } else {
            current.data.stok += b.stok;
        }
        return current;
    }

    public Barang cari(String nama) {
        return cariRekursif(root, nama);
    }

    private Barang cariRekursif(Tree current, String nama) {
        if (current == null) return null;
        if (nama.equalsIgnoreCase(current.data.nama)) return current.data;

        if (nama.compareToIgnoreCase(current.data.nama) < 0) {
            return cariRekursif(current.left, nama);
        } else {
            return cariRekursif(current.right, nama);
        }
    }

    public void hapus(String nama) {
        root = hapusRekursif(root, nama);
    }

    private Tree hapusRekursif(Tree current, String nama) {
        if (current == null) return null;

        if (nama.compareToIgnoreCase(current.data.nama) < 0) {
            current.left = hapusRekursif(current.left, nama);
        } else if (nama.compareToIgnoreCase(current.data.nama) > 0) {
            current.right = hapusRekursif(current.right, nama);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            Barang terkecil = cariMin(current.right);
            current.data = terkecil;
            current.right = hapusRekursif(current.right, terkecil.nama);
        }
        return current;
    }

    private Barang cariMin(Tree root) {
        Barang min = root.data;
        while (root.left != null) {
            min = root.left.data;
            root = root.left;
        }
        return min;
    }

    public void tampilkanSemua() {
        inOrder(root);
    }

    private void inOrder(Tree node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.data);
            inOrder(node.right);
        }
    }
}