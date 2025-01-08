import java.util.*;

/**
 * 임의의 컬럼들을 선택하고 그 컬럼 내 값을 한줄 한줄 비교했을때
 * 동일한 값들을 가진 줄이 존재한다면 그 컬럼들은 키로 사용할 수 없음
 * 컬럼의 개수가 적으므로 모든 컬럼의 조합을 탐색하며
 * 키로 사용 가능한지, 최소성을 만족하는지 탐색
 */
public class pg후보키 {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        String[][] relation = new String[][]{{"100", "ryan", "music", "2"}, {"200", "apeach", "math", "2"}, {"300", "tube", "computer", "3"}, {"400", "con", "computer", "4"}, {"500", "muzi", "music", "3"}, {"600", "apeach", "music", "2"}};

        System.out.println(solution.solution(relation));
    }

    private static class Solution {

        public int solution(String[][] relation) {
            int rowLength = relation.length;
            int columnLength = relation[0].length;

            // 이후 여러 컬럼에서의 값 중복을 편하게 비교하기 위해 인덱스를 해싱하여 저장
            // ex) ryan, apeach, tube, apeach -> a, b, c, b
            char[][] indexList = new char[columnLength][rowLength];
            char index;
            for (int i = 0; i < columnLength; i++) {
                index = 'a';
                loop:
                for (int j = 0; j < rowLength; j++) {
                    for (int k = 0; k < j; k++) {
                        if (relation[j][i].equals(relation[k][i])) {
                            indexList[i][j] = indexList[i][k];
                            continue loop;
                        }
                    }
                    indexList[i][j] = index++;
                }
            }

            List<Integer> candidateKey = new LinkedList<>(); // 후보키를 저장할 리스트
            Set<String> candidate; // 해시값 중복 체크를 위한 Set
            StringBuilder now;

            // 비트마스킹을 통해 모든 컬럼 조합 탐색
            loop1:
            for (int i = 1; i < (1 << columnLength); i++) {

                // 유일성 검사
                candidate = new HashSet<>();
                for (int j = 0; j < rowLength; j++) {

                    // 선택된 컬럼들의 j번째 row 를 순회하며 해시값 생성
                    now = new StringBuilder();
                    for (int k = 0; k < columnLength; k++) {
                        if ((i & (1 << k)) != 0) {
                            now.append(indexList[k][j]);
                        }
                    }

                    // 이미 해당 값이 나온 적 있는 경우
                    if (candidate.contains(now.toString())) {
                        continue loop1;
                    }
                    candidate.add(now.toString());
                }

                // 지금까지의 후보키를 순회하며 최소성 검사
                for (int j = 0; j < candidateKey.size(); j++) {
                    if ((i & candidateKey.get(j)) == candidateKey.get(j)) {
                        continue loop1;
                    }
                }
                candidateKey.add(i);
            }

            return candidateKey.size();
        }
    }
}
