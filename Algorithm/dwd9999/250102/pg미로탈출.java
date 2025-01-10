import java.util.ArrayDeque;
import java.util.Queue;

public class pg미로탈출 {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        String[] maps = new String[]{"LOOXS", "OOOOX", "OOOOO", "OOOOO", "EOOOO"};

        System.out.println(solution.solution(maps));
    }

    private static class Solution {
        private final static int[] moveX = {0, 1, 0, -1};
        private final static int[] moveY = {1, 0, -1, 0};

        public int solution(String[] maps) {
            int X = maps.length;
            int Y = maps[0].length();

            int[] start = new int[2];
            int[] end = new int[2];
            int[] lever = new int[2];

            boolean[][] map = new boolean[X][Y];
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    switch (maps[i].charAt(j)) {
                        case 'X':
                            map[i][j] = true;
                            break;
                        case 'S':
                            start[0] = i;
                            start[1] = j;
                            break;
                        case 'L':
                            lever[0] = i;
                            lever[1] = j;
                            break;
                        case 'E':
                            end[0] = i;
                            end[1] = j;
                            break;
                    }
                }
            }

            boolean[][][] visited = new boolean[X][Y][2];
            Queue<int[]> bfsQueue = new ArrayDeque<>();
            bfsQueue.add(new int[]{start[0], start[1], 0, 0});
            visited[start[0]][start[1]][0] = true;

            int[] now;
            int nextX, nextY;
            while (!bfsQueue.isEmpty()) {
                now = bfsQueue.poll();
                for (int dir = 0; dir < 4; dir++) {
                    nextX = now[0] + moveX[dir];
                    nextY = now[1] + moveY[dir];
                    if (nextX >= 0 && nextY >= 0 && nextX < X && nextY < Y &&
                            !visited[nextX][nextY][now[2]] && !map[nextX][nextY]) {
                        if (nextX == end[0] && nextY == end[1] && now[2] == 1) {
                            return now[3] + 1;
                        }
                        visited[nextX][nextY][now[2]] = true;
                        if (nextX == lever[0] && nextY == lever[1]) {
                            visited[nextX][nextY][1] = true;
                            bfsQueue.add(new int[]{nextX, nextY, 1, now[3] + 1});
                        } else {
                            bfsQueue.add(new int[]{nextX, nextY, now[2], now[3] + 1});
                        }
                    }
                }
            }

            return -1;
        }
    }
}