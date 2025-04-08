import java.util.PriorityQueue;

public class boj32388 {

    // 도착지
    private static int goalX, goalY;

    // 최소 스트레스 저장
    private static double[] minStress;

    // 로고 정보
    private static int logoCount;
    private static Logo[] logoList;

    // 시작 지점
    private static final int START_X = 0;
    private static final int START_Y = 0;

    // 로고의 위치
    private static class Logo {
        int x;
        int y;

        public Logo(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 현재 로고와 스트레스를 저장하는 클래스
    private static class Node implements Comparable<Node> {
        int logo;
        double stress;

        public Node(int logo, double stress) {
            this.logo = logo;
            this.stress = stress;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(stress, o.stress);
        }
    }

    public static void main(String[] args) throws Exception {
        goalX = read();
        goalY = read();

        // 로고 개수 받아서 로고 저장
        logoCount = read();
        logoList = new Logo[logoCount];
        for (int i = 0; i < logoCount; i++) {
            logoList[i] = new Logo(read(), read());
        }

        minStress = new double[logoCount];

        System.out.print(dijkstra());
    }

    private static double dijkstra() {
        // 시작 지점에서 그냥 일직선으로 가는 경우
        double min = getStress(START_X, START_Y, goalX, goalY);

        // 시작 지점에서 각 로고까지 스트레스 계산 후 큐에 넣음
        double stress;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < logoCount; i++) {
            stress = getStress(START_X, START_Y, logoList[i].x, logoList[i].y) - 1;
            if (stress < 0) {
                stress = 0;
            }
            pq.add(new Node(i, stress));
            minStress[i] = stress;
        }

        // 평범한 다익스트라
        Node now;
        boolean[] visited = new boolean[logoCount];
        while (!pq.isEmpty()) {
            now = pq.poll();
            if (!visited[now.logo]) {
                visited[now.logo] = true;

                // 현재 로고에서 도착지까지 거리 계산
                stress = Math.max(getStress(logoList[now.logo].x, logoList[now.logo].y, goalX, goalY) - 1, 0)
                        + now.stress;
                if (min > stress) {
                    min = stress;
                }

                // 다른 로고로 가는 길 순회
                for (int i = 0; i < logoCount; i++) {
                    if (i != now.logo) {
                        stress = Math.max(getStress(logoList[now.logo].x, logoList[now.logo].y, logoList[i].x, logoList[i].y) - 2, 0)
                                + now.stress;

                        // 더 적은 스트레스로 가능하면 갱신 후 큐에 저장
                        if (minStress[i] > stress) {
                            minStress[i] = stress;
                            pq.add(new Node(i, stress));
                        }
                    }
                }
            }

        }

        return min;
    }

    private static double getStress(int startX, int startY, int endX, int endY) {
        return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    }

    private static int read() throws Exception {
        int c, n = System.in.read();
        boolean negative = false;
        if (n == '-') {
            negative = true;
            n = System.in.read() & 15;
        } else {
            n &= 15;
        }
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return negative ? -n : n;
    }
}
