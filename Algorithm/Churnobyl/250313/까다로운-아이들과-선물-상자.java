import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] segTree;
    static int MAX_VALUE = 100_000;
    static int[] w, b;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        w = new int[M];
        b = new int[M];

        segTree = new int[MAX_VALUE * 4];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int val = Integer.parseInt(st.nextToken());
            update(1, 1, MAX_VALUE, val, 1);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            w[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        boolean canTakeIt = true;

        for (int i = 0; i < M; i++) {
            int want = w[i];
            int rank = b[i];

            int indexNode = query(1, 1, MAX_VALUE, rank);
            int actualValue = indexNode;

            if (actualValue < want || segTree[1] < rank) {
                canTakeIt = false;
                break;
            }

            update(1, 1, MAX_VALUE, indexNode, -1);
            if (actualValue - want > 0) {
                update(1, 1, MAX_VALUE, actualValue - want, 1);
            }
        }

        System.out.println(canTakeIt ? 1 : 0);
    }

    private static void update(int node, int start, int end, int idx, int value) {
        if (start == end) {
            segTree[node] += value;
            return;
        }

        int mid = (start + end) / 2;
        if (idx <= mid) {
            update(node * 2, start, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, end, idx, value);
        }

        segTree[node] = segTree[node * 2] + segTree[node * 2 + 1];
    }

    private static int query(int node, int start, int end, int rank) {
        if (start == end) return start;

        int mid = (start + end) / 2;
        int rightCount = segTree[node * 2 + 1];

        if (rightCount >= rank) {
            return query(node * 2 + 1, mid + 1, end, rank);
        } else {
            return query(node * 2, start, mid, rank - rightCount);
        }
    }
}
