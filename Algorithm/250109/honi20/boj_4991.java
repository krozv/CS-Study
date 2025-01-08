/**
 * [조건]
 * - 더러운 칸을 방문해서 깨끗한 칸으로 바꿀 수 있다.
 * - 로봇은 가구가 놓여진 칸으로 이동할 수 없다.
 * - 인접칸으로 이동 가능하다.
 * - 같은 칸을 여러 번 방문 가능하다.
 * 
 * [출력]
 * - 더러운 칸을 모두 깨끗한 칸으로 만드는데 필요한 이동 횟수의 최솟값
 */
import java.io.*;
import java.util.*;

public class Main_4991 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static class Point {
		int row, col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		@Override
		public boolean equals(Object p) {
			if (p instanceof Point) {
				return this.row == ((Point)p).row && this.col == ((Point)p).col;
			}
			else {
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			return this.row + this.col;
		}
	}
	
	static class Node {
		Point point;
		int state;
		int dist;
		
		public Node(Point point, int state, int dist) {
			this.point = point;
			this.state = state;
			this.dist = dist;
		}
	}
	
	static final char CLEAN = '.';
	static final char DIRTY = '*';
	static final char FURNITURE = 'x';
	static final char START = 'o';
	
	static final int[] DELTA_ROW = {-1,1,0,0};
	static final int[] DELTA_COL = {0,0,-1,1};
	
	static int roomRow, roomCol;
	static char[][] room;
	static Point start;
	static Map<Point, Integer> dirty;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		while (true) {
			st = new StringTokenizer(br.readLine().trim());
			roomCol = Integer.parseInt(st.nextToken());
			roomRow = Integer.parseInt(st.nextToken());
			
			// 종료 조건
			if (roomRow == 0 && roomCol == 0) {
				break;
			}
			
			room = new char[roomRow][roomCol];
			dirty = new HashMap<>();
			
			for (int row = 0; row < roomRow; row++) {
				room[row] = br.readLine().trim().toCharArray();
				
				for (int col = 0; col < roomCol; col++) {
					if (room[row][col] == START) {
						start = new Point(row, col);
					}
					// 더러운 칸은 맵에 저장한다.
					else if (room[row][col] == DIRTY) {
						dirty.put(new Point(row, col), dirty.size());
					}
				}
			}
			
			// 더러운 칸의 개수만큼 비트를 초기화한다.
			int state = (1 << dirty.size()) - 1;
			
			// 시작 위치부터 탐색을 시작한다.
			sb.append(solve(state)).append("\n");
		}
		
		System.out.println(sb);
	}
	
	private static int solve(int state) {
		Queue<Node> queue = new ArrayDeque<>();
		
		// minDist[row][col][state]
		int[][][] minDist = new int[roomRow][roomCol][1 << dirty.size()];
		initMinDist(minDist);
		
		queue.offer(new Node(start, state, 0));
		minDist[start.row][start.col][state] = 0;
		
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			int curRow = node.point.row;
			int curCol = node.point.col;
			int curState = node.state;
			int curDist = node.dist;
			
			// 더러운 칸을 모두 청소한 경우(= 상태값이 0인 경우), 횟수 반환 후 종료
			if (curState == 0) {
				return curDist;
			}
			
			// 다음 인접칸으로 이동한다.
			for (int dir = 0; dir < 4; dir++) {
				int nextRow = curRow + DELTA_ROW[dir];
				int nextCol = curCol + DELTA_COL[dir];
				
				// 범위를 벗어나는 경우, 패스
				if (nextRow < 0 || nextCol < 0 || nextRow >= roomRow || nextCol >= roomCol) {
					continue;
				}
				
				// 이동칸이 가구인 경우, 패스
				if (room[nextRow][nextCol] == FURNITURE) {
					continue;
				}
				
				Point nextPos = new Point(nextRow, nextCol);
				int nextState = curState;
				
				// 이동칸이 더러운 칸이 경우, 다음 상태의 비트값을 0으로 갱신
				if (room[nextRow][nextCol] == DIRTY) {
					nextState = clean(nextState, nextPos);
				}
				
				// 이동칸에 저장된 값보다 이동 횟수가 적은 경우에만 갱신
				if (curDist + 1 < minDist[nextRow][nextCol][nextState]) {
					minDist[nextRow][nextCol][nextState] = curDist + 1;
					queue.offer(new Node(nextPos, nextState, curDist+1));
				}
			}
		}
		
		return -1;
	}
	
	private static int clean(int nextState, Point nextPos) {
		return nextState & ~(1 << dirty.get(nextPos));
	}
	
	private static void initMinDist(int[][][] minDist) {
		for (int row = 0; row < roomRow; row++) {
			for (int col = 0; col < roomCol; col++) {
				Arrays.fill(minDist[row][col], Integer.MAX_VALUE);
			}
		}
	}
}