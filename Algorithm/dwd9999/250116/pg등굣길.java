public class pg등굣길 {

    private static class Solution {

        private int x, y;
        private int[][] path;
        private boolean[][] isPuddle;

        private final int MOD = 1_000_000_007;

        public int solution(int m, int n, int[][] puddles) {
            x = n;
            y = m;

            path = new int[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    path[i][j] = -1;
                }
            }
            path[0][0] = 1;

            isPuddle = new boolean[x][y];
            for (int i = 0; i < puddles.length; i++) {
                isPuddle[puddles[i][0]][puddles[i][1]] = true;
            }

            return findPath(x - 1, y - 1);
        }

        private int findPath(int m, int n) {
            if (isOutOfBounds(m, n)) {
                return 0;
            }

            if (path[m][n] != -1) {
                return path[m][n];
            }

            if (isPuddle[m][n]) {
                return path[m][n] = 0;
            }

            return path[m][n] = ((findPath(m - 1, n) % MOD) + (findPath(m, n - 1) % MOD)) % MOD;
        }

        private boolean isOutOfBounds(int m, int n) {
            return m < 0 || m >= x || n < 0 || n >= y;
        }
    }
}
