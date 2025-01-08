/**
 * [조건]
 * - 클릭 시 본인 + 인접 4칸의 색 변경
 * 
 * [출력]
 * - 3x3인 흰 보드를 입력 보드로 바꾸는 데 필요한 최소 클릭 
 */
import java.io.*;
import java.util.*;

public class Main_10472 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Board {
		int state;	// 보드 상태
		int cnt;	// 뒤집기 횟수
		
		public Board(int state, int cnt) {
			this.state = state;
			this.cnt = cnt;
		}
	}
	
	static final int ROW = 3;
	static final int COL = 3;
	static final char BLACK = '*';
	static final char WHITE = '.';
	
	static final int[] DELTA_ROW = {0,-1,1,0,0};
	static final int[] DELTA_COL = {0,0,0,-1,1};
	
	static int testCase;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		testCase = Integer.parseInt(br.readLine().trim());
		
		while (testCase-- > 0) {
			int board = 0;
			
			for (int row = 0; row < ROW; row++) {
				String str = br.readLine().trim();
				for (int col = 0; col < COL; col++) {
					
					// 검은색 칸 체크
					if (str.charAt(col) == BLACK) {
						board = changeColor(board, row, col);
					}
				}
			}
			
			sb.append(solve(board)).append("\n");
		}
		
		System.out.println(sb);
	}
	
	private static int solve(int board) {
		Queue<Board> queue = new ArrayDeque<>();
		boolean[] isVisited = new boolean[1 << 9];
		
		Arrays.fill(isVisited, false);
		queue.offer(new Board(board, 0));
		isVisited[board] = true;
		
		while (!queue.isEmpty()) {
			Board curBoard = queue.poll();
			int curState = curBoard.state;
			int curCnt = curBoard.cnt;
			
			// 흰 보드가 된 경우, 종료
			if (curState == 0) {
				return curCnt;
			}
			
			// 다음으로 변경할 칸을 탐색한다.
			for (int row = 0; row < ROW; row++) {
				for (int col = 0; col < COL; col++) {
					// 본인 + 상하좌우 칸의 색상을 변경한 후 상태를 구한다.
					int nextState = getNextState(curState, row, col);
					
					// 이미 해당 상태의 보드를 경험한 경우, 패스
					if (isVisited[nextState]) {
						continue;
					}
					
					isVisited[nextState] = true;
					queue.offer(new Board(nextState, curCnt+1));
				}
			}
		}
		
		return -1;
	}
	
	// 본인 + 상하좌우 인접 칸의 색상 변경 후 상태 반환
	private static int getNextState(int curState, int row, int col) {
		int nextState = curState;
		
		for (int dir = 0; dir < 5; dir++) {
			int nextRow = row + DELTA_ROW[dir];
			int nextCol = col + DELTA_COL[dir];
			
			// 범위를 벗어나는 경우, 패스
			if (nextRow < 0 || nextCol < 0 || nextRow >= ROW || nextCol >= COL) {
				continue;
			}
			
			nextState = changeColor(nextState, nextRow, nextCol);
		}
		
		return nextState;
	}
	
	// 보드의 특정 비트 색상 변경
	private static int changeColor(int state, int row, int col) {
		int shiftIdx = row * ROW + col;
		
		return state ^ (1 << shiftIdx);
	}
}