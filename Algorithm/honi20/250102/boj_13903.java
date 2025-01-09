/**
 * [보도블록 규칙]
 * 1. 세로 블록만 밟는다.
 * 	- 시작은 첫 번째 row의 세로 블록 중 아무 곳에서나 가능하다.
 * 2. 특정 규칙으로 이동한다.
 * 3. 첫 row에서 출발하여 마지막 row에 도착하면 출근이 성공한 것이다.
 * 4. 최소한의 걸음으로 출근을 하고 싶다.
 * 
 * [입력]
 * - 보도블록의 세로, 가로
 * - 보도블록의 초기 상태 (1이 세로 블록)
 * - 이동 가능한 규칙의 개수
 * - 규칙 r, c
 */
import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Point {
		int row;
		int col;
		int step;
		
		public Point(int row, int col, int step) {
			this.row = row;
			this.col = col;
			this.step = step;
		}
	}
	
	static final int ROW_BLOCK = 1;
	static final int ROW = 0;
	static final int COL = 1;
	
	static int blockRow, blockCol;
	static int[][] block;
	static int ruleCnt;
	static int[][] rule;
	
//	static Queue<Point> queue;
//	static boolean[][] isVisited;
	static int minResult = -1;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine().trim());
		blockRow = Integer.parseInt(st.nextToken());
		blockCol = Integer.parseInt(st.nextToken());
		
		block = new int[blockRow][blockCol];
		for (int row = 0; row < blockRow; row++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int col = 0; col < blockCol; col++) {
				block[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		ruleCnt = Integer.parseInt(br.readLine().trim());
		
		rule = new int[ruleCnt][2];
		for (int idx = 0; idx < ruleCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			rule[idx][ROW] = Integer.parseInt(st.nextToken());
			rule[idx][COL] = Integer.parseInt(st.nextToken());
		}
		
		goToWork();
		
		System.out.println(minResult);
	}
	
	private static void goToWork() {
		Queue<Point> queue = new ArrayDeque<>();
		boolean[][] isVisited = new boolean[blockRow][blockCol];

		// 첫 번째 row에서 세로 블록인 칸을 모두 queue에 넣는다.
		for (int col = 0; col < blockCol; col++) {
			if (block[0][col] == ROW_BLOCK) {
				queue.add(new Point(0, col, 0));
			}
		}
		
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			int curRow = point.row;
			int curCol = point.col;
			int curStep = point.step;
		
			// 마지막 행에 도착한 경우, 결괏값 갱신 후 종료
			if (curRow == blockRow - 1) {
				minResult = curStep;
				break;
			}
			
			// 규칙에 따라 갈 수 있는 칸을 탐색한다.
			for (int idx = 0; idx < ruleCnt; idx++) {
				int nextRow = curRow + rule[idx][ROW];
				int nextCol = curCol + rule[idx][COL];
				
				// 범위를 벗어나는 경우, 패스
				if (nextRow < 0 || nextCol < 0 || nextRow >= blockRow || nextCol >= blockCol) {
					continue;
				}
				
				// 세로 블록이 아닌 경우, 패스
				if (block[nextRow][nextCol] != ROW_BLOCK) {
					continue;
				}
				
				// 이미 방문한 블록인 경우, 패스
				if (isVisited[nextRow][nextCol]) {
					continue;
				}
		
				isVisited[nextRow][nextCol] = true;
				queue.offer(new Point(nextRow, nextCol, curStep + 1));
			}
		}
	}
}