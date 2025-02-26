class Node<E> {
    E data;
    Node<E> next;

    public Node(E value) {
        data = value;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

class CustomQueue<E> {
    int size;
    Node<E> front, rear;

    public CustomQueue() {
    }

    public void enqueue(E data) {
        Node<E> node = new Node<>(data);

        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }

        size++;
    }

    public Node<E> dequeue() {
        if (front == null) {
            return null;
        }

        Node<E> node = front;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        size--;
        return node;
    }

    @Override
    public String toString() {
        String result = "";

        Node<E> iterator = front;

        while (iterator != null) {
            result += iterator;
            iterator = iterator.next;
        }

        return result;
    }
}