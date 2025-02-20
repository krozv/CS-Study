class Solution {
    static final int MINIMUM = 0;
    static final int NEEDED = 1;
    
    static int dungeonCnt;
    static int answer = -1;
    
    public int solution(int k, int[][] dungeons) {
        dungeonCnt = dungeons.length;
        
        solve(-1, k, 0, 0, dungeons);
      
        return answer;
    }
    
    private void solve(int curIdx, int curGauge, int cnt, int state, int[][] dungeons) {
        answer = Math.max(answer, cnt);
        
        // 모든 던전을 탐색한 경우
        if (state == (1 << dungeonCnt) - 1) {
            return;
        }
        
        // 다음 던전 탐색
        for (int idx = 0; idx < dungeonCnt; idx++) {
            // 이미 방문한 던전인 경우, 패스
            if ((state & (1 << idx)) == (1 << idx)) {
                continue;
            }
            
            // 최소 피로도를 만족하지 않는 경우, 패스
            if (curGauge < dungeons[idx][MINIMUM]) {
                continue;
            }
            
            solve(idx, curGauge - dungeons[idx][NEEDED], cnt + 1, state | (1 << idx), dungeons);
        }
    }
}