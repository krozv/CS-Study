import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static char[][] map;
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};
    static boolean[][][][] isMove;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new char[N][N];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        makeIsMove();

        for (int i = 0; i < Q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken()) - 1;
            int c1 = Integer.parseInt(st.nextToken()) - 1;
            int r2 = Integer.parseInt(st.nextToken()) - 1;
            int c2 = Integer.parseInt(st.nextToken()) - 1;

            int result = run(r1, c1, r2, c2);
            sb.append(result).append("\n");
        }

        System.out.println(sb);
    }

    static void makeIsMove() {
        isMove = new boolean[N][N][N][N];
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (map[y][x] != '.') continue;
                for (int dir = 0; dir < 4; dir++) {
                    for (int jump = 1; jump <= 5; jump++) {
                        int ny = y + dy[dir] * jump;
                        int nx = x + dx[dir] * jump;
                        if (!inRange(ny, nx)) break;
                        if (map[ny][nx] == '#') break;
                        if (map[ny][nx] == 'S') continue;
                        if (checkSnake(y, x, ny, nx)) {
                            isMove[y][x][ny][nx] = true;
                        }
                    }
                }
            }
        }
    }

    private static int run(int r1, int c1, int r2, int c2) {
        if (map[r1][c1] != '.' || map[r2][c2] != '.') {
            return -1;
        }

        int[][][] dp = new int[N][N][6];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        dp[r1][c1][1] = 0;
        pq.add(new int[]{0, r1, c1, 1});

        while (!pq.isEmpty()) {
            int[] now = pq.poll();
            int dist = now[0];
            int y = now[1];
            int x = now[2];
            int k = now[3];

            if (y == r2 && x == c2) continue;
            if (dp[y][x][k] < dist) continue;

            for (int dir = 0; dir < 4; dir++) {
                int ny = y + k * dy[dir];
                int nx = x + k * dx[dir];
                if (inRange(ny, nx) && isMove[y][x][ny][nx]) {
                    if (dp[ny][nx][k] > dist + 1) {
                        dp[ny][nx][k] = dist + 1;
                        pq.add(new int[]{dp[ny][nx][k], ny, nx, k});
                    }
                }

                for (int i = k + 1; i <= 5; i++) {
                    ny = y + i * dy[dir];
                    nx = x + i * dx[dir];
                    if (inRange(ny, nx) && isMove[y][x][ny][nx]) {
                        int cost = dist + getCost(k, i) + 1;
                        if (dp[ny][nx][i] > cost) {
                            dp[ny][nx][i] = cost;
                            pq.add(new int[]{cost, ny, nx, i});
                        }
                    }
                }

                for (int t = 1; t < k; t++) {
                    ny = y + t * dy[dir];
                    nx = x + t * dx[dir];
                    if (inRange(ny, nx) && isMove[y][x][ny][nx]) {
                        int cost = dist + 2;
                        if (dp[ny][nx][t] > cost) {
                            dp[ny][nx][t] = cost;
                            pq.add(new int[]{cost, ny, nx, t});
                        }
                    }
                }
            }


        }

        int answer = Integer.MAX_VALUE;
        for (int k = 1; k <= 5; k++) {
            answer = Math.min(answer, dp[r2][c2][k]);
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private static int getCost(int start, int end) {
        int cost = 0;
        for (int i = start + 1; i <= end; i++) {
            cost += i * i;
        }
        return cost;
    }

    private static boolean inRange(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    private static boolean checkSnake(int y, int x, int ny, int nx) {
        if (y == ny) {
            int start = Math.min(x, nx);
            int end = Math.max(x, nx);
            for (int i = start + 1; i <= end; i++) {
                if (map[y][i] == '#') return false;
            }
        } else {
            int start = Math.min(y, ny);
            int end = Math.max(y, ny);
            for (int i = start + 1; i <= end; i++) {
                if (map[i][x] == '#') return false;
            }
        }
        return true;
    }
}
