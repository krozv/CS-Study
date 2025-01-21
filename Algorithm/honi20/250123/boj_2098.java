import java.io.*;
import java.util.*;

public class Main_2098 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int INF = 123456789;
	
	static int cityCnt;
	static int[][] cost;
	static int[][] dp;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		cityCnt = Integer.parseInt(br.readLine().trim());
		cost = new int[cityCnt][cityCnt];
		
		for (int city1 = 0; city1 < cityCnt; city1++) {
			st = new StringTokenizer(br.readLine().trim());
			for (int city2 = 0; city2 < cityCnt; city2++) {
				cost[city1][city2] = Integer.parseInt(st.nextToken());
			}
		}
		
		initDp();
		
		System.out.println(solve(0, 1));
	}
	
	private static int solve(int curCity, int state) {
		// 모든 도시를 방문한 경우
		if (state == (1 << cityCnt) - 1) {
			if (cost[curCity][0] == 0) {
				return INF;
			}
			
			return cost[curCity][0];
		}
		
		// 이미 방문한 도시와 상태인 경우, 해당 값 반환
		if (dp[curCity][state] != -1) {
			return dp[curCity][state];
		}
		
		dp[curCity][state] = INF;
		
		// 다음 도시로 이동
		for (int idx = 1; idx < cityCnt; idx++) {
			// 이동경로가 없는 경우, 패스
			if (cost[curCity][idx] == 0) {
				continue;
			}
			
			// 이미 방문한 도시인 경우, 패스
			if ((state & (1 << idx)) == (1 << idx)) {
				continue;
			}
			
			dp[curCity][state] = Math.min(dp[curCity][state], solve(idx, state | (1 << idx)) + cost[curCity][idx]);
		}
		
		return dp[curCity][state];
	}
	
	private static void initDp() {
		dp = new int[cityCnt][(1 << cityCnt)];
		
		for (int idx = 0; idx < cityCnt; idx++) {
			Arrays.fill(dp[idx], -1);
		}
	}
}
