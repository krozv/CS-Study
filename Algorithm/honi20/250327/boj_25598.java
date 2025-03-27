import java.io.*;
import java.util.*;

public class Main_25598 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int[] DELTA_ROW = { -1, 0, 1, 0, 0 };
	static final int[] DELTA_COL = { 0, 1, 0, -1, 0 };

	static final int EMPTY = 0;
	static final int WALL = 1;
	static final int LOWER_ZOMBI = 0;
	static final int UPPER_ZOMBI = 1;

	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	static final int STAY = 4;

	static class Point {
		int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public void update(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Point) {
				return (this.row == ((Point) o).row) && (this.col == ((Point) o).col);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return this.row + this.col;
		}
	}

	static class Zombi {
		Point point;
		int type;
		int direct;
		int dist;

		public Zombi(Point point, int type, int direct, int dist) {
			this.point = point;
			this.type = type;
			this.direct = direct;
			this.dist = dist;
		}

		public void updateDirect(int direct) {
			this.direct = direct;
		}
	}

	static int mapSize;
	static int[][] map;
	static String order;
	static Point player;
	static int zombiCnt;
	static Zombi[] zombi;
	static int query;

	public static void main(String[] args) throws Exception {
		input();

		for (int idx = 0; idx < query; idx++) {
			// 1. 플레이어 명령 실행
			movePlayer(idx);

			// 2. 좀비 순서대로 이동
			for (int z = 0; z < zombiCnt; z++) {
				moveZombi(zombi[z]);

				// 3. 이동 후 좀비의 위치가 플레이어의 위치와 같다면, 게임 종료
				if (player.equals(zombi[z].point)) {
					System.out.println((idx + 1) + "\nDEAD...");
					return;
				}
			}
		}

		System.out.println("ALIVE!");
	}

	private static void movePlayer(int index) {
		int dir = getDirect(order.charAt(index));
		int nextRow = player.row + DELTA_ROW[dir];
		int nextCol = player.col + DELTA_COL[dir];

		// 게임 필드를 벗어나는 경우
		if (nextRow <= 0 || nextCol <= 0 || nextRow > mapSize || nextCol > mapSize) {
			return;
		}

		// 벽인 경우
		if (map[nextRow][nextCol] == WALL) {
			return;
		}

		player.update(nextRow, nextCol);
	}

	private static void moveZombi(Zombi zombi) {
		boolean isFail = false;

		// 1) 현재 바라보는 방향으로 dist만큼 이동
		for (int idx = 0; idx < zombi.dist; idx++) {
			int nextRow = zombi.point.row + DELTA_ROW[zombi.direct];
			int nextCol = zombi.point.col + DELTA_COL[zombi.direct];

			// 게임 필드를 벗어나는 경우
			if (nextRow <= 0 || nextCol <= 0 || nextRow > mapSize || nextCol > mapSize) {
				isFail = true;
				break;
			}

			// 벽인 경우
			if (map[nextRow][nextCol] == WALL) {
				// 상급 좀비인 경우, 해당 벽 제거
				if (zombi.type == UPPER_ZOMBI) {
					map[nextRow][nextCol] = EMPTY;
				}
				isFail = true;
				break;
			}

			zombi.point.update(nextRow, nextCol);
		}

		// 2) 방향 변경
		// 2-1) 하급 좀비인 경우, 중간에 이동을 멈춘 경우에만 방향 변경
		if (zombi.type == LOWER_ZOMBI) {
			if (isFail) {
				zombi.updateDirect(reverseDir(zombi.direct));
			}
		}

		// 2-2) 상급 좀비인 경우, 상우하좌 중 벽이 많은 방향으로 변경
		else {
			int maxCnt = -1;
			for (int dir = 0; dir < 4; dir++) {
				int cnt = getWallCnt(zombi.point, dir);

				if (cnt > maxCnt) {
					zombi.updateDirect(dir);
					maxCnt = cnt;
				}
			}
		}
	}

	private static int getWallCnt(Point point, int dir) {
		int cnt = 0;
		int curRow = point.row;
		int curCol = point.col;

		while (true) {
			curRow += DELTA_ROW[dir];
			curCol += DELTA_COL[dir];

			// 범위를 벗어나는 경우
			if (curRow <= 0 || curCol <= 0 || curRow > mapSize || curCol > mapSize) {
				break;
			}

			// 벽인 경우
			if (map[curRow][curCol] == WALL) {
				++cnt;
			}
		}

		return cnt;
	}

	private static int reverseDir(int dir) {
		return (dir + 2) % 4;
	}

	private static void input() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		mapSize = Integer.parseInt(br.readLine().trim());
		map = new int[mapSize + 1][mapSize + 1];

		order = br.readLine().trim();

		st = new StringTokenizer(br.readLine().trim());
		player = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

		int wallCnt = Integer.parseInt(br.readLine().trim());
		while (wallCnt-- > 0) {
			st = new StringTokenizer(br.readLine().trim());
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			map[row][col] = WALL;
		}

		zombiCnt = Integer.parseInt(br.readLine().trim());
		zombi = new Zombi[zombiCnt];
		for (int idx = 0; idx < zombiCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			int type = Integer.parseInt(st.nextToken());
			int direct = getDirect(st.nextToken().charAt(0));
			int dist = Integer.parseInt(st.nextToken());

			zombi[idx] = new Zombi(new Point(row, col), type, direct, dist);
		}

		query = Integer.parseInt(br.readLine().trim());
	}

	private static int getDirect(char dir) {
		switch (dir) {
		case 'U':
			return UP;
		case 'R':
			return RIGHT;
		case 'D':
			return DOWN;
		case 'L':
			return LEFT;
		case 'S':
			return STAY;
		}

		return -1;
	}
}