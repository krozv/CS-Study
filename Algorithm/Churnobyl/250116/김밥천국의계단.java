import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 김밥천국의 계단
 * 무한히 많은 계단 중에 가장 아래가 0번 계단, N번째 계단 옆에 김밥 가게 있음
 * 2가지 행동 중에 하나 정해서 총 K번 행동했을 때 N번째 계단에 도달할 수 있으면 minigimbob, 없으면 water
 * 1. 계단 한 칸 오르기
 * 2. i번째 계단에서 i + Math.floor(i / 2)로 순간이동
 * 현재 0번째 계단에서 시작하면 과연 먹을 수 있을까?
 */

public class Main {
    static int N, K;
    static int[] dp;
    static Queue<int[]> queue = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[N + 1];
        Arrays.fill(dp, Integer.MAX_VALUE); // 다익스트라
        dp[0] = 0;

        queue.add(new int[] {0, 0});

        while (!queue.isEmpty()) {
            int[] next = queue.poll();
            int pos = next[0];
            int cnt = next[1];

            if (cnt >= K) continue;

            int nextPos = pos + 1; // + 1
            if (check(cnt, nextPos)) return;

            nextPos = pos + (pos / 2); // i + Math.floor(i / 2)
            if (check(cnt, nextPos)) return;
        }

        System.out.println("water");
    }

    private static boolean check(int cnt, int nextPos) {
        if (nextPos <= N && cnt + 1 < dp[nextPos]) {
            if (nextPos == N) { // 위치 0에서 턴 소모하면 되니까 K번 안에 N에 도달하기만 하면 됨
                System.out.println("minigimbob");
                return true;
            }
            dp[nextPos] = cnt + 1;
            queue.add(new int[]{nextPos, cnt + 1});
        }
        return false;
    }
}