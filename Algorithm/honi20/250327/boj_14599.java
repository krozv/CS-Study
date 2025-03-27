import java.io.*;
import java.util.*;

public class Main_14599 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int BLOCK_TYPE = 19;
	static final int[][][] BLOCK = { { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } },
			{ { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } },
			{ { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 0 } }, { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } },
			{ { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 2 } }, { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } },
			{ { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }, { { 0, 1 }, { 0, 2 }, { 1, 0 }, { 1, 1 } },
			{ { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } }, { { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 } },
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } }, { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } },
			{ { 0, 1 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } } };

	static final int ROW_SIZE = 20;
	static final int COL_SIZE = 10;
	static final int ROW = 0;
	static final int COL = 1;
	static final int EMPTY = 0;
	static final int FULL = 1;

	static class Point {
		int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	static int[][] arr;
	static int result;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		arr = new int[ROW_SIZE][COL_SIZE];
		for (int row = 0; row < ROW_SIZE; row++) {
			String str = br.readLine().trim();

			for (int col = 0; col < COL_SIZE; col++) {
				arr[row][col] = str.charAt(col) - '0';
			}
		}

		result = 0;

		// 하나의 도형의 모양이 선택됨.
		for (int type = 0; type < BLOCK_TYPE; type++) {
			solve(type);
		}

		System.out.println(result);
	}

	private static void solve(int type) {
		Queue<Point> queue = new ArrayDeque<>();
		boolean[][] isVisited = new boolean[ROW_SIZE][COL_SIZE];

		for (int col = 0; col < COL_SIZE; col++) {
			if (isAvail(0, col, type)) {
				queue.offer(new Point(0, col));
			}
		}

		while (!queue.isEmpty()) {
			Point point = queue.poll();
			int curRow = point.row;
			int curCol = point.col;

			if (isVisited[curRow][curCol]) {
				continue;
			}

			// 좌우로 갈 수 있는 부분 있는지 확인
			for (int col = 0; col < COL_SIZE; col++) {
				if (isAvail(curRow, col, type) && !isVisited[curRow][col]) {
					// 한 칸 아래로 갈 수 있는 경우
					if (isAvail(curRow + 1, col, type) && !isVisited[curRow + 1][col]) {
						queue.offer(new Point(curRow + 1, col));
					}
					// 더 이상 내려갈 수 없는 경우
					else {
						changeArr(curRow, col, type, FULL);
						result = Math.max(result, getLineCnt());
						changeArr(curRow, col, type, EMPTY);
					}
				}
			}

			isVisited[curRow][curCol] = true;
		}
	}

	private static boolean isAvail(int curRow, int curCol, int curIdx) {
		for (int block = 0; block < 4; block++) {
			int nextRow = curRow + BLOCK[curIdx][block][ROW];
			int nextCol = curCol + BLOCK[curIdx][block][COL];

			// 범위를 벗어나는 경우
			if (nextRow < 0 || nextCol < 0 || nextRow >= ROW_SIZE || nextCol >= COL_SIZE) {
				return false;
			}

			// 이미 블록으로 차있는 경우
			if (arr[nextRow][nextCol] == FULL) {
				return false;
			}
		}

		return true;
	}

	private static void changeArr(int curRow, int curCol, int curIdx, int val) {
		for (int block = 0; block < 4; block++) {
			int nextRow = curRow + BLOCK[curIdx][block][ROW];
			int nextCol = curCol + BLOCK[curIdx][block][COL];

			arr[nextRow][nextCol] = val;
		}
	}

	private static int getLineCnt() {
		int cnt = 0;
		for (int row = ROW_SIZE - 1; row >= 0; row--) {
			boolean isFull = true;
			for (int col = 0; col < COL_SIZE; col++) {
				if (arr[row][col] == EMPTY) {
					isFull = false;
					break;
				}
			}

			if (isFull) {
				++cnt;
			}
		}

		return cnt;
	}
}
