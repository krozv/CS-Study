import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 비트마스킹을 활용한 조합으로 모든 조합을 확인하자
 * 당연히 DP를 활용해 중복 연산은 방지
 * 모든 조합의 최대 안정성 계산 후 순회하며 키가 목표치를 넘지만 안정성이 제일 높은 경우 찾기!
 */
public class boj10649 {

    private static int[] height;
    private static int[] weight;
    private static int[] stability;

    private static final String FAILED = "Mark is too tall";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int teamCount = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        // 찾기 편하게 키, 몸무게, 안정성 모두 비트 인덱스로 저장
        height = new int[1 << teamCount];
        weight = new int[1 << teamCount];
        stability = new int[1 << teamCount];

        for (int i = 0; i < teamCount; i++) {
            st = new StringTokenizer(br.readLine());
            height[1 << i] = Integer.parseInt(st.nextToken());
            weight[1 << i] = Integer.parseInt(st.nextToken());
            stability[1 << i] = Integer.parseInt(st.nextToken());
        }

        // 모든 조합을 순회하며 최대 안정치를 구함
        for (int i = 1; i < (1 << teamCount); i++) {
            getMaxStability(i);
        }

        int result = -1;

        // 모든 조합을 순회함
        for (int i = 1; i < (1 << teamCount); i++) {
            // 그 조합의 총 키가 묙표치를 넘으며, 더 높은 안정성을 가질 수 있는 경우
            if (getHeight(i) >= target && stability[i] > result) {
                result = stability[i];

            }
        }

        // 한번도 안정성을 갱신시키지 못했으면 실패
        System.out.println(result == -1 ? FAILED : result);
    }


    private static void getMaxStability(int include) {
        // 이미 처음에 입력받은 경우
        if (stability[include] != 0) {
            return;
        }

        int max = -1;

        int now = include;
        int temp;
        while (now > 0) {
            // 가장 낮은 비트
            temp = now & -now;

            // 유효성 검사
            if (isValid(temp) && isValid(include ^ temp)) {
                // 위에 올릴 소의 무게를 견딜 수 있는지 확인
                if (stability[include ^ temp] >= weight[temp]) {
                    // 더 높은 안정성을 가지는 경우 갱신
                    if (Math.min(stability[temp], stability[include ^ temp] - weight[temp]) > max) {
                        max = Math.min(stability[temp], stability[include ^ temp] - weight[temp]);
                    }
                }
            }

            // 사용한 비트 제외
            now ^= temp;
        }

        // 최대 안정치 설정
        stability[include] = max;
    }


    // 해당 조합이 불가능하여 -1이 저장된 경우 판별
    private static boolean isValid(int include) {
        return stability[include] != -1;
    }


    // 한 비트씩 추출해서 다 더해주기
    private static int getHeight(int include) {
        if (height[include] != 0) {
            return height[include];
        }

        int temp = include & -include;
        return height[include] = getHeight(include ^ temp) + getHeight(temp);
    }
}
