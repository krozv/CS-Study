import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 3 * 3 보드이므로 보드의 상태는 총 2^9
 * int 변수로 보드의 모든 상태를 표현할 수 있음
 * 이 점을 이용해 같은 모양이 나오는 상황을 백트래킹
 */
public class boj10472 {

    public static void main(String[] args) throws Exception {
        int tmp, board;
        int[] now;
        boolean[] visited;
        Queue<int[]> bfsQueue;
        StringBuilder sb = new StringBuilder();

        // 테스트 케이스 입력
        int testCase = read();

        for (int tc = 0; tc < testCase; tc++) {
            // 2^9 크기의 비트로 보드 저장
            board = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tmp = System.in.read();
                    if (tmp == '*') {
                        board |= (1 << (i * 3 + j));
                    }
                }
                System.in.read();
            }

            // 이미 보드가 하얀색인 경우 끝
            if (board == 0) {
                sb.append(0).append("\n");
                continue;
            }

            // 방문 처리
            visited = new boolean[1 << 10];

            bfsQueue = new ArrayDeque<>();
            bfsQueue.add(new int[]{board, 0});
            visited[board] = true;

            loop:
            while (!bfsQueue.isEmpty()) {
                now = bfsQueue.poll();
                // 보드의 9개 칸 순회
                for (int i = 0; i < 9; i++) {

                    // 보드 복사
                    tmp = now[0];

                    // 현재 위치 좌우칸 반전
                    if (i % 3 == 1) {
                        tmp ^= (1 << (i - 1));
                        tmp ^= (1 << (i + 1));
                    } else if (i % 3 == 2) {
                        tmp ^= (1 << (i - 1));
                    } else {
                        tmp ^= (1 << (i + 1));
                    }

                    // 현재 위치 상하칸 반전
                    if (i > 2) {
                        tmp ^= (1 << (i - 3));
                    }
                    if (i < 6) {
                        tmp ^= (1 << (i + 3));
                    }

                    // 현재 위치 반전
                    tmp ^= (1 << i);

                    // 보드가 모두 하얀색이 된 경우
                    if (tmp == 0) {
                        sb.append(now[1] + 1).append("\n");
                        break loop;
                    }

                    // 아직 방문하지 않은 보드인 경우 방문처리 후 큐에 넣기
                    if (!visited[tmp]) {
                        visited[tmp] = true;
                        bfsQueue.add(new int[]{tmp, now[1] + 1});
                    }
                }
            }
        }
        System.out.println(sb);
    }

    private static int read() throws Exception {
        int c, n = System.in.read() & 15;
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return n;
    }
}
