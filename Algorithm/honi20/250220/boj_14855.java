import java.io.*;
import java.util.*;

public class Main_14855 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int WEIGHT = 0;
	static final int VALUE = 1;
	static final int CNT = 2;

	static int itemCnt;
	static int limitWeight;
	static int specialWeight, specialValue;
	static int[][] items;
	static int[] dp;
	static int result;

	public static void main(String[] args) throws Exception {
		input();

		solve();

		// dp 배열의 최댓값이 결과값이 된다.
		result = 0;
		for (int idx = 1; idx <= limitWeight; idx++) {
			result = Math.max(result, dp[idx]);
		}

		System.out.println(result);
	}

	private static void solve() {
		dp = new int[limitWeight + 1];
		Arrays.fill(dp, 0);

		for (int idx = 1; idx <= itemCnt; idx++) {
			// 만두 속으로 만들 수 있는 개수만큼 현재 만두를 탐색한다.
			for (int cnt = 0; cnt < items[idx][CNT]; cnt++) {
				// 각 무게(밀가루)에 대해 담을 수 있는 최대 가치(가격)을 저장한다.
				for (int weight = limitWeight; weight >= items[idx][WEIGHT]; weight--) {
					dp[weight] = Math.max(dp[weight], dp[weight - items[idx][WEIGHT]] + items[idx][VALUE]);
				}
			}
		}

		// dp 값에 변화가 없을 때까지 스페셜 메뉴를 탐색한다.
		while (true) {
			boolean isChanged = false;

			for (int weight = limitWeight; weight >= specialWeight; weight--) {
				if (dp[weight] < (dp[weight - specialWeight] + specialValue)) {
					dp[weight] = dp[weight - specialWeight] + specialValue;
					isChanged = true;
				}
			}

			if (!isChanged) {
				break;
			}
		}
	}

	private static void input() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		limitWeight = Integer.parseInt(st.nextToken());
		itemCnt = Integer.parseInt(st.nextToken());
		specialWeight = Integer.parseInt(st.nextToken());
		specialValue = Integer.parseInt(st.nextToken());

		items = new int[itemCnt + 1][3];
		for (int idx = 1; idx <= itemCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());

			int remain = Integer.parseInt(st.nextToken());
			int filling = Integer.parseInt(st.nextToken());

			items[idx][WEIGHT] = Integer.parseInt(st.nextToken());
			items[idx][VALUE] = Integer.parseInt(st.nextToken());
			items[idx][CNT] = remain / filling;
		}
	}
}
