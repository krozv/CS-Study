import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int size, zombieCount;

    private static boolean[][] isWall;

    private static StringTokenizer st;

    private static Player player;
    private static Zombie[] zombies;

    private static final int[][] DIR = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {0, 0}};

    // 플레이어의 이동 명령과 현재 위치를 저장
    private static class Player {
        String command;
        int x;
        int y;

        public Player(String command, String pos) {
            this.command = command;

            st = new StringTokenizer(pos);
            this.x = Integer.parseInt(st.nextToken()) - 1;
            this.y = Integer.parseInt(st.nextToken()) - 1;
        }
    }

    // 좀비의 위치, 등급, 방향, 속도를 저장
    private static class Zombie {
        int x;
        int y;
        boolean isAdvanced;
        int direction;
        int speed;

        public Zombie(String info) {
            st = new StringTokenizer(info);
            this.x = Integer.parseInt(st.nextToken()) - 1;
            this.y = Integer.parseInt(st.nextToken()) - 1;
            this.isAdvanced = st.nextToken().equals("1");
            this.direction = toDir(st.nextToken().toCharArray()[0]);
            this.speed = Integer.parseInt(st.nextToken());
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        size = Integer.parseInt(br.readLine());
        isWall = new boolean[size][size];

        player = new Player(br.readLine(), br.readLine());

        int wallCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < wallCount; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            isWall[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = true;
        }

        zombieCount = Integer.parseInt(br.readLine());
        zombies = new Zombie[zombieCount];
        for (int i = 0; i < zombieCount; i++) {
            zombies[i] = new Zombie(br.readLine());
        }

        int finalDay = Integer.parseInt(br.readLine());
        for (int i = 0; i < finalDay; i++) {
            if (isDead(i)) {
                sb.append(i + 1).append("\n").append("DEAD...");
                System.out.println(sb);
                return;
            }
        }

        System.out.println("ALIVE!");
    }

    // 하루 진행 후 생존 여부 반환
    private static boolean isDead(int turn) {
        movePlayer(turn);

        for (int i = 0; i < zombieCount; i++) {
            if (moveZombie(i)) {
                return true;
            }
        }

        return false;
    }

    // 플레이어 이동
    private static void movePlayer(int turn) {
        char command = player.command.charAt(turn);
        int dir = toDir(command);

        int nextX = player.x + DIR[dir][0];
        int nextY = player.y + DIR[dir][1];

        if (isOutOfBounds(nextX, nextY) || isWall[nextX][nextY]) {
            return;
        }

        player.x = nextX;
        player.y = nextY;
    }

    // idx번 좀비가 이동하고, 플레이어 조우 여부를 반환
    private static boolean moveZombie(int idx) {
        Zombie zombie = zombies[idx];

        int nowX = zombie.x;
        int nowY = zombie.y;
        int speed = zombie.speed;

        while (!isOutOfBounds(nowX, nowY) && !isWall[nowX][nowY] && speed-- >= 0) {
            zombie.x = nowX;
            zombie.y = nowY;
            nowX = zombie.x + DIR[zombie.direction][0];
            nowY = zombie.y + DIR[zombie.direction][1];
        }

        if (zombie.isAdvanced) {
            if (speed >= 0 && !isOutOfBounds(nowX, nowY) && isWall[nowX][nowY]){
                isWall[nowX][nowY] = false;
            }

            zombie.direction = countWall(zombie.x, zombie.y);
        } else {
            if (speed >= 0) {
                zombie.direction = (zombie.direction + 2) % 4;
            }
        }

        if (zombie.x == player.x && zombie.y == player.y) {
            return true;
        }

        if (zombie.isAdvanced) {
            zombie.direction = countWall(zombie.x, zombie.y);
        }

        return false;
    }

    private static int countWall(int x, int y) {
        int dir = 0;
        int max = 0;

        int nowX, nowY, count;
        for (int i = 0; i < 4; i++) {
            count = 0;
            nowX = x;
            nowY = y;

            while (!isOutOfBounds(nowX, nowY)) {
                if (isWall[nowX][nowY]) {
                    count++;
                }
                nowX += DIR[i][0];
                nowY += DIR[i][1];
            }

            if (count > max) {
                dir = i;
                max = count;
            }
        }
        return dir;
    }

    private static int toDir(char dir) {
        if (dir == 'U') {
            return 0;
        } else if (dir == 'R') {
            return 1;
        } else if (dir == 'D') {
            return 2;
        } else if (dir == 'L') {
            return 3;
        } else {
            return 4;
        }
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= size || y >= size;
    }
}
