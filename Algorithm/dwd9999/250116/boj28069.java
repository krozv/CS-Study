import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 0번째 계단부터 위로 올라가며 계산한 값을 재사용하는 Bottom-Up DP
 * 기저사례를 0번째 계단으로 잡고 시작
 * 여기서 0번째와 1번째 계단에서는 지팡이를 사용해 제자리 걸음이 가능함
 * 즉, 주어진 횟수 이하로는 도착해도 무관함을 상기
 */
public class boj28069 {

    private static int stairCount, maxMove; // 계단 수, 최대 걸음수
    private static int[] moveToArrive; // 도착하기까지 필요한 최소 걸음 수

    private static final String SUCCESS_RETURN = "minigimbob";
    private static final String FAIL_RETURN = "water";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        stairCount = Integer.parseInt(st.nextToken());
        maxMove = Integer.parseInt(st.nextToken());

        // 0번째 계단을 제외한 나머지 계단 최소 걸음수 초기화
        moveToArrive = new int[stairCount + 1];
        for (int i = 1; i <= stairCount; i++) {
            moveToArrive[i] = Integer.MAX_VALUE;
        }

        // 0번째 계단부터 쭉 올라감
        for (int i = 0; i < stairCount; i++) {
            upStair(i);
        }

        // 최대 걸음수 이하로만 들어오면 남는 걸음수는 0번쨰 or 1번쨰 계단에서 지팡이로 제자리 걸음 가능
        System.out.println(moveToArrive[stairCount] <= maxMove ? SUCCESS_RETURN : FAIL_RETURN);
    }

    // currentStair 에서 계단을 올라갈때 발생하는 일을 처리하는 메서드
    private static void upStair(int currentStair) {
        // 다음 갈 계단 순회
        int[] nextStairs = nextStairs(currentStair);
        for (int i = 0; i < nextStairs.length; i++) {
            // 다음 갈 계단이 계단 수를 넘어서는 경우
            if (nextStairs[i] > stairCount) {
                continue;
            }

            // 걸음수 최솟값 갱신
            if (moveToArrive[nextStairs[i]] > moveToArrive[currentStair] + 1) {
                moveToArrive[nextStairs[i]] = moveToArrive[currentStair] + 1;
            }
        }
    }

    // 다음에 갈 수 있는 계단을 반환하는 메서드
    private static int[] nextStairs(int currentStair) {
        if (currentStair == 0) {
            return new int[]{1};
        } else {
            return new int[]{currentStair + 1, currentStair + (currentStair / 2)};
        }
    }
}
