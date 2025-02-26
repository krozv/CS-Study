class CustomArrayList<T> {
    private T[] arr;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        arr = (T[]) new Object[10]; // 초기 배열 크기를 10으로 지정
        size = 0;
    }

    public void add(T value) {
        // 배열 길이보다 같거나 크면 리사이징
        if (size >= arr.length) {
            resize(arr.length * 2);
        }

        arr[size++] = value;
    }

    public void remove(int index) {
        checkIndex(index);

        // 요소 삭제 후 뒤에 있는 요소의 개수
        int numberMoved = size - index - 1;
        if (numberMoved > 0) {
            System.arraycopy(arr, index + 1, arr, index, numberMoved);
        }

        arr[--size] = null;
    }

    public void remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                remove(i);
                return;
            }
        }
    }

    public T get(int index) {
        checkIndex(index);
        return arr[index];
    }

    public int indexOf(Object object) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == object) {
                return i;
            }
        }

        return -1;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        System.arraycopy(arr, 0, newArr, 0, size);
        arr = newArr;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(index + "번 인덱스는 범위 밖임");
        }
    }

    public int size() {
        return size;
    }
}