class Solution {
	final int[] DELTA_ROW = { -1, 1, 0, 0 };
	final int[] DELTA_COL = { 0, 0, -1, 1 };

	final int EMPTY = 0;
	final int RED_START = 1;
	final int BLUE_START = 2;
	final int RED_DEST = 3;
	final int BLUE_DEST = 4;
	final int WALL = 5;

	final int RED = 0;
	final int BLUE = 1;

	class Point {
		int row, col;

		public Point(int row, int col) {
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

	int rowSize, colSize;
	boolean[][][] isVisited;
	int result;

	public int solution(int[][] maze) {
		int answer = 0;

		rowSize = maze.length;
		colSize = maze[0].length;
		isVisited = new boolean[rowSize][colSize][2];
		Point redPos = null, bluePos = null;

		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				if (maze[row][col] == RED_START) {
					redPos = new Point(row, col);
					isVisited[row][col][RED] = true;
				} else if (maze[row][col] == BLUE_START) {
					bluePos = new Point(row, col);
					isVisited[row][col][BLUE] = true;
				}
			}
		}

		result = Integer.MAX_VALUE;
		solve(redPos, bluePos, 0, maze);

		answer = (result == Integer.MAX_VALUE) ? 0 : result;

		return answer;
	}

	private void solve(Point redPos, Point bluePos, int turn, int[][] maze) {
		// 두 수레가 모두 도착한 경우
		if (isDestination(redPos, RED_DEST, maze) && isDestination(bluePos, BLUE_DEST, maze)) {
			result = Math.min(result, turn);
			return;
		}

		if (result <= turn) {
			return;
		}

		// red 수레는 도착한 경우
		if (isDestination(redPos, RED_DEST, maze)) {
			move(bluePos, redPos, BLUE, turn, maze);
		}
		// blue 수레는 도착한 경우
		else if (isDestination(bluePos, BLUE_DEST, maze)) {
			move(redPos, bluePos, RED, turn, maze);
		}
		// 둘 다 도착하지 않은 경우
		else {
			move(redPos, bluePos, turn, maze);
		}
	}

	private void move(Point curPos, Point other, int color, int turn, int[][] maze) {
		for (int dir = 0; dir < 4; dir++) {
			Point next = new Point(curPos.row + DELTA_ROW[dir], curPos.col + DELTA_COL[dir]);

			// 이동 칸이 범위를 벗어나거나 벽인 경우, 패스
			if (isOutOfBoundOrWall(next, maze)) {
				continue;
			}

			// 다른 수레가 존재하는 경우, 패스
			if (next.equals(other)) {
				continue;
			}

			// 이미 방문한 칸이 경우, 패스
			if (isVisited[next.row][next.col][color]) {
				continue;
			}

			isVisited[next.row][next.col][color] = true;
			if (color == RED) {
				solve(next, other, turn + 1, maze);
			} else {
				solve(other, next, turn + 1, maze);
			}
			isVisited[next.row][next.col][color] = false;
		}
	}

	private void move(Point curR, Point curB, int turn, int[][] maze) {
		for (int dir1 = 0; dir1 < 4; dir1++) {
			for (int dir2 = 0; dir2 < 4; dir2++) {
				Point nextR = new Point(curR.row + DELTA_ROW[dir1], curR.col + DELTA_COL[dir1]);
				Point nextB = new Point(curB.row + DELTA_ROW[dir2], curB.col + DELTA_COL[dir2]);

				// 이동 칸이 범위를 벗어나거나 벽인 경우, 패스
				if (isOutOfBoundOrWall(nextR, maze) || isOutOfBoundOrWall(nextB, maze)) {
					continue;
				}

				// 동시에 같은 칸으로 이동하려는 경우, 패스
				if (nextR.equals(nextB)) {
					continue;
				}

				// 수레끼리 자리를 바꾸는 경우, 패스
				if ((nextR.equals(curB)) && (nextB.equals(curR))) {
					continue;
				}

				// 이미 방문한 칸인 경우, 패스
				if (isVisited[nextR.row][nextR.col][RED] || isVisited[nextB.row][nextB.col][BLUE]) {
					continue;
				}

				isVisited[nextR.row][nextR.col][RED] = true;
				isVisited[nextB.row][nextB.col][BLUE] = true;
				solve(nextR, nextB, turn + 1, maze);
				isVisited[nextR.row][nextR.col][RED] = false;
				isVisited[nextB.row][nextB.col][BLUE] = false;
			}
		}
	}

	private boolean isOutOfBoundOrWall(Point point, int[][] maze) {
		if (point.row < 0 || point.col < 0 || point.row >= rowSize || point.col >= colSize) {
			return true;
		}

		if (maze[point.row][point.col] == WALL) {
			return true;
		}

		return false;
	}

	private boolean isDestination(Point point, int dest, int[][] maze) {
		return (maze[point.row][point.col] == dest);
	}
}