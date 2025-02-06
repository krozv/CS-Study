import java.io.*;
import java.util.*;

public class Main {
    static long a, b, c, d, k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        a = Long.parseLong(st.nextToken());  // 집까지의 거리
        b = Long.parseLong(st.nextToken());  // Toka의 초기 이동 거리

        st = new StringTokenizer(br.readLine());
        c = Long.parseLong(st.nextToken());  // Doldol이 추가로 떨어진 거리
        d = Long.parseLong(st.nextToken());  // Doldol의 이동 거리

        k = Long.parseLong(br.readLine());   // Toka의 이동 거리 감소량

        long left = 0;
        long right = (a + c) / d + 1;  // 최대 이동 횟수를 기준으로 이진 탐색
        long answer = -1;

        while (left <= right) {
            long mid = (left + right) / 2;

            long m = Math.min(mid, (k == 0) ? mid : b / k);
            long tokka_moved = m * b - (k * m * (m - 1)) / 2;
            long tokka_pos = a - tokka_moved;
            
            long doldol_pos = a + c - d * mid;

            // **둘이 동시에 도착하면 Toka는 잡힘**
            if (tokka_pos <= 0 && doldol_pos <= 0) {
                right = mid - 1;
            }
            // **Doldol이 Tokka보다 먼저 도착하면 잡힘**
            else if (mid > 0 && doldol_pos <= tokka_pos) {
                right = mid - 1;
            }
            // **Tokka가 안전하게 집 도착**
            else if (tokka_pos <= 0) {
                answer = mid;
                right = mid - 1;
            }
            // **Tokka가 아직 집에 도착하지 못한 경우**
            else {
                left = mid + 1;
            }
        }

        System.out.println(answer);
    }
}
