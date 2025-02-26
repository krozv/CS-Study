class Node<E> {
    E data;
    Node<E> prev, next;

    public Node(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}

class CustomDoublyLinkedList<E> {
    int size;
    Node<E> head, tail;

    public CustomDoublyLinkedList() {
    }

    public void add(E value) {
        Node<E> node = new Node<>(value);

        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        size++;
    }

    public void addFirst(E value) {
        Node<E> node = new Node<>(value);

        if (head == null) {
            head = tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }

        size++;
    }

    public void insert(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + "인덱스는 없음");
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<E> node = new Node<>(value);
        Node<E> iterator = head;
        int count = 0;

        while (count != index) {
            iterator = iterator.next;
            count++;
        }

        node.prev = iterator.prev;
        node.next = iterator;

        if (iterator.prev != null) {
            iterator.prev.next = node;
        }
        iterator.prev = node;

        size++;
    }

    public E poll() {
        if (tail == null) {
            throw new IndexOutOfBoundsException();
        }

        E data = tail.data;

        if (tail == head) {
            head = tail = null;
        } else {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        }

        size--;
        return data;
    }

    public E pollFirst() {
        if (tail == null) {
            throw new IndexOutOfBoundsException();
        }

        E data = head.data;

        if (tail == head) {
            head = tail = null;
        } else {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        }

        size--;
        return data;
    }

    @Override
    public String toString() {
        String result = "";
        Node<E> iterator = head;

        while (iterator != null) {
            result += iterator.data;
            result += " <=> ";
            iterator = iterator.next;
        }

        return result;
    }
}