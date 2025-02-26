class CustomStack<E> {
    E[] arr;
    int size;

    @SuppressWarnings("unchecked")
    public CustomStack() {
        arr = (E[]) new Object[10];
    }

    public void push(E data) {
        if (size >= arr.length) {
            resize(arr.length * 2);
        }

        arr[size++] = data;
    }

    public void pop() {
        size--;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] newArr = (E[]) new Object[capacity];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < size; i++) {
            result += arr[i];
            result += " -> ";
        }

        return result;
    }
}