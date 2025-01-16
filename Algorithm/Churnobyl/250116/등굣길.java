class Solution {
    static int mod = 1_000_000_007;
    static boolean[][] map;
    static int[][] dp;
    static int m, n;
    
    public int solution(int m, int n, int[][] puddles) {
        this.m = m;
        this.n = n;
        map = new boolean[n][m];
        dp = new int[n][m];
        dp[0][0] = 1;
        
        for (int i = 0; i < puddles.length; i++) {
            map[puddles[i][1] - 1][puddles[i][0] - 1] = true;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j]) continue;
                
                if (i > 0) dp[i][j] += dp[i - 1][j];
                if (j > 0) dp[i][j] += dp[i][j - 1];
                dp[i][j] %= mod;
            }
        }
        
        return dp[n - 1][m - 1] % mod;
    }
}