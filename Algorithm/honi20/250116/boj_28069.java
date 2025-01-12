/**
 * [조건]
 * - 초기 시작 계단 번호는 0번
 * - K번의 행동을 통해 N번 계단으로 이동
 * 	1) 1칸만 이동
 * 	2) 현재 i번째 칸일 때, i+floor(i/2)번째 칸으로 이동
 * 
 * [출력]
 * - N개의 계단을 K번 만에 오를 수 있는지 여부
 */
import java.io.*;
import java.util.*;

public class Main_28069 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int stairsCnt;
	static int movingCnt;
	static int[] stairs;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine().trim());
		stairsCnt = Integer.parseInt(st.nextToken());
		movingCnt = Integer.parseInt(st.nextToken());
		
		stairs = new int[stairsCnt + 1];
		
		// 1번 계단부터 마지막 계단까지 각 계단에 도달할 수 있는 최소 횟수를 구한다.
		for (int idx = 1; idx <= stairsCnt; idx++) {
			// 이전 칸에서 1칸 올라온 경우 
			stairs[idx] = stairs[idx-1] + 1;
				
			// 행동2를 통해 도달할 수 없는 칸이라면, 패스
			if (idx % 3 == 2) {
				continue;
			}
				
			// 행동2를 통해 올라온 경우
			int pre = (idx - (idx / 3));
			stairs[idx] = Math.min(stairs[idx], stairs[pre] + 1);
		}
		
		System.out.println((stairs[stairsCnt] <= movingCnt) ? "minigimbob" : "water");
	}
}
