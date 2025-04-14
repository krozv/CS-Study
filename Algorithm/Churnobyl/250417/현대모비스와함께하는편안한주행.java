import java.io.*;
import java.util.*;

public class Main {

    static class Node implements Comparable<Node> {
        int index;
        double cost;

        Node(int index, double cost) {
            this.index = index;
            this.cost = cost;
        }

        public int compareTo(Node other) {
            return Double.compare(this.cost, other.cost);
        }
    }

    static int a, b, N;
    static int[][] signBoard;
    static double[] dist;

    public static void main(String[] args) throws IOException {
        setting();

        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[0] = 0;
        pq.add(new Node(0, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (dist[cur.index] < cur.cost) continue;

            for (int i = 0; i < N + 2; i++) {
                if (i == cur.index) continue;

                double d = getDistance(cur.index, i);
                if (dist[i] > dist[cur.index] + d) {
                    dist[i] = dist[cur.index] + d;
                    pq.add(new Node(i, dist[i]));
                }
            }
        }

        if (dist[N + 1] == 0) System.out.println(0);
        else System.out.printf("%.9f\n", dist[N + 1]);
    }

    static double getDistance(int from, int to) {
        int[] s = signBoard[from];
        int[] e = signBoard[to];

        double length = Math.hypot(e[0] - s[0], e[1] - s[1]);

        if (from >= 1 && from <= N) length -= 1;
        if (to >= 1 && to <= N) length -= 1;
        return Math.max(length, 0);
    }

    private static void setting() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(br.readLine());

        signBoard = new int[N + 2][2];
        dist = new double[N + 2];
        Arrays.fill(dist, Double.MAX_VALUE);

        signBoard[0] = new int[]{0, 0}; // 출발지

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            signBoard[i][0] = Integer.parseInt(st.nextToken());
            signBoard[i][1] = Integer.parseInt(st.nextToken());
        }

        signBoard[N + 1] = new int[]{a, b}; // 도착지
    }
}
