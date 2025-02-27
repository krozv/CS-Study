import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj28078 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int queryCount = Integer.parseInt(br.readLine());

        GravityQueue queue = new GravityQueue();

        String query;
        for (int i = 0; i < queryCount; i++) {
            st = new StringTokenizer(br.readLine());
            query = st.nextToken();

            if (query.equals("push")) {
                queue.push(st.nextToken());
            } else if (query.equals("pop")) {
                queue.pop();
            } else if (query.equals("rotate")) {
                queue.rotate(st.nextToken());
            } else {
                sb.append(queue.count(st.nextToken())).append("\n");
            }

        }

        System.out.print(sb);
    }

    private static class GravityQueue {
        int ballCount;
        int guardCount;
        int direction;
        Ball head;
        Ball tail;

        public GravityQueue() {
            ballCount = 0;
            guardCount = 0;
            direction = 0;
            head = null;
            tail = null;
        }

        void push(String type) {
            if (type.equals("b")) {
                if (tail == null) {
                    Ball ball = new Ball();
                    head = ball;
                    tail = ball;
                } else {
                    if (tail.count == -1) {
                        Ball ball = new Ball();
                        tail.next = ball;
                        ball.prev = tail;
                        tail = ball;
                    } else {
                        tail.count++;
                    }
                }
                ballCount++;
            } else {
                Ball ball = new Ball();
                ball.count = -1;
                if (tail == null) {
                    head = ball;
                    tail = ball;
                } else {
                    tail.next = ball;
                    ball.prev = tail;
                    tail = ball;
                }
                guardCount++;
            }

            fall();
        }

        void pop() {
            if (head != null) {
                if (head.count == -1) {
                    guardCount--;
                    if (head.next == null) {
                        head = null;
                        tail = null;
                    } else {
                        head.next.prev = null;
                        head = head.next;
                    }
                } else {
                    ballCount--;
                    if (head.count == 1) {
                        if (head.next == null) {
                            head = null;
                            tail = null;
                        } else {
                            head.next.prev = null;
                            head = head.next;
                        }
                    } else {
                        head.count--;
                    }
                }

                fall();
            }
        }

        void rotate(String dir) {
            if (dir.equals("l")) {
                direction = (direction + 4 - 1) % 4;
            } else {
                direction = (direction + 4 + 1) % 4;
            }

            fall();
        }

        int count(String type) {
            if (type.equals("b")) {
                return ballCount;
            } else {
                return guardCount;
            }
        }

        void fall() {
            if (direction == 1 && head != null && head.count != -1) {
                ballCount -= head.count;

                if (head.next == null) {
                    head = null;
                    tail = null;
                } else {
                    head.next.prev = null;
                    head = head.next;
                }

            } else if (direction == 3 && tail != null && tail.count != -1) {
                ballCount -= tail.count;

                if (tail.prev == null) {
                    head = null;
                    tail = null;
                } else {
                    tail.prev.next = null;
                    tail = tail.prev;
                }
            }
        }

        private static class Ball {
            int count;
            Ball prev;
            Ball next;

            public Ball() {
                count = 1;
            }
        }
    }
}
