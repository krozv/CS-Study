import java.util.ArrayList;
import java.util.List;

/**
 * (x, y)에 1이 하나 생길때마다 총 합이 (x + 1) * (y + 1) * (X - x) * (Y - y) 씩 상승함
 * 이 점을 이용해 미리 부서진 부분의 좌표와 값을 저장해두고, DFS 시작
 * Knapsack 문제라고 하길래 골랐는데 내 머리로는 모르겠다..
 */
public class boj17075 {

    private static int X, Y, mod, total;
    private static int[][] map;

    private static List<Pos> broken = new ArrayList<>();
    private static StringBuilder sb = new StringBuilder();

    // 부서진 위치의 좌표와 값 저장
    private static class Pos {
        int x, y, value;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
            this.value = ((x + 1) * (y + 1) * (X - x) * (Y - y)) % mod;
        }
    }

    public static void main(String[] args) throws Exception {
        X = read();
        Y = read();
        mod = read();

        map = new int[X][Y];
        total = 0;

        // 배열을 저장하며 이미 1인 칸이 있는 경우 미리 계산
        int now;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                now = read();
                if (now == 1) {
                    total += (i + 1) * (j + 1) * (X - i) * (Y - j);
                } else if (now == -1) {
                    broken.add(new Pos(i, j));
                    now = 0;
                }
                map[i][j] = now;
            }
        }
        total %= mod;

        // 모두 0이여도 가능한 경우
        if (total == 0) {
            write();
        } else {
            // DFS 시작
            if (isAvailable(total, 0)) {
                write();
            } else {
                sb.append(-1).append("\n");
            }
        }

        System.out.print(sb);
    }

    // DFS 방식으로 하나하나 1로 만들며 값 비교
    private static boolean isAvailable(int value, int depth) {
        // 나머지가 0인 경우 성공
        if (value == 0) {
            return true;
        }

        // 이미 최대 깊이까지 도달한 경우
        if (depth == broken.size()) {
            return false;
        }

        // 현재 위치 1로 변경 후 다음 depth 진입
        Pos now = broken.get(depth);
        map[now.x][now.y] = 1;
        if (isAvailable((value + now.value) % mod, depth + 1)) {
            return true;
        }

        // 현재 위치 0으로 변경 후 다음 depth 진입
        map[now.x][now.y] = 0;
        if (isAvailable(value, depth + 1)) {
            return true;
        }

        // 모든 경우를 봐도 실패한 경우
        return false;
    }

    // 배열 출력
    private static void write() {
        sb.append(1).append("\n");
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }
    }

    private static int read() throws Exception {
        int c, n = System.in.read();
        boolean negative = false;
        if (n == '-') {
            negative = true;
            n = System.in.read() & 15;
        } else {
            n &= 15;
        }
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return negative ? -n : n;
    }
}
