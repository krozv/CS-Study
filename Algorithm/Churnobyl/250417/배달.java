import java.util.*;

class Solution {
    static int[][] edges;
    static int[] dp;
    
    public int solution(int N, int[][] road, int K) {
        edges = new int[N][N];
        dp = new int[N];
        
        Arrays.fill(dp, Integer.MAX_VALUE);
        
        for (int[] r : road) {
            int a = r[0] - 1;
            int b = r[1] - 1;
            int cost = r[2];
            
            if (edges[a][b] == 0 || edges[a][b] > cost) {
                edges[a][b] = cost;
                edges[b][a] = cost;
            }
        }
        
        dp[0] = 0;
        
        Queue<Integer> queue = new ArrayDeque<>();
        
        queue.add(0);
        
        while (!queue.isEmpty()) {
            int nxt = queue.poll();
            
            for (int i = 0; i < N; i++) {
                if (edges[nxt][i] != 0 && dp[nxt] + edges[nxt][i] < dp[i]) {
                    dp[i] = dp[nxt] + edges[nxt][i];
                    queue.add(i);
                }
            }
        }
        
        int answer = 0;
        
        for (int i = 0; i < N; i++) {
            if (dp[i] <= K) answer++;
        }
        
        return answer;
    }
}