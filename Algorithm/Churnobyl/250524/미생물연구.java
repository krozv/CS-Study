import java.io.*;
import java.util.*;

public class Main {
    static final int MAX = 100;
    static int N, Q;
    static int[][] MAP = new int[MAX][MAX];

    static class Query {
        int r1, c1, r2, c2;
        Query(int r1, int c1, int r2, int c2) {
            this.r1 = r1; this.c1 = c1; this.r2 = r2; this.c2 = c2;
        }
    }
    static Query[] queries;

    static class Micro {
        int id, minR, minC, maxR, maxC, count;
        Micro(int id, int minR, int minC, int maxR, int maxC, int count) {
            this.id = id; this.minR = minR; this.minC = minC;
            this.maxR = maxR; this.maxC = maxC; this.count = count;
        }
    }
    static List<Micro> micros;
    static boolean[] dead;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    static void input(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        queries = new Query[Q + 1];
        for (int q = 1; q <= Q; q++) {
            st = new StringTokenizer(br.readLine());
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());
            queries[q] = new Query(r1, c1, r2, c2);
        }
    }

    static void insert(int id, int r1, int c1, int r2, int c2) {
        for (int r = r1; r < r2; r++)
            for (int c = c1; c < c2; c++)
                MAP[r][c] = id;
    }

    static boolean[][] visit = new boolean[MAX][MAX];

    static Micro bfs(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{r, c});
        visit[r][c] = true;
        int minR = r, minC = c, maxR = r, maxC = c;
        int cnt = 1;
        int id = MAP[r][c];

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (MAP[nr][nc] != id || visit[nr][nc]) continue;
                visit[nr][nc] = true;
                q.offer(new int[]{nr, nc});
                cnt++;
                minR = Math.min(minR, nr);
                minC = Math.min(minC, nc);
                maxR = Math.max(maxR, nr);
                maxC = Math.max(maxC, nc);
            }
        }
        return new Micro(id, minR, minC, maxR, maxC, cnt);
    }

    static void findLiveMicro() {
        micros = new ArrayList<>();
        dead = new boolean[MAX];
        boolean[] check = new boolean[MAX];

        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                visit[r][c] = false;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int id = MAP[r][c];
                if (id == 0 || dead[id] || visit[r][c]) continue;
                Micro m = bfs(r, c);
                if (check[id]) {
                    dead[id] = true;
                    continue;
                }
                check[id] = true;
                micros.add(m);
            }
        }
        List<Micro> alive = new ArrayList<>();
        for (Micro m : micros)
            if (!dead[m.id]) alive.add(m);
        micros = alive;
    }

    static void sortMicro() {
        micros.sort((a, b) -> {
            if (a.count != b.count) return b.count - a.count;
            return a.id - b.id;
        });
    }

    static boolean checkMove(int[][] newMAP, Micro m, int fr, int fc) {
        for (int r = m.minR; r <= m.maxR; r++) {
            for (int c = m.minC; c <= m.maxC; c++) {
                if (MAP[r][c] != m.id) continue;
                int newR = fr - m.minR + r;
                int newC = fc - m.minC + c;
                if (newR >= N || newC >= N) return false;
                if (newMAP[newR][newC] != 0) return false;
            }
        }
        return true;
    }

    static void move(int[][] newMAP, Micro m, int fr, int fc) {
        for (int r = m.minR; r <= m.maxR; r++)
            for (int c = m.minC; c <= m.maxC; c++)
                if (MAP[r][c] == m.id)
                    newMAP[fr - m.minR + r][fc - m.minC + c] = m.id;
    }

    static void moveMicro(int[][] newMAP, Micro m) {
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                if (checkMove(newMAP, m, r, c)) {
                    move(newMAP, m, r, c);
                    return;
                }
    }

    static void moveAll() {
        int[][] newMAP = new int[MAX][MAX];
        for (Micro m : micros)
            moveMicro(newMAP, m);
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++)
                MAP[r][c] = newMAP[r][c];
    }

    static int getCount(int id) {
        for (Micro m : micros)
            if (m.id == id) return m.count;
        return 0;
    }

    static int getScore(int maxID) {
        boolean[][] company = new boolean[MAX][MAX];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (MAP[r][c] == 0) continue;
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d], nc = c + dc[d];
                    if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                    int id1 = MAP[r][c];
                    int id2 = MAP[nr][nc];
                    if (id1 == id2 || id2 == 0) continue;
                    company[id1][id2] = true;
                    company[id2][id1] = true;
                }
            }
        }
        int score = 0;
        for (int i = 1; i <= maxID - 1; i++)
            for (int k = i + 1; k <= maxID; k++)
                if (company[i][k])
                    score += getCount(i) * getCount(k);
        return score;
    }

    static void simulate() {
        for (int id = 1; id <= Q; id++) {
            Query q = queries[id];
            insert(id, q.r1, q.c1, q.r2, q.c2); // 미생물 투입
            findLiveMicro();
            sortMicro(); // 큰 군집, 번호순 정렬
            moveAll(); // 다 움직이기
            System.out.println(getScore(id)); // 결과 출력
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input(br);
        simulate();
    }
}
