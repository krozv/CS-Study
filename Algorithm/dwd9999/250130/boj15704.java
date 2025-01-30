import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class boj15704 {

    private static int nodeCount, edgeCount, budget;
    private static List<Edge>[] graph;

    private static class Edge implements Comparable<Edge> {
        int from, to, cost, limit;

        public Edge(int from, int to, int cost, int limit) {
            this.from = from;
            this.to = to;
            this.cost = cost;
            this.limit = limit;
        }

        @Override
        public int compareTo(Edge o) {
            return cost - o.cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        nodeCount = Integer.parseInt(st.nextToken());
        edgeCount = Integer.parseInt(st.nextToken());
        budget = Integer.parseInt(st.nextToken());

        graph = new List[nodeCount + 1];
        for (int i = 1; i <= nodeCount; i++) {
            graph[i] = new ArrayList<>();
        }

        int nodeA, nodeB, cost, limit;
        for (int i = 0; i < edgeCount; i++) {
            st = new StringTokenizer(br.readLine());
            nodeA = Integer.parseInt(st.nextToken());
            nodeB = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());
            limit = Integer.parseInt(st.nextToken());

            graph[nodeA].add(new Edge(nodeA, nodeB, cost, limit));
            graph[nodeB].add(new Edge(nodeB, nodeA, cost, limit));
        }

        int start = 0;
        int end = Integer.MAX_VALUE;
        int mid;
        while (end - start > 1) {
            mid = (start + end) / 2;

            if (isAvailable(mid)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        System.out.println(start);
    }

    private static boolean isAvailable(int peopleCount) {
        int[] cost = new int[nodeCount + 1];
        for (int i = 2; i <= nodeCount; i++) {
            cost[i] = Integer.MAX_VALUE;
        }

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(1, 1, 0, 0));

        boolean[] visited = new boolean[nodeCount + 1];

        int nowCost;
        Edge now;
        while (!queue.isEmpty()) {
            now = queue.poll();
            if (!visited[now.to]) {
                visited[now.to] = true;
                for (Edge next : graph[now.to]) {
                    nowCost = getCost(cost[now.to], next.cost, peopleCount, next.limit);
                    if (nowCost >= 0 && cost[next.to] > nowCost) {
                        cost[next.to] = nowCost;
                        queue.add(new Edge(next.from, next.to, cost[next.to], next.limit));
                    }
                }
            }
        }

        return cost[nodeCount] <= budget;
    }

    private static int getCost(int before, int cost, int peopleCount, int limit) {
        if (limit < peopleCount) {
            if (cost <= Integer.MAX_VALUE / (peopleCount - limit)) {
                cost *= (peopleCount - limit);
                if (cost <= Integer.MAX_VALUE / (peopleCount - limit)) {
                    cost *= (peopleCount - limit);
                    if (cost <= Integer.MAX_VALUE - before) {
                        return cost + before;
                    }
                }
            }
            return -1;
        }
        return before;
    }
}
