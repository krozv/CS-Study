import java.util.*;

/**
* 후보키
* 릴레이션에서 후보키의 최대 개수를 구하라
* 후보키는 유일성과 최소성을 만족시켜야 함
*
* 일단 모든 집합을 다 보되 효율적으로 보는 방법 찾기
* 비트 마스킹 -> subset있으면 subset이 최소성 만족하는 후보키
*/

class Solution {
    static int r, c, ans;
    static String[][] relation;
    static List<Integer> candidateKeys = new ArrayList<>();

    public int solution(String[][] relation) {
        r = relation.length;
        c = relation[0].length;
        this.relation = relation;
        ans = 0;

        dfs(0, 0); // DFS

        return ans;
    }

    public void dfs(int idx, int bit) {
        // Base Case
        if (idx == c) {
            if (isUnique(bit) && isMinimal(bit)) {
                candidateKeys.add(bit); // 후보키가 될 수 있는 bit 추가
                ans++;
            }
            return;
        }

        dfs(idx + 1, bit); // 다음으로
        dfs(idx + 1, bit | (1 << idx)); // 현재 인덱스 추가
    }

    public boolean isUnique(int bit) {
        Set<String> set = new HashSet<>(); // 유일성 판단용 HashSet
        for (int i = 0; i < r; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < c; j++) {
                if ((bit & (1 << j)) != 0) { // j번 칼럼이 bit에 포함되어 있으면
                    sb.append(relation[i][j]).append(","); // 해당 칼럼값 추가
                }
            }
            set.add(sb.toString());
        }
        return set.size() == r; // 행 수랑 똑같으면 true
    }

    public boolean isMinimal(int bit) {
        for (int key : candidateKeys) { 
            if ((bit & key) == key) { // bit가 후보키 중에서 더 작은 집합을 포함하고 있으면 최소성 만족 못함
                return false;
            }
        }
        return true;
    }
}
