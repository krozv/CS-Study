import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj13549 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[200000];
        Queue<int[]> bfsQueue = new ArrayDeque<>();
        visited[start] = true;
        bfsQueue.add(new int[]{start, 0});

        int[] now;
        int location, next, min = Math.abs(end - start);
        while (!bfsQueue.isEmpty()) {
            now = bfsQueue.poll();
            if (now[1] >= min) {
                continue;
            }

            location = now[0];
            if (location == end) {
                min = now[1];
                continue;
            }

            while (location < end) {
                visited[location] = true;

                next = location - 1;
                if (next >= 0 && !visited[next]) {
                    visited[next] = true;
                    bfsQueue.add(new int[]{next, now[1] + 1});
                }

                next = location + 1;
                if (next <= 100000 && !visited[next]) {
                    visited[next] = true;
                    bfsQueue.add(new int[]{next, now[1] + 1});
                }

                if (location == 0) {
                    break;
                }
                location *= 2;
            }

            if (location != 0 && now[1] + (location - end) < min) {
                min = now[1] + (location - end);
            }
        }
        System.out.println(min);
    }
}
