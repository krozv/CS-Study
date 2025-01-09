import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 20 x 20이므로 BFS 과정에서 시간 초과가 일어날 것 같지 않음
 * BFS 횟수를 늘려서라도 많은 정보를 얻어 총 최단 거리 계산 시간을 줄이자
 * 각 구역 간 거리를 알고있고, 출발지가 정해져있는 문제 -> 외판원 순회
 * 단, 외판원 순회와 다르게 시작 지점으로 돌아오지 않음을 상기하자
 */
public class boj4991 {

    private static int x, y; // 방 크기
    private static int pointCount; // 출발점 + 더러운 구역 개수
    private static int[][] map; // 방 구조 저장
    private static int[][] point; // i번 구역의 좌표 i[0], i[1]
    private static int[][] distance; // i번 구역부터 j번 구역까지 가는 최단거리

    private static int[][][] cache; // i번 구역을 도착하는 최단 거리. TSP 결과 저장

    private static final int[][] DIRECTION = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) throws Exception {
        // 구역을 point 배열에 저장하기 위한 변수들
        // start, dirtyPoints 구별하는 이유는 외판원 순회 시작점 저장을 위함
        int[] start;
        List<int[]> dirtyPoints;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        loop:
        while ((y = Integer.parseInt(st.nextToken())) != 0 && (x = Integer.parseInt(st.nextToken())) != 0) {
            pointCount = 0;
            start = new int[2];
            dirtyPoints = new ArrayList<>();
            map = new int[x][y];

            // 입력을 받아 map 저장
            String line;
            char square;
            for (int i = 0; i < x; i++) {
                line = br.readLine();
                for (int j = 0; j < y; j++) {
                    square = line.charAt(j);
                    if (square == 'x') {
                        map[i][j] = 1;
                    } else if (square == 'o') {
                        map[i][j] = 2;
                        start[0] = i;
                        start[1] = j;
                        pointCount++;
                    } else if (square == '*') {
                        map[i][j] = 2;
                        dirtyPoints.add(new int[]{i, j});
                        pointCount++;
                    }
                }
            }

            // map 저장 과정에서 미리 저장해둔 구역들 좌표 저장
            point = new int[pointCount][2];
            point[0] = start;
            for (int i = 1; i < pointCount; i++) {
                point[i] = dirtyPoints.get(i - 1);
            }

            // 각 구역 간 거리 Integer.MAX_VALUE 초기화
            distance = new int[pointCount][pointCount];
            for (int i = 0; i < pointCount; i++) {
                for (int j = 0; j < pointCount; j++) {
                    if (i == j) {
                        distance[i][j] = 0;
                    } else {
                        distance[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            // 각 구역 사이 거리 구하기
            for (int i = 0; i < pointCount; i++) {
                bfs(i);
            }

            // 도달하지 못하는 구역이 있는 경우 종료
            for (int i = 0; i < pointCount; i++) {
                for (int j = 0; j < pointCount; j++) {
                    if (distance[i][j] == Integer.MAX_VALUE) {
                        sb.append("-1").append("\n");
                        st = new StringTokenizer(br.readLine());
                        continue loop;
                    }
                }
            }

            // 각 구역간 이동 비용을 알았으니 외판원 순회
            cache = new int[pointCount][pointCount][1 << pointCount];
            for (int i = 0; i < pointCount; i++) {
                for (int j = 0; j < pointCount; j++) {
                    cache[i][j][1 << i] = distance[i][j];
                }
            }

            int result = Integer.MAX_VALUE;
            for (int i = 1; i < pointCount; i++) {
                if (result > tsp(0, i, ((1 << pointCount) - 1) ^ (1 << i))) {
                    result = cache[0][i][((1 << pointCount) - 1) ^ (1 << i)];
                }
            }

            // 결과 출력 후 반복
            sb.append(result).append("\n");
            st = new StringTokenizer(br.readLine());
        }
        System.out.println(sb);
    }

    // startPoint 기준 다른 구역까지 거리를 구하는 BFS
    private static void bfs(int startPoint) {
        Queue<int[]> bfsQueue = new ArrayDeque<>();
        bfsQueue.add(new int[]{point[startPoint][0], point[startPoint][1], 0});

        boolean[][] visited = new boolean[x][y];
        visited[point[startPoint][0]][point[startPoint][1]] = true;

        int nextX, nextY, temp;
        int[] now;
        while (!bfsQueue.isEmpty()) {
            now = bfsQueue.poll();
            for (int dir = 0; dir < 4; dir++) {
                nextX = now[0] + DIRECTION[dir][0];
                nextY = now[1] + DIRECTION[dir][1];

                if (nextX >= 0 && nextY >= 0 && nextX < x && nextY < y
                        && !visited[nextX][nextY] && map[nextX][nextY] != 1) {
                    if (map[nextX][nextY] == 2) {
                        // 현재 도착한 구역이 몇번 구역인지 확인 후 거리 갱신
                        temp = -1;
                        for (int i = 0; i < pointCount; i++) {
                            if (point[i][0] == nextX && point[i][1] == nextY) {
                                temp = i;
                            }
                        }
                        distance[startPoint][temp] = now[2] + 1;
                    }

                    // 해당 구역을 지나친 후에야 다른 구역에 도달할 수 있는 경우도 있으므로 계속 진행
                    visited[nextX][nextY] = true;
                    bfsQueue.add(new int[]{nextX, nextY, now[2] + 1});
                }
            }
        }
    }

    // 출발 구역, 도착 구역, 현재까지 방문한 도시(도착 구역 제외)
    // 더러운 칸 개수가 10개가 넘지 않으므로 지금까지 방문한 도시를 비트마스킹으로 저장
    private static int tsp(int start, int end, int visited) {
        // 이미 계산한 경우 재사용
        if (cache[start][end][visited] != 0) {
            return cache[start][end][visited];
        }

        // 출발 구역과 도착 구역이 같아 불가능한 경우
        if (start == end) {
            return cache[start][end][visited] = -1;
        }


        int min = Integer.MAX_VALUE;
        // 모든 구역을 순회
        for (int i = 0; i < pointCount; i++) {
            if ((visited & (1 << i)) != 0 && // 지금까지 오면서 방문한 구역인 경우
                    tsp(start, i, visited ^ (1 << i)) != -1 && // 출발 구역 -> i번째 구역 가는 것이 가능한 경우
                    tsp(i, end, (1 << i)) != -1) { // i번째 구역 -> 도착 구역 가는 것이 가능한 경우
                // 최단거리 갱신
                min = Math.min(min, cache[start][i][visited ^ (1 << i)] + cache[i][end][(1 << i)]);
            }
        }

        // 갈 수 없는 경우 -1, 가능한 경우 최단거리 반환
        return cache[start][end][visited] = min != Integer.MAX_VALUE ? min : -1;
    }
}
