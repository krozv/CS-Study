import java.io.*;
import java.util.*;

public class Main_17075 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Point {
		int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	static int rowSize, colSize;
	static int MOD;
	static int[][] arr;
	static int total;
	static List<Point> points; // -1인 칸들의 좌표

	static List<Integer>[] preDp;
	static List<Integer>[] curDp;

	public static void main(String[] args) throws Exception {

		input();

		// 이미 MOD로 나누어 떨어지는 경우, -1인 칸을 0으로 바꿔서 출력
		if (total == 0) {
			printArray();
		} 
		else {
			initDp();

			solve();

			// 복원 불가능한 경우
			if (curDp[MOD - total].isEmpty()) {
				sb.append(-1);
			}
			// 복원 가능한 경우
			else {
				// dp에 속한 좌표들은 1로 변환
				for (int idx : curDp[MOD - total]) {
					Point point = points.get(idx);
					arr[point.row][point.col] = 1;
				}

				printArray();
			}
		}

		System.out.println(sb);
	}

	private static void solve() {
		for (int idx = 0; idx < points.size(); idx++) {
			Point point = points.get(idx);
			int value = addValue(point.row, point.col);
			int remain = value % MOD;

			// 첫 번째 칸인 경우, 그냥 dp에 저장
			if (idx == 0) {
				curDp[remain].add(idx);
				continue;
			}

			copyArray(curDp, preDp);

			// 1 ~ MOD에 대해 해당 나머지를 만들 수 있는 칸 리스트를 저장
			if (curDp[remain].isEmpty()) {
				curDp[remain].add(idx);
			}

			for (int mod = 1; mod < MOD; mod++) {
				int curMod = (remain + mod) % MOD;

				if (curDp[curMod].isEmpty() && !preDp[mod].isEmpty()) {
					for (int val : preDp[mod]) {
						curDp[curMod].add(val);
					}

					curDp[curMod].add(idx);
				}
			}
		}
	}

	// 배열 복사하는 함수
	private static void copyArray(List[] fromArray, List[] toArray) {
		for (int mod = 0; mod < MOD; mod++) {
			toArray[mod].clear();
			for (int val : (List<Integer>) fromArray[mod]) {
				toArray[mod].add(val);
			}
		}
	}

	// DP 초기화 함수
	private static void initDp() {
		preDp = new List[MOD];
		curDp = new List[MOD];

		for (int mod = 0; mod < MOD; mod++) {
			preDp[mod] = new ArrayList<>();
			curDp[mod] = new ArrayList<>();
		}
	}

	// 복원된 석판을 출력하는 함수
	private static void printArray() {
		sb.append("1\n");

		for (int row = 1; row <= rowSize; row++) {
			for (int col = 1; col <= colSize; col++) {
				sb.append(arr[row][col] == -1 ? 0 : arr[row][col]).append(" ");
			}
			sb.append("\n");
		}
	}

	// 입력 함수
	private static void input() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		MOD = Integer.parseInt(st.nextToken());

		arr = new int[rowSize + 1][colSize + 1];
		points = new ArrayList<>();
		total = 0;

		for (int row = 1; row <= rowSize; row++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int col = 1; col <= colSize; col++) {
				arr[row][col] = Integer.parseInt(st.nextToken());

				// 값이 1인 경우, 만들어지는 직사각형 개수만큼 숫자 더한다.
				if (arr[row][col] == 1) {
					total += addValue(row, col);
					total %= MOD;
				} else if (arr[row][col] == -1) {
					points.add(new Point(row, col));
				}
			}
		}
	}

	// 현재 좌표에 해당하는 총 합을 반환하는 함수
	private static int addValue(int row, int col) {
		return (rowSize - row + 1) * (colSize - col + 1) * row * col;
	}
}
