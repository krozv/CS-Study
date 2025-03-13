import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Node {
    int data;
    Node prev, next;

    public Node(int data) {
        this.data = data;
    }
}

class GravityQueue {
    int ballCount, barrierCount, direction;
    Node front, rear;

    public GravityQueue() {
    }

    public void push(String bw) {
        if (bw.equals("b")) {
            Node node = new Node(1);

            if (rear == null) {
                if (direction == 1 || direction == 3) return;

                front = rear = node;
            } else {
                if (direction == 3) { // 아래가 뒤
                    return;
                } else { // 나머지 경우
                    if (rear.data > 0) {
                        rear.data += 1;
                    } else {
                        rear.next = node;
                        node.prev = rear;
                        rear = node;
                    }
                }
            }

            ballCount++;

        } else {
            Node node = new Node(0);

            if (rear == null) {
                front = rear = node;
            } else {
                rear.next = node;
                node.prev = rear;
                rear = node;
            }

            barrierCount++;
        }
    }

    public void pop() {
        if (front == null) return;

        if (front.data == 1) {
            removeFirst();
            ballCount -= 1;
        } else if (front.data > 1) {
            front.data -= 1;
            ballCount -= 1;
        } else {
            removeFirst();
            barrierCount -= 1;

            if (front != null && direction == 1 && front.data > 0) {
                ballCount -= front.data;
                removeFirst();
            }
        }
    }

    public void removeFirst() {
        if (front == null) return;

        if (front.next != null) {
            front = front.next;
            if (front.prev != null) {
                front.prev.next = null;
                front.prev = null;
            }
        } else {
            front = null;
            rear = null;
        }
    }

    public void removeLast() {
        if (rear == null) return;

        if (rear.prev != null) {
            rear = rear.prev;
            if (rear.next != null) {
                rear.next.prev = null;
                rear.next =null;
            }
        } else {
            front = null;
            rear = null;
        }
    }

    public void rotate(String lr) {
        if (lr.equals("l")) direction = (3 + direction) % 4;
        else direction = (direction + 1) % 4;

        if (front != null && direction == 1 && front.data > 0) {
            ballCount -= front.data;
            removeFirst();
        } else if (rear != null && direction == 3 && rear.data > 0) {
            ballCount -= rear.data;
            removeLast();
        }
    }

    public int count(String bw) {
        if (bw.equals("b")) return ballCount;
        else return barrierCount;
    }
}

public class Main {
    static int Q;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());

        GravityQueue gq = new GravityQueue();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String query = st.nextToken();

            switch (query) {
                case "push":
                    gq.push(st.nextToken());
                    break;
                case "count":
                    sb.append(gq.count(st.nextToken())).append("\n");
                    break;
                case "pop":
                    gq.pop();
                    break;
                case "rotate":
                    gq.rotate(st.nextToken());
                    break;
            }
        }

        System.out.println(sb);
    }
}
