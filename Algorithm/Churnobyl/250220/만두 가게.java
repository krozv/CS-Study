import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int[] a;
    static int[] b;
    static int[] c;
    static int[] d;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        a = new int[m + 1];
        b = new int[m + 1];
        c = new int[m + 1];
        d = new int[m + 1];

        c[0] = Integer.parseInt(st.nextToken());
        d[0] = Integer.parseInt(st.nextToken());

        dp = new int[m + 1][n + 1];

        for (int i = 1; i < m + 1; i++) {
            st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
            d[i] = Integer.parseInt(st.nextToken());
        }

        // 만두피만 있는 스페셜 만두로 dp[0] 초기화
        for (int i = 1; i < n + 1; i++) {
            if (i - c[0] >= 0) {
                dp[0][i] = dp[0][i - c[0]] + d[0];
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }

        // knapsack
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                // 만약 밀가루가 충분해서 i번 만두를 만들 수 있다면
                if (j - c[i] >= 0) {
                    // 우선 둘 중에 큰 값으로 초기화
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                    
                    // i번 만두를 만들 수 있는 개수만큼 체크
                    for (int k = 1; k <= (j / c[i]); k++) {
                        // 만두 속의 양이 충분하면
                        if (a[i] - b[i] * k >= 0) {
                            // 현재 값과 새로운 값 중에서 더 큰 값으로 업데이트
                            dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - c[i] * k] + d[i] * k);
                        }
                    }
                } else {
                    // 밀가루가 충분치 않으면 이전 밀가루 양과 i - 1번 만두까지 중에 더 큰 값으로 업데이트
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        System.out.println(dp[m][n]);
    }
}
