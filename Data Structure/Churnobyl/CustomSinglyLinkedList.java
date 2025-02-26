class Node<E> {
    E data;
    Node<E> next;

    public Node(E data) {
        this.data = data;
    }
}

class CustomSinglyLinkedList<E> {
    int size;
    Node<E> head, tail;

    public CustomSinglyLinkedList() {
    }

    public void add(E value) {
        Node<E> node = new Node<>(value);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }

        size++;
    }

    public void addFirst(E value) {
        Node<E> node = new Node<>(value);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }

        size++;
    }

    public void pop() {
        // 아무것도 없으면 패스
        if (head == null) {
            return;
        }

        // 요소가 하나 밖에 없으면 연결 끊기
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // O(n) 연산
            Node<E> iterator = head;

            // iterator의 next가 가리키는 노드가 tail일 때까지 반복
            while (iterator.next != tail) {
                iterator = iterator.next;
            }

            // 다음 노드에 대한 연결을 끊고
            iterator.next = null;
            // tail을 iterator로 설정
            tail = iterator;
        }

        size--;
    }

    @Override
    public String toString() {
        String result = "";

        Node<E> iterator = head;

        while (iterator != null) {
            result += iterator.data;
            result += " -> ";
            iterator = iterator.next;
        }

        return result;
    }
}