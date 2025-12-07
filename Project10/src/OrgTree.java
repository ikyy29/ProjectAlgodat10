public class OrgTree {
    public OrgNode root;

    public OrgTree() { root = null; }

    // build sample canteen structure (manager + staff)
    public void buildDefault() {
        root = new OrgNode("Manager A", "Manager", 2025, 35, 1);
        OrgNode staff1 = new OrgNode("Budi", "Kasir", 2023, 22, 2);
        OrgNode staff2 = new OrgNode("Sinta", "Chef", 2022, 28, 2);
        OrgNode staff3 = new OrgNode("Dewi", "Pelayan", 2024, 24, 2);
        root.addChild(staff1);
        root.addChild(staff2);
        root.addChild(staff3);
    }

    // Preorder traversal
    public void preOrder(OrgNode n) {
        if (n == null) return;
        System.out.println(n.position + " - " + n.name);
        preOrder(n.child1); preOrder(n.child2); preOrder(n.child3);
    }

    // Postorder traversal
    public void postOrder(OrgNode n) {
        if (n == null) return;
        postOrder(n.child1); postOrder(n.child2); postOrder(n.child3);
        System.out.println(n.position + " - " + n.name);
    }

    // Level order (simple queue of OrgNode using CustomerQueue-like pattern)
    private static class QNode { OrgNode data; QNode next; QNode(OrgNode d){data=d;} }
    public void levelOrder() {
        if (root == null) return;
        QNode head = new QNode(root), tail = head;
        while (head != null) {
            OrgNode cur = head.data;
            System.out.println(cur.position + " - " + cur.name);
            if (cur.child1 != null) { tail.next = new QNode(cur.child1); tail = tail.next; }
            if (cur.child2 != null) { tail.next = new QNode(cur.child2); tail = tail.next; }
            if (cur.child3 != null) { tail.next = new QNode(cur.child3); tail = tail.next; }
            head = head.next;
        }
    }

    // Search by name (DFS)
    public OrgNode search(OrgNode n, String name) {
        if (n == null) return null;
        if (n.name.equalsIgnoreCase(name)) return n;
        OrgNode r = search(n.child1, name); if (r != null) return r;
        r = search(n.child2, name); if (r != null) return r;
        return search(n.child3, name);
    }

    // Count members
    public int countMembers(OrgNode n) {
        if (n == null) return 0;
        return 1 + countMembers(n.child1) + countMembers(n.child2) + countMembers(n.child3);
    }

    // Height
    public int height(OrgNode n) {
        if (n == null) return -1;
        int h1 = height(n.child1), h2 = height(n.child2), h3 = height(n.child3);
        int max = h1; if (h2 > max) max = h2; if (h3 > max) max = h3;
        return 1 + max;
    }

    // swap two members by swapping name & position (simple)
    public boolean swapMember(String a, String b) {
        OrgNode n1 = search(root, a); OrgNode n2 = search(root, b);
        if (n1 == null || n2 == null) return false;
        String tn = n1.name; n1.name = n2.name; n2.name = tn;
        String tp = n1.position; n1.position = n2.position; n2.position = tp;
        return true;
    }

    // print summary
    public void printSummary() {
        System.out.println("=== ORG SUMMARY ===");
        System.out.println("Total members: " + countMembers(root));
        System.out.println("Height: " + height(root));
        System.out.println("Members (level-order):");
        levelOrder();
    }
}
