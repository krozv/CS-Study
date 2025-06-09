import java.io.*;
import java.util.*;

public class Main {
    static class Group implements Comparable<Group> {
        int[] reader;
        int readersFaith;
        int bit;
        List<int[]> member = new ArrayList<>();

        @Override
        public int compareTo(Group o) {
            if (this.readersFaith != o.readersFaith)
                return Integer.compare(o.readersFaith, this.readersFaith);
            if (this.reader[0] != o.reader[0])
                return Integer.compare(this.reader[0], o.reader[0]);
            return Integer.compare(this.reader[1], o.reader[1]);
        }
    }

    static int N, T;
    static int[][] F;
    static int[][] B;
    static TreeSet<Group>[] groups = new TreeSet[4];
    static boolean[][] visited;
    static boolean[][] defense;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        setting();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < T; i++) {
            morning();
            lunch();
            evening();
            int[] total = getTotal();

            for (int j = 0; j < 7; j++) {
                sb.append(total[j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    private static int[] getTotal() {
        int[] faiths = new int[8];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                faiths[F[i][j]] += B[i][j];
            }
        }
        return new int[] {faiths[7], faiths[3], faiths[5], faiths[6], faiths[4], faiths[2], faiths[1]};
    }

    private static void evening() {
        spread(groups[1]);
        spread(groups[2]);
        spread(groups[3]);
        for (int i = 0; i < N; i++) Arrays.fill(defense[i], false);
    }

    private static void spread(TreeSet<Group> group) {
        List<Group> toSpread = new ArrayList<>(group);

        for (Group g : toSpread) {
            int gr = g.reader[0], gc = g.reader[1];

            if (defense[gr][gc]) continue;

            int x = B[gr][gc] - 1;
            int dir = B[gr][gc] % 4;
            B[gr][gc] = 1;
            int sr = gr, sc = gc;

            while (x > 0) {
                int nr = sr + dy[dir], nc = sc + dx[dir];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) break;

                if (F[nr][nc] == g.bit) {
                    sr = nr; sc = nc;
                    continue;
                }
                defense[nr][nc] = true;

                if (x > B[nr][nc]) {
                    x -= (B[nr][nc] + 1);
                    B[nr][nc]++;
                    changeGroup(nr, nc, F[nr][nc], g.bit);
                    F[nr][nc] = g.bit;
                    sr = nr; sc = nc;
                    if (x <= 0) break;
                } else {
                    B[nr][nc] += x;
                    changeGroup(nr, nc, F[nr][nc], g.bit | F[nr][nc]);
                    F[nr][nc] = (g.bit | F[nr][nc]);
                    x = 0;
                    break;
                }
            }
        }
    }


    private static void changeGroup(int memY, int memX, int oldBit, int newBit) {
        int oldCnt = Integer.bitCount(oldBit);
        int newCnt = Integer.bitCount(newBit);

        Iterator<Group> iter = groups[oldCnt].iterator();
        while (iter.hasNext()) {
            Group g = iter.next();
            if (g.bit == oldBit) {
                for (int i = 0; i < g.member.size(); i++) {
                    int[] a = g.member.get(i);
                    if (a[0] == memY && a[1] == memX) {
                        g.member.remove(i);
                        if (g.member.isEmpty()) iter.remove();
                        break;
                    }
                }
                break;
            }
        }
        Group found = null;
        for (Group g : groups[newCnt]) {
            if (g.bit == newBit) {
                found = g; break;
            }
        }
        if (found == null) {
            found = new Group();
            found.bit = newBit;
            found.member = new ArrayList<>();
            found.reader = new int[]{memY, memX};
            found.readersFaith = B[memY][memX];
            groups[newCnt].add(found);
        }
        found.member.add(new int[]{memY, memX});
    }

    private static void lunch() {
        for (int i = 1; i < 4; i++) {
            groups[i] = new TreeSet<>();
        }

        visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    Group group = bfs(i, j);
                    giveFaithToReader(group);
                    groups[Integer.bitCount(group.bit)].add(group);
                }
            }
        }
    }

    private static void giveFaithToReader(Group group) {
        if (group.member.size() == 1) {
            group.reader = group.member.get(0);
            group.readersFaith = B[group.reader[0]][group.reader[1]];
            return;
        }
        int[] read = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int candiFaith = Integer.MIN_VALUE;

        for (int[] m : group.member) {
            if (B[m[0]][m[1]] > candiFaith) {
                read = m.clone();
                candiFaith = B[m[0]][m[1]];
            } else if (B[m[0]][m[1]] == candiFaith && m[0] < read[0]) {
                read = m.clone();
            } else if (B[m[0]][m[1]] == candiFaith && m[0] == read[0] && m[1] < read[1]) {
                read = m.clone();
            }
        }
        group.reader = read;

        for (int[] m : group.member) {
            if (m[0] == read[0] && m[1] == read[1]) continue;
            B[m[0]][m[1]]--;
        }
        B[read[0]][read[1]] += (group.member.size() - 1);
        group.readersFaith = B[read[0]][read[1]];
    }

    private static Group bfs(int y, int x) {
        visited[y][x] = true;
        Group group = new Group();
        group.bit = F[y][x];
        group.member.add(new int[] {y, x});

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {y, x});
        while (!queue.isEmpty()) {
            int[] nxt = queue.poll();
            for (int i = 0; i < 4; i++) {
                int ny = nxt[0] + dy[i], nx = nxt[1] + dx[i];
                if (ny < 0 || ny >= N || nx < 0 || nx >= N || visited[ny][nx]) continue;
                if (F[ny][nx] != group.bit) continue;
                visited[ny][nx] = true;
                group.member.add(new int[] {ny, nx});
                queue.add(new int[] {ny, nx});
            }
        }
        return group;
    }

    private static void morning() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                B[i][j]++;
    }

    public static void setting() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        F = new int[N][N];
        B = new int[N][N];
        defense = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                char faith = line.charAt(j);
                switch (faith) {
                    case 'T': F[i][j] = 1; break;
                    case 'C': F[i][j] = 2; break;
                    case 'M': F[i][j] = 4; break;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                B[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
