import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
class HashTable {
    int defaultSize = 1000;
    LinkedList<int[]>[] arr = new LinkedList[defaultSize];

    public HashTable() {
    }

    public void put(int key, int value) {
        int remainder = key % defaultSize;

        if (arr[remainder] == null) {
            arr[remainder] = new LinkedList<>();
        }

        for (int[] pair : arr[remainder]) {
            if (pair[0] == key) {
                pair[1] = value; // 기존 값 업데이트
                return;
            }
        }

        arr[remainder].add(new int[] {key, value});
    }

    public int get(int key) {
        int remainder = key % defaultSize;

        if (arr[remainder] == null) return Integer.MIN_VALUE;

        for (int[] pair : arr[remainder]) {
            if (pair[0] == key) return pair[1];
        }

        return Integer.MIN_VALUE;
    }

    public void remove(int key) {
        int remainder = key % defaultSize;

        if (arr[remainder] == null) return;

        Iterator<int[]> iterator = arr[remainder].iterator();

        while (iterator.hasNext()) {
            int[] pair = iterator.next();
            if (pair[0] == key) {
                iterator.remove();
                return;
            }
        }
    }
}