import java.io.*;
import java.util.*;

public class Main {
    static int[] segTree;
    static int h, offset;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        h = (int) Math.ceil(Math.log(n) / Math.log(2));
        offset = 1 << h;
        segTree = new int[offset * 2];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int p = Integer.parseInt(st.nextToken());
            segTree[offset + i] = p;
        }

        for (int i = offset - 1; i > 0; i--) {
            segTree[i] = segTree[i * 2] + segTree[i * 2 + 1];
        }

        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int winner = Integer.parseInt(st.nextToken());
            int prefix = query(1, 1, offset, 1, winner - 1);
            int X = prefix + 1;

            sb.append(X).append(" ");
            update(winner);
        }

        System.out.println(sb);
    }

    private static int query(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight) {
        if (queryRight < nodeLeft || queryLeft > nodeRight) {
            return 0;
        }

        if (queryLeft <= nodeLeft && nodeRight <= queryRight) {
            return segTree[node];
        }

        int mid = (nodeLeft + nodeRight) / 2;

        return query(node * 2, nodeLeft, mid, queryLeft, queryRight) + query(node * 2 + 1, mid + 1, nodeRight, queryLeft, queryRight);
    }

    private static void update(int k) {
        int node = offset + k - 1;
        segTree[node] = 0;
        while(node > 1) {
            node /= 2;
            segTree[node] = segTree[node * 2] + segTree[node * 2 + 1];
        }
    }
}
