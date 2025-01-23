/**
 * [조건]
 * - 마크가 던지는 프리스비를 뺏기 위해서는 마크보다 키가 크거나 같아야 한다.
 * - 소 여러 마리가 하나의 스택을 쌓아 키를 높일 수 있다.
 * - 소의 힘 = 소가 들 수 있는 무게
 * 
 * [출력]
 * - 마크의 프리스비를 뺏을 수 있는지 여부
 * - 가능하다면 쌓은 스택의 맨 위에 추가로 더 올릴 수 있는 무게 출력
 */
import java.io.*;
import java.util.*;

public class Main_10649 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int HEIGHT = 0;
	static final int WEIGHT = 1;
	static final int POWER = 2;

	static int cowCnt;
	static int mark;
	static int[][] cows;
	static int[] dp;
	static int result;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		cowCnt = Integer.parseInt(st.nextToken());
		mark = Integer.parseInt(st.nextToken());

		cows = new int[cowCnt][3];
		for (int idx = 0; idx < cowCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			cows[idx][HEIGHT] = Integer.parseInt(st.nextToken());
			cows[idx][WEIGHT] = Integer.parseInt(st.nextToken());
			cows[idx][POWER] = Integer.parseInt(st.nextToken());
		}

		initDp();

		result = -1;
		solve();

		System.out.println(result != -1 ? result : "Mark is too tall");
	}

	private static void solve() {
		// 가능한 소의 스택 집합 탐색
		for (int state = 1; state < (1 << cowCnt); state++) {
			int totalHeight = 0;

			for (int idx = 0; idx < cowCnt; idx++) {
				// 현재 소가 집합에 포함되어 있는지 확인
				if ((state & (1 << idx)) == (1 << idx)) {
					int preState = state ^ (1 << idx);

					// 현재 소를 스택의 맨 위에 두는 경우
					if (dp[preState] >= cows[idx][WEIGHT]) {
						int minPower = Math.min(dp[preState] - cows[idx][WEIGHT], cows[idx][POWER]);
						dp[state] = Math.max(dp[state], minPower);
					}

					totalHeight += cows[idx][HEIGHT];
					if (totalHeight >= mark) {
						break;
					}
				}
			}

			if (totalHeight >= mark) {
				result = Math.max(result, dp[state]);
			}
		}
	}

	private static void initDp() {
		dp = new int[(1 << cowCnt)];
		Arrays.fill(dp, -1);

		dp[0] = Integer.MAX_VALUE;
		for (int idx = 0; idx < cowCnt; idx++) {
			dp[(1 << idx)] = cows[idx][POWER];
		}
	}
}
