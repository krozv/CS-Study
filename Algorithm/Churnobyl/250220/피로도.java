class Solution {
    static int length, answer;
    static int[][] dungeons;
    
    public int solution(int k, int[][] dungeons) {
        this.length = dungeons.length;
        this.dungeons = dungeons;
        dfs(k, 0, 0);
        return answer;
    }
    
    private static void dfs(int fatigue, int count, int bit) {
        if (bit == (1 << length) - 1) {
            answer = Math.max(answer, count);
            return;
        }
        
        for (int i = 0; i < length; i++) {
            if ((bit & (1 << i)) == 0) {
                if (fatigue >= dungeons[i][0]) {
                    dfs(fatigue - dungeons[i][1], count + 1, bit | (1 << i));   
                }
                dfs(fatigue, count, bit | (1 << i));
            }
        }
    }
}