public class OrgNode {
    public String name;
    public String position;
    public int tahun;
    public int umur;
    public int level;

    public OrgNode child1;
    public OrgNode child2;
    public OrgNode child3;

    public OrgNode(String name, String position, int tahun, int umur, int level) {
        this.name = name;
        this.position = position;
        this.tahun = tahun;
        this.umur = umur;
        this.level = level;
        this.child1 = null; this.child2 = null; this.child3 = null;
    }

    // add child to the first available slot (1..3)
    public boolean addChild(OrgNode child) {
        if (child1 == null) { child1 = child; return true; }
        if (child2 == null) { child2 = child; return true; }
        if (child3 == null) { child3 = child; return true; }
        return false; // full
    }
}
