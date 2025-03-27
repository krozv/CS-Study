import java.io.*;
import java.util.*;

public class Main {
    static int N, D;
    static boolean[][] map;
    static List<int[]> characters = new ArrayList<>();
    static char[] commands;
    static Map<Character, int[]> directions = new HashMap<>();
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        setting();
        run();
    }

    private static void run() {
        boolean isDead = false;

        for (int i = 1; i <= D; i++) {
            if (moveAll(i)) {
                isDead = true;
                System.out.println(i);
                break;
            }
        }

        if (isDead) {
            System.out.println("DEAD...");
        } else {
            System.out.println("ALIVE!");
        }
    }

    private static boolean moveAll(int day) {
        for (int[] character : characters) {
            if (character[0] == 0) {
                char com = commands[day - 1];
                int[] dir = directions.get(com);

                int ny = character[1] + dir[0];
                int nx = character[2] + dir[1];

                if (checkBoundary(ny, nx) && !checkWall(ny, nx)) {
                    character[1] = ny;
                    character[2] = nx;
                }
            } else if (character[0] == 1) {
                int[] dir = directions.get((char) character[3]);
                for (int i = 0; i < character[4]; i++) {
                    int ny = character[1] + dir[0];
                    int nx = character[2] + dir[1];

                    if (checkBoundary(ny, nx) && !checkWall(ny, nx)) {
                        character[1] = ny;
                        character[2] = nx;
                    } else {
                        changeDirection(character);
                        break;
                    }
                }

                if (checkDead(character)) {
                    return true;
                }
            } else if (character[0] == 2) {
                int[] dir = directions.get((char) character[3]);
                for (int i = 0; i < character[4]; i++) {
                    int ny = character[1] + dir[0];
                    int nx = character[2] + dir[1];

                    if (checkBoundary(ny, nx)) {
                        if (!checkWall(ny, nx)) {
                            character[1] = character[1] + dir[0];
                            character[2] = character[2] + dir[1];
                        } else {
                            map[ny][nx] = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }

                if (checkDead(character)) {
                    return true;
                }
                changeDirectionForAdvanced(character);
            }
        }

        return false;
    }

    private static boolean checkDead(int[] character) {
        int[] player = characters.get(0);
        return player[1] == character[1] && player[2] == character[2];
    }

    private static void changeDirectionForAdvanced(int[] character) {
        int dir = 0;
        int cnt = 0;

        for (int i = 0; i < 4; i++) {
            int cache = 0;
            int cacheY = character[1];
            int cacheX = character[2];

            while (checkBoundary(cacheY + dy[i], cacheX + dx[i])) {
                cacheY += dy[i];
                cacheX += dx[i];

                if (checkWall(cacheY, cacheX)) {
                    cache++;
                }
            }

            if (cache > cnt) {
                dir = i;
                cnt = cache;
            }
        }

        changeDirection(character, dir);
    }

    private static void changeDirection(int[] character) {
        if (character[3] == 'U') character[3] = 'D';
        else if (character[3] == 'D') character[3] = 'U';
        else if (character[3] == 'L') character[3] = 'R';
        else if (character[3] == 'R') character[3] = 'L';
    }

    private static void changeDirection(int[] character, int dir) {
        switch (dir) {
            case 0:
                character[3] = 'U';
                break;
            case 1:
                character[3] = 'R';
                break;
            case 2:
                character[3] = 'D';
                break;
            case 3:
                character[3] = 'L';
                break;
        }
    }

    private static boolean checkWall(int y, int x) {
        return map[y][x];
    }

    private static boolean checkBoundary(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    private static void setting() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new boolean[N][N];

        commands = br.readLine().toCharArray();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken()) - 1;
        int x = Integer.parseInt(st.nextToken()) - 1;

        characters.add(new int[] {0, y, x, 'S', 1}); // 종류, y, x, 방향, 이동횟수

        int W = Integer.parseInt(br.readLine());

        for (int i = 0; i < W; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            map[r][c] = true;
        }

        int Z = Integer.parseInt(br.readLine());

        for (int i = 0; i < Z; i++) {
            st = new StringTokenizer(br.readLine());
            y = Integer.parseInt(st.nextToken()) - 1;
            x = Integer.parseInt(st.nextToken()) - 1;
            int species = Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);
            int velocity = Integer.parseInt(st.nextToken());

            characters.add(new int[] {(species == 0 ? 1 : 2), y, x, dir, velocity});
        }

        D = Integer.parseInt(br.readLine());

        directions.put('U', new int[] {-1, 0});
        directions.put('D', new int[] {1, 0});
        directions.put('L', new int[] {0, -1});
        directions.put('R', new int[] {0, 1});
        directions.put('S', new int[] {0, 0});
    }
}