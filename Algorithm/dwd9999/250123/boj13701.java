package SSAFY_AlgoStudy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * visited 체크를 통해 중복 출력을 제거
 * 하지만 메모리가 부족하므로 boolean이 아닌 비트로 체크하자
 */
public class boj13701 {

    // 최대 2^25이므로 최대 지수 설정
    private static final int MAX_EXPONENT = 25;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        // int가 32비트를 표현할 수 있음
        // 즉, 하나의 int가 2^5를 표현할 수 있으니, 5를 뺀 배열로 모두 체크 가능
        int[] visited = new int[1 << (MAX_EXPONENT - 5)];

        int num;
        while (st.hasMoreTokens()) {
            num = Integer.parseInt(st.nextToken());
            if ((visited[num / 32] & (1 << num % 32)) == 0) {
                sb.append(num).append(" ");
                visited[num / 32] |= (1 << num % 32);
            }
        }

        System.out.println(sb);
    }
}
