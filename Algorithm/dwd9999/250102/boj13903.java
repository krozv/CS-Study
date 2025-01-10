import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj13903 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        boolean[][] map = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < y; j++) {
                if (st.nextToken().equals("1")) {
                    map[i][j] = true;
                }
            }
        }

        int moveX, moveY;
        int moveCount = Integer.parseInt(br.readLine());
        int[][] moveList = new int[moveCount][2];
        for (int i = 0; i < moveCount; i++) {
            st = new StringTokenizer(br.readLine());
            moveX = Integer.parseInt(st.nextToken());
            moveY = Integer.parseInt(st.nextToken());
            moveList[i][0] = moveX;
            moveList[i][1] = moveY;
        }


        boolean[][] visited = new boolean[x][y];
        Queue<int[]> bfsQueue = new ArrayDeque<>();
        for (int i = 0; i < y; i++) {
            if (map[0][i]) {
                if (x == 1) {
                    System.out.println(0);
                    return;
                }
                bfsQueue.add(new int[]{0, i, 0});
                visited[0][i] = true;
            }
        }

        int nextX, nextY;
        int[] now;
        while (!bfsQueue.isEmpty()) {
            now = bfsQueue.poll();
            for (int move = 0; move < moveCount; move++) {
                nextX = now[0] + moveList[move][0];
                nextY = now[1] + moveList[move][1];
                if (nextX >= 0 && nextY >= 0 && nextX < x && nextY < y && map[nextX][nextY] && !visited[nextX][nextY]) {
                    if (nextX == x - 1) {
                        System.out.println(now[2] + 1);
                        return;
                    }
                    visited[nextX][nextY] = true;
                    bfsQueue.add(new int[]{nextX, nextY, now[2] + 1});
                }
            }
        }
        System.out.println(-1);
    }

}
