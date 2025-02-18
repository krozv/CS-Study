import java.util.Arrays;
import java.util.Comparator;

/**
 * Knapsack 공식은 금방 만들었지만 의외의 복병이 있던 문제
 * j >= (요구 피로도) 인 경우 비교를 시작하지만 j - (사용 피로도) 값을 가져와야 함
 * 즉, 최소 (요구 피로도) - (사용 피로도) 의 값을 가져오게 됨
 * 이 값이 시작부터 너무 커지면 해당 던전에 대한 정보를 다음 던전이 못가져오는 상황이 발생
 *
 * ex)
 * 지금 당장 입력된 에시만 해도 첫 값이 (80, 20)
 * k = 80 이므로 j가 계속해서 증가하다가 result[1][80]에 1이 들어감
 * 하지만 두번째 값인 (50, 40)은 순회 과정에서 절대 result[1][80]을 참조하지 않음
 * j가 80까지 상승해도 result[1][70]까지만 확인함 70 = j - ((요구 피로도) - (사용 피로도))
 *
 * 이런 현상을 방지하기 위해 (요구 피로도) - (사용 피로도) 로 정렬하여 시작하자
 */
public class pg피로도 {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        int k = 80;
        int[][] dungeons = {{80, 20}, {50, 40}, {30, 10}};

        System.out.println(solution.solution(k, dungeons));
    }

    private static class Solution {

        public int solution(int k, int[][] dungeons) {
            int count = dungeons.length;
            Arrays.sort(dungeons, Comparator.comparingInt(o -> o[0] - o[1]));
            int[][] result = new int[count + 1][k + 1];

            for (int i = 1; i <= count; i++) {
                for (int j = 1; j <= k; j++) {

                    // 피로도 j로는 최소 피로도를 만족하지 못해 불가능한 경우
                    if (dungeons[i - 1][0] > j) {
                        result[i][j] = result[i - 1][j];
                    } else {
                        // 이 던전을 포함하지 않은 경우와 이 던전의 요구 피로도양을 뺀 경우 + 1 을 비교
                        result[i][j] = Math.max(result[i - 1][j], result[i - 1][j - dungeons[i - 1][1]] + 1);
                    }

                }
            }

            return result[count][k];
        }

    }
}
