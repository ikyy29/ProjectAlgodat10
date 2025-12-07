import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private ItemNode head;
    private int nextId = 1; // auto-increment id

    public Inventory() {
        head = null;
    }

    // Insert new item at end (create id automatically)
    public ItemNode insert(String name, double price, int stock) {
        ItemNode node = new ItemNode(nextId++, name, price, stock);
        if (head == null) {
            head = node;
            return node;
        }
        ItemNode cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = node;
        return node;
    }

    // Update item (by id)
    public boolean update(int id, String name, double price, int stock) {
        ItemNode t = findById(id);
        if (t == null) return false;
        t.name = name;
        t.price = price;
        t.stock = stock;
        return true;
    }

    // Delete by id
    public boolean delete(int id) {
        if (head == null) return false;
        if (head.id == id) {
            head = head.next;
            return true;
        }
        ItemNode prev = head;
        ItemNode cur = head.next;
        while (cur != null) {
            if (cur.id == id) {
                prev.next = cur.next;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    // Find by id
    public ItemNode findById(int id) {
        ItemNode cur = head;
        while (cur != null) {
            if (cur.id == id) return cur;
            cur = cur.next;
        }
        return null;
    }

    // Find by name (first match, case-insensitive)
    public ItemNode findByName(String name) {
        ItemNode cur = head;
        while (cur != null) {
            if (cur.name.equalsIgnoreCase(name)) return cur;
            cur = cur.next;
        }
        return null;
    }

    // Display inventory (normal order)
    public void display() {
        System.out.println("=== INVENTORY ===");
        if (head == null) {
            System.out.println("(kosong)");
            return;
        }
        ItemNode cur = head;
        while (cur != null) {
            System.out.println(cur.toString());
            cur = cur.next;
        }
    }

    // Convert linked list to ArrayList<ItemNode> (shallow copies of nodes)
    public List<ItemNode> toList() {
        List<ItemNode> out = new ArrayList<>();
        ItemNode cur = head;
        while (cur != null) {
            out.add(cur);
            cur = cur.next;
        }
        return out;
    }

    // Rebuild linked list from list (used after sorting)
    public void rebuildFromList(List<ItemNode> list) {
        head = null;
        ItemNode prev = null;
        for (ItemNode src : list) {
            // create new node object to avoid linking old next pointers
            ItemNode n = new ItemNode(src.id, src.name, src.price, src.stock);
            if (head == null) head = n;
            else prev.next = n;
            prev = n;
        }
    }

    // Count nodes
    public int size() {
        int c = 0;
        ItemNode cur = head;
        while (cur != null) { c++; cur = cur.next; }
        return c;
    }
}
