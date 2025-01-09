import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 로봇 청소기로 청소하기
 * 더러운 칸을 모두 청소해야 함
 * 로봇은 상하좌우 움직일 수 있고 같은 칸 여러 번 방문 가능
 * 가구가 놓여진 칸은 이동할 수 없음
 * 더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값은?
 *
 * max w = 20, max h = 20, max robots = 10
 * # of cases = 20 * 20 * (2 ^ 10) = 409,600
 */

public class Main {
    static int w, h, maxIdx;
    static int[][] map;
    static boolean[][][] visited;
    static Queue<int[]> queue = new ArrayDeque<>();
    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};
    static int[][] edges;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 각 테스트 케이스 세팅
        while (true) {
            queue.clear(); // DFS를 위한 queue 초기화
            st = new StringTokenizer(br.readLine());

            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            if (w == 0 && h == 0) return;

            map = new int[h][w];

            maxIdx = 1; // 오염 지역 인덱스

            // map 읽기
            for (int i = 0; i < h; i++) {
                String line = br.readLine();

                for (int j = 0; j < w; j++) {
                    char c = line.charAt(j);

                    switch (c) {
                        case 'o':
                            map[i][j] = 0; // 로봇 청소기는 0으로 변환
                            queue.add(new int[] {i, j, map[i][j], 0}); // BFS를 위한 큐에 집어 넣기
                            break;
                        case '*':
                            map[i][j] = maxIdx++; // 오염 지역은 1부터 n으로 초기화
                            queue.add(new int[] {i, j, map[i][j], 0}); // BFS를 위한 큐에 집어 넣기
                            break;
                        case 'x':
                            map[i][j] = -2; // 못 가는 지역은 -2
                            break;
                        case '.':
                            map[i][j] = -1; // 갈 수 있는 지역은 -1
                    }
                }
            }

            visited = new boolean[h][w][maxIdx]; // 오염 지역의 개수만큼 visited 배열 초기화
            edges = new int[maxIdx][maxIdx]; // 나중에 최단 경로를 찾기 위해 edges 배열 초기화

            for (int i = 0; i < maxIdx; i++) {
                Arrays.fill(edges[i], Integer.MAX_VALUE); // edges 배열 INF 선언
            }

            run();
        }
    }

    private static void run() {
        // BFS
        while (!queue.isEmpty()) {
            int[] next = queue.poll();

            int y = next[0];
            int x = next[1];
            int start = next[2];
            int step = next[3];

            visited[y][x][start] = true;

            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];

                // ny, nx가 범위 내 && 초기 오염 위치로부터 시작했을 때 아직 방문 x && 가구 x 라면
                if (0 <= ny && ny < h && 0 <= nx && nx < w && !visited[ny][nx][start] && map[ny][nx] != -2) {
                    if (map[ny][nx] > -1) { // 근데 로봇의 초기 위치나 오염 지역이면서
                        if (start != map[ny][nx]) { // 초기 위치가 아니면
                            // 초기 위치부터 시작해서 로봇 위치나 다른 오염 지역에 도착했다는 뜻이므로
                            if (edges[start][map[ny][nx]] == Integer.MAX_VALUE) {
                                edges[start][map[ny][nx]] = Math.min(step + 1, edges[start][map[ny][nx]]);
                                queue.add(new int[]{ny, nx, start, step + 1});
                            }
                        }
                    } else { // 그냥 갈 수 있는 길이면
                        // step을 +1하고 다시 탐색
                        queue.add(new int[] {ny, nx, start, step + 1});
                    }
                    visited[ny][nx][start] = true;
                }
            }
        }

        findRoute(); // TSP
    }

    private static void findRoute() {
        dp = new int[1 << maxIdx][maxIdx]; // 배열 초기화 dp[방문한 지역(bitMask)][현재 위치]
        for (int i = 0; i < (1 << maxIdx); i++) {
            // -1로 초기화
            Arrays.fill(dp[i], -1);
        }

        for (int i = 0; i < maxIdx; i++) {
            for (int j = 0; j < maxIdx; j++) {
                if (i != j && edges[i][j] == Integer.MAX_VALUE) { // 연결되지 않은 노드가 있다면
                    System.out.println(-1);
                    return; // -1 출력하고 종료
                }
            }
        }

        int result = tsp(1, 0);

        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    // TSP 재귀 메서드
    private static int tsp(int mask, int pos) {
        // 모든 점을 다 돌았으면 0 리턴하고 종료
        if (mask == (1 << maxIdx) - 1) {
            return 0;
        }

        // 평가됐다면 그대로 리턴
        if (dp[mask][pos] != -1) {
            return dp[mask][pos];
        }

        int minCost = Integer.MAX_VALUE;

        // 아직 방문하지 않은 점 찾기
        for (int next = 0; next < maxIdx; next++) {
            // 만약 아직 방문하지 않았으면서 && 해당 위치가 갈 수 있는 위치라면
            if ((mask & (1 << next)) == 0 && edges[pos][next] != Integer.MAX_VALUE) {
                // 갔다 치고 + 다음 지점에서 TSP 재귀적으로 호출
                int cost = edges[pos][next] + tsp(mask | (1 << next), next);
                // 시뮬레이션한 비용 중에 최솟값을 minCost로 업데이트
                minCost = Math.min(minCost, cost);
            }
        }

        if (minCost == Integer.MAX_VALUE) {

        }

        dp[mask][pos] = minCost;
        return minCost;
    }
}