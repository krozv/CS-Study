import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 토카가 집에 도착할 수 있는 최단 시간을 이분탐색으로 구하자
 * 같은 시간에 돌돌이가 얼마나 왔는지 확인해서 비교하기
 * 단, 둘이 동시에 집에 도착하면 잡힌 판정인것에 유의
 */
public class boj31498 {

    private static long runDistance, runPower, catchDistance, catchPower, decrease;

    public static void main(String[] args) throws Exception {
        input();

        // 거리가 1 이상이므로 시간 0은 절대 도착 불가능
        long start = 0;
        // 더 이상 토카가 움직이지 못하는 시간을 경계값으로 설정 (토카가 초인이라 안지치는 경우 처리)
        long end = decrease != 0 ? (runPower + decrease - 1) / decrease : runDistance;

        // 더 이상 움직이지 못하는 순간에도 집에 도착하지 못하는 경우
        if (runDistance - end * (2 * runPower - decrease * (end - 1)) / 2 > 0) {
            System.out.println(-1);
            return;
        }

        long mid, tempA;
        while (end - start > 1) {
            mid = (start + end) / 2;

            // 이 시간동안 토카가 갈 수 있는 거리
            tempA = runDistance - mid * (2 * runPower - decrease * (mid - 1)) / 2;
            if (tempA > 0) {
                start = mid;
            } else {
                end = mid;
            }
        }

        // 최단시간동안 토카가 가는 총 거리
        long runTotal = runDistance - end * (2 * runPower - decrease * (end - 1)) / 2;
        // 최단시간동안 돌돌이가 가는 총 거리
        long catchTotal = catchDistance - end * catchPower;

        if (runTotal <= catchTotal) { // 돌돌이보다 토카가 멀리감
            if (catchTotal <= 0 && catchTotal + catchPower > 0) { // 동시에 집 도착함ㅠ
                System.out.println(-1);
            } else { // 도망감~
                System.out.println(end);
            }
        } else { // 그냥 잡힘ㅠ
            System.out.println(-1);
        }
    }

    // 입력값이 너무 많아..
    private static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        runDistance = Long.parseLong(st.nextToken());
        runPower = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        catchDistance = Long.parseLong(st.nextToken()) + runDistance;
        catchPower = Long.parseLong(st.nextToken());
        decrease = Long.parseLong(br.readLine());
    }
}
