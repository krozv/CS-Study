/**
[조건]
- 오른쪽과 아래쪽으로만 이동 가능

[출력]
- 집에서 학교까지 갈 수 있는 최단경로의 개수
*/
import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;
    
    static int[][] pathCnt;
    static boolean[][] isPuddle;
    
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        
        init(m, n, puddles);
        
        solve(m, n);
        
        answer = pathCnt[m][n];
        
        return answer;
    }
    
    public void solve(int mapRow, int mapCol) {
        for (int row = 1; row <= mapRow; row++) {
            for (int col = 1; col <= mapCol; col++) {
                // 시작점인 경우
                if (row == 1 && col == 1) {
                    continue;
                }
                
                // 현재 칸이 웅덩이인 경우
                if (isPuddle[row][col]) {
                    continue;
                }
                
                // 위쪽 칸의 개수 추가
                if (row > 1 && !isPuddle[row-1][col]) {
                    pathCnt[row][col] += pathCnt[row-1][col];
                    pathCnt[row][col] %= MOD;
                }
                
                // 왼쪽 칸의 개수 추가
                if (col > 1 && !isPuddle[row][col-1]) {
                    pathCnt[row][col] += pathCnt[row][col-1];
                    pathCnt[row][col] %= MOD;
                }
            }
        }
    }
    
    public void init(int mapRow, int mapCol, int[][] puddles) {
        pathCnt = new int[mapRow+1][mapCol+1];
        isPuddle = new boolean[mapRow+1][mapCol+1];
        
        for (int row = 1; row <= mapRow; row++) {
            Arrays.fill(pathCnt[row], 0);
            Arrays.fill(isPuddle[row], false);
        }
        
        pathCnt[1][1] = 1;
        
        for (int idx = 0; idx < puddles.length; idx++) {
            isPuddle[puddles[idx][0]][puddles[idx][1]] = true;
        }
    }
}