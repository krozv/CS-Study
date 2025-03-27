public class Main {

    private static int max;
    private static int[] map, visited;

    private static final int SIZE_X = 20;
    private static final int SIZE_Y = 10;

    private static final int[][] DIR = {{1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) throws Exception {
        map = new int[SIZE_X];

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (System.in.read() == '1') {
                    map[i] |= (1 << j);
                }
            }
            System.in.read();
        }

        max = 0;
        int blockCount = floor.length;
        for (int i = 0; i < blockCount; i++) {
            visited = new int[SIZE_X];
            visited[0] |= 1;
            dfs(i, 0, 0);
        }

        System.out.println(max);
    }

    private static void dfs(int block, int x, int y) {
        int length, nextX, nextY;
        boolean isAvailable;

        for (int dir = 0; dir < 3; dir++) {
            length = floor[block][dir].length;
            isAvailable = true;

            for (int i = 0; i < length; i++) {
                nextX = x + floor[block][dir][i][0] + DIR[dir][0];
                nextY = y + floor[block][dir][i][1] + DIR[dir][1];
                if (isOutOfBounds(nextX, nextY) || (map[nextX] & (1 << nextY)) != 0) {
                    isAvailable = false;
                }
            }

            if (isAvailable) {
                nextX = x + DIR[dir][0];
                nextY = y + DIR[dir][1];
                if ((visited[nextX] & (1 << nextY)) == 0) {
                    visited[nextX] |= (1 << nextY);
                    dfs(block, nextX, nextY);
                }
            } else if (dir == 0) {
                max = Math.max(max, lineErased(block, x, y));
            }
        }
    }

    private static int lineErased(int block, int x, int y) {
        int[][] pos = shape[block];
        int idx = 0;
        int length = pos.length;

        int line, nowX, count = 0;
        while (idx < length) {
            line = map[x + pos[idx][0]];
            nowX = pos[idx][0];

            while (idx < length && nowX == pos[idx][0]) {
                line |= (1 << (y + pos[idx++][1]));
            }

            if (line == (1 << SIZE_Y) - 1) {
                count++;
            }
        }

        return count;
    }


    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y;
    }

    private static final int[][][] shape = {
            {
                    {0, 0}, {0, 1}, {1, 0}, {2, 0}
            },
            {
                    {0, 0}, {0, 1}, {0, 2}, {1, 2}
            },
            {
                    {0, 1}, {1, 1}, {2, 0}, {2, 1}
            },
            {
                    {0, 0}, {1, 0}, {1, 1}, {1, 2}
            },
            {
                    {0, 0}, {0, 1}, {1, 1}, {2, 1}
            },
            {
                    {0, 2}, {1, 0}, {1, 1}, {1, 2}
            },
            {
                    {0, 0}, {1, 0}, {2, 0}, {2, 1}
            },
            {
                    {0, 0}, {0, 1}, {0, 2}, {1, 0}
            },
            {
                    {0, 0}, {1, 0}, {2, 0}, {3, 0}
            },
            {
                    {0, 0}, {0, 1}, {0, 2}, {0, 3}
            },
            {
                    {0, 0}, {0, 1}, {1, 0}, {1, 1}
            },
            {
                    {0, 0}, {1, 0}, {1, 1}, {2, 1}
            },
            {
                    {0, 1}, {0, 2}, {1, 0}, {1, 1}
            },
            {
                    {0, 1}, {1, 0}, {1, 1}, {2, 0}
            },
            {
                    {0, 0}, {0, 1}, {1, 1}, {1, 2}
            },
            {
                    {0, 0}, {1, 0}, {1, 1}, {2, 0}
            },
            {
                    {0, 0}, {0, 1}, {0, 2}, {1, 1}
            },
            {
                    {0, 1}, {1, 0}, {1, 1}, {2, 1}
            },
            {
                    {0, 1}, {1, 0}, {1, 1}, {1, 2}
            },
    };

    private static final int[][][][] floor = {
            {
                    {{2, 0}, {0, 1}}, {{0, 1}, {1, 0}, {2, 0}}, {{0, 0}, {1, 0}, {2, 0}}
            },
            {
                    {{0, 0}, {0, 1}, {1, 2}}, {{0, 2}, {1, 2}}, {{0, 0}, {1, 2}}
            },
            {
                    {{2, 0}, {2, 1}}, {{0, 1}, {1, 1}, {2, 1}}, {{0, 1}, {1, 1}, {2, 0}}
            },
            {
                    {{1, 0}, {1, 1}, {1, 2}}, {{0, 0}, {1, 2}}, {{0, 0}, {1, 0}}
            },
            {
                    {{0, 0}, {2, 1}}, {{0, 1}, {1, 1}, {2, 1}}, {{0, 0}, {1, 1}, {2, 1}}
            },
            {
                    {{1, 0}, {1, 1}, {1, 2}}, {{0, 2}, {1, 2}}, {{0, 2}, {1, 0}}
            },
            {
                    {{2, 0}, {2, 1}}, {{0, 0}, {1, 0}, {2, 1}}, {{0, 0}, {1, 0}, {2, 0}}
            },
            {
                    {{1, 0}, {0, 1}, {0, 2}}, {{1, 0}, {0, 2}}, {{0, 0}, {1, 0}}
            },
            {
                    {{3, 0}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}}
            },
            {
                    {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 3}}, {{0, 0}}
            },
            {
                    {{1, 0}, {1, 1}}, {{0, 1}, {1, 1}}, {{0, 0}, {1, 0}}
            },
            {
                    {{1, 0}, {2, 1}}, {{0, 0}, {1, 1}, {2, 1}}, {{0, 0}, {1, 0}, {2, 1}}
            },
            {
                    {{0, 2}, {1, 0}, {1, 1}}, {{0, 2}, {1, 1}}, {{0, 1}, {1, 0}}
            },
            {
                    {{1, 1}, {2, 0}}, {{0, 1}, {1, 1}, {2, 0}}, {{0, 1}, {1, 0}, {2, 0}}
            },
            {
                    {{0, 0}, {1, 1}, {1, 2}}, {{0, 1}, {1, 2}}, {{0, 0}, {1, 1}}
            },
            {
                    {{2, 0}, {1, 1}}, {{0, 0}, {1, 1}, {2, 0}}, {{0, 0}, {1, 0}, {2, 0}}
            },
            {
                    {{0, 0}, {0, 2}, {1, 1}}, {{0, 2}, {1, 1}}, {{0, 0}, {1, 1}}
            },
            {
                    {{1, 0}, {2, 1}}, {{0, 1}, {1, 1}, {2, 1}}, {{0, 1}, {1, 0}, {2, 1}}
            },
            {
                    {{1, 0}, {1, 1}, {1, 2}}, {{0, 1}, {1, 2}}, {{0, 1}, {1, 0}}
            }
    };
}
