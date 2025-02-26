import java.util.Comparator;

class CustomHeap<E> {
    private E[] arr;
    private int size;
    private final Comparator<? super E> comparator;

    @SuppressWarnings("unchecked")
    public CustomHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.arr = (E[]) new Object[10]; // 초기 크기 설정
        this.size = 0;
    }

    public void push(E value) {
        if (size >= arr.length) {
            resize(arr.length * 2);
        }

        arr[size] = value;
        bubbleUp(size);
        size++;
    }


    public void pop() {
        if (size == 0) return;
        arr[0] = arr[size - 1];
        size--;
        heapify(0);
    }

    private void heapify(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (left < size && comparator.compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }
        if (right < size && comparator.compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapify(largest);
        }
    }

    private void swap(int indexA, int indexB) {
        E temp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = temp;
    }


    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(arr[index], arr[parentIndex]) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] newArr = (E[]) new Object[capacity];
        System.arraycopy(arr, 0, newArr, 0, size);
        arr = newArr;
    }

    @Override
    public String toString() {
        String result = "[";

        for (int i = 0; i < size; i++) {
            result += arr[i];
            if (i != size - 1) {
                result += ", ";
            }
        }
        result += "]";

        return result;
    }
}