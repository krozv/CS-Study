import java.util.*;

/**
 * 다 풀고나니 간단한 다익스트라지만, 풀이에 확신을 가지기 어려운 문제
 * 1. 무빙워크 방향대로 가는 경우, 무조건 전원을 키는것이 이득
 * 2. 최단거리를 갱신하면 방금 사용한 무빙워크의 상태가 이후 변경될 일이 없음
 */
public class boj31723 {

    private static int buildCount, roadCount;

    private static long[] distance;
    private static boolean[] isOn;

    private static List<Road>[] graph;

    // 다익스트라용 클래스
    // {to} 까지 {time}만큼 걸려서 도착
    private static class Node implements Comparable<Node> {
        int to;
        long time;

        public Node(int to, long time) {
            this.to = to;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(time, o.time);
        }
    }

    // 무빙워크 정보를 담은 클래스
    private static class Road {
        int idx;
        int to;
        long time;
        boolean isOn;

        public Road(int idx, int to, long time, boolean isOn) {
            this.idx = idx;
            this.to = to;
            this.time = time;
            this.isOn = isOn;
        }
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();

        buildCount = read();
        roadCount = read();

        // 최단거리 초기화
        distance = new long[buildCount];
        for (int i = 1; i < buildCount; i++) {
            distance[i] = Long.MAX_VALUE;
        }

        isOn = new boolean[roadCount];

        graph = new List[buildCount];
        for (int i = 0; i < buildCount; i++) {
            graph[i] = new ArrayList<>();
        }

        // 무빙워크 정보 저장
        int from, to;
        long time;
        for (int i = 0; i < roadCount; i++) {
            from = read() - 1;
            to = read() - 1;
            time = read();

            graph[from].add(new Road(i, to, time, true));
            graph[to].add(new Road(i, from, time * 2L, false));
        }

        sb.append(dijkstra()).append("\n");
        for (int i = 0; i < roadCount; i++) {
            sb.append(isOn[i] ? "1 " : "0 ");
        }

        System.out.println(sb);
    }

    // 다익스트라로 최단거리 갱신 후 거리 총합을 반환함
    private static long dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0L));

        boolean[] visited = new boolean[buildCount];

        int length;
        Node now;
        Road next;
        while (!pq.isEmpty()) {
            now = pq.poll();
            if (!visited[now.to]) {
                visited[now.to] = true;

                // 현재 노드에서 연결된 무빙워크 순회
                length = graph[now.to].size();
                for (int i = 0; i < length; i++) {
                    next = graph[now.to].get(i);

                    if (distance[next.to] > now.time + next.time) {
                        distance[next.to] = now.time + next.time;
                        isOn[next.idx] = next.isOn;

                        pq.add(new Node(next.to, now.time + next.time));
                    }
                }
            }

        }

        // 모든 노드까지의 거리 총합 반환
        long sum = 0;
        for (int i = 0; i < buildCount; i++) {
            sum += distance[i];
        }
        return sum;
    }

    private static int read() throws Exception {
        int c, n = System.in.read() & 15;
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return n;
    }
}
