import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 흰색 혹은 검은색 가능한 3 x 3 크기 보드
 * 임의의 칸을 클릭 시 해당 칸과 상하좌우로 인접한 칸이 플립된다
 * 테스트 케이스의 수 P가 주어지고 만들어야 할 3 x 3 크기 보드가 주어진다
 * 모두 흰색인 보드에서 시작할 때 테스트 케이스 모양으로 만들 수 있는 최소 클릭 횟수는?
 */

public class Main {
    static Queue<int[]> queue = new ArrayDeque<>();
    static Map<Integer, Integer> cache = new HashMap<>();
    static int P;
    static int target;
    static int BOARD_SIZE = 3;
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        P = Integer.parseInt(br.readLine());

        // 테스트 케이스
        for (int i = 0; i < P; i++) {
            setting(br);
            run();
        }
    }

    private static void run() {
        queue.add(new int[] {0, 0}); // 모두 흰색 보드, 0번 플립 초기화

        // BFS
        while (!queue.isEmpty()) {
            int[] next = queue.poll();
            int board = next[0];
            int flipCount = next[1];

            if (board == target) {
                System.out.println(flipCount);
                return;
            }

            for (int i = 0; i < 9; i++) {
                int res = flip(board, i);

                // 이미 cache에 해당 보드가 있으면
                if (cache.containsKey(res)) {
                    // cache의 flip 횟수보다 현재 횟수 +1이 더 적으면
                    if (flipCount + 1 < cache.get(res)) {
                        cache.put(res, flipCount + 1);
                        queue.add(new int[] {res, flipCount + 1});
                    }
                } else { // cache에 해당 보드가 없으면 그냥 추가
                    cache.put(res, flipCount + 1);
                    queue.add(new int[] {res, flipCount + 1});
                }
            }
        }

        System.out.println(cache.get(target));
    }

    private static int flip(int board, int pos) {
        int result = board;

        result ^= (1 << (pos)); // pos 자리 토글

        int y = pos / 3;
        int x = pos % 3;

        // 상하좌우 토글
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (0 <= ny && ny < 3 && 0 <= nx && nx < 3) {
                int surr = ny * 3 + nx;
                result ^= (1 << surr);
            }
        }

        return result;
    }

    /**
     * 세팅 메서드
     * @param br 입력 스트림
     * @throws IOException 입력 예외 처리
     */
    private static void setting(BufferedReader br) throws IOException {
        target = 0; // 타겟 보드 초기화

        // 각 타일을 흰색 0, 검은색 1로 생각했을 때 이진수 9자리로 표현 가능
        for (int i = 0; i < BOARD_SIZE; i++) {
            String line = br.readLine();
            for (int j = 0; j < BOARD_SIZE; j++) {
                // 검은색 타일에 해당하는 자릿수 이진수 1로 세팅
                target |=line.charAt(j) == '*' ? (1 << (i * 3 + j)) : 0;
            }
        }
    }
}