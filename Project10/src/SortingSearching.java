import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingSearching {

    // Bubble sort on list by price ascending or descending
    public static void bubbleSort(List<ItemNode> list, boolean asc) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                double a = list.get(j).price;
                double b = list.get(j + 1).price;
                if ((asc && a > b) || (!asc && a < b)) {
                    ItemNode tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // Selection sort on list by name (lexicographic) ascending or descending
    public static void selectionSort(List<ItemNode> list, boolean asc) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int idx = i;
            for (int j = i + 1; j < n; j++) {
                String a = list.get(j).name.toLowerCase();
                String b = list.get(idx).name.toLowerCase();
                if ((asc && a.compareTo(b) < 0) || (!asc && a.compareTo(b) > 0)) {
                    idx = j;
                }
            }
            ItemNode tmp = list.get(i);
            list.set(i, list.get(idx));
            list.set(idx, tmp);
        }
    }

    // Linear search by name on the inventory list (returns index in list or -1)
    public static int linearSearchByName(List<ItemNode> list, String target) {
        target = target.toLowerCase();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.toLowerCase().equals(target)) return i;
        }
        return -1;
    }

    // Binary search by id on sorted-by-id list (assumes list sorted by id ascending)
    public static int binarySearchById(List<ItemNode> list, int targetId) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            int id = list.get(mid).id;
            if (id == targetId) return mid;
            if (id < targetId) l = mid + 1;
            else r = mid - 1;
        }
        return -1;
    }

    // Helper: sort list by id ascending (for binary search)
    public static void sortById(List<ItemNode> list) {
        list.sort(Comparator.comparingInt(a -> a.id));
    }

    // Helper: print list (for debug/display)
    public static void printList(List<ItemNode> list) {
        for (ItemNode it : list) {
            System.out.println(it.toString());
        }
    }
}
