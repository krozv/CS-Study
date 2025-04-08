import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class pg배달 {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        int N = 5;
        int[][] road = {{1, 2, 1}, {2, 3, 3}, {5, 2, 2}, {1, 4, 2}, {5, 3, 1}, {5, 4, 2}};
        int K = 3;

        System.out.println(solution.solution(N, road, K));
    }

    private static class Solution {

        // 도로 정보
        private static List<Road>[] graph;

        // 최소 거리
        private static int[] distance;

        // 다익스트라 방문처리
        private static boolean[] visited;

        // 도로 정보
        private static class Road {
            int to;
            int time;

            public Road(int to, int time) {
                this.to = to;
                this.time = time;
            }
        }

        // 우선순위큐 노드
        private static class Node implements Comparable<Node> {
            int to;
            int time;

            public Node(int to, int time) {
                this.to = to;
                this.time = time;
            }

            public int compareTo(Node o) {
                return time - o.time;
            }
        }

        public int solution(int N, int[][] road, int K) {
            graph = new List[N];
            for (int i = 0; i < N; i++) {
                graph[i] = new ArrayList<>();
            }

            // 최단거리 설정
            distance = new int[N];
            for (int i = 0; i < N; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            visited = new boolean[N];

            // 양방향 도로 정보 저장
            int length = road.length;
            for (int i = 0; i < length; i++) {
                graph[road[i][0] - 1].add(new Road(road[i][1] - 1, road[i][2]));
                graph[road[i][1] - 1].add(new Road(road[i][0] - 1, road[i][2]));
            }

            dijkstra();

            // K 이하인 경우 더함
            int sum = 0;
            for (int i = 0; i < N; i++) {
                if (distance[i] <= K) {
                    sum++;
                }
            }

            return sum;
        }

        // 아주 평범한 다익스트라
        private static void dijkstra() {
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(0, 0));
            distance[0] = 0;

            int length;
            Node now;
            Road next;
            while (!pq.isEmpty()) {
                now = pq.poll();
                if (!visited[now.to]) {
                    visited[now.to] = true;

                    length = graph[now.to].size();
                    for (int i = 0; i < length; i++) {
                        next = graph[now.to].get(i);
                        if (distance[next.to] > now.time + next.time) {
                            distance[next.to] = now.time + next.time;
                            pq.add(new Node(next.to, distance[next.to]));
                        }
                    }
                }
            }
        }
    }
}
