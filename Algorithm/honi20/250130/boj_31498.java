import java.io.*;
import java.util.*;

public class Main_31498 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static long runnerPos;
	static long runnerDist;
	static long catcherPos;
	static long catcherDist;
	static long decreaseDist;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		runnerPos = Long.parseLong(st.nextToken());
		runnerDist = Long.parseLong(st.nextToken());

		st = new StringTokenizer(br.readLine().trim());
		catcherPos = runnerPos + Long.parseLong(st.nextToken());
		catcherDist = Long.parseLong(st.nextToken());

		decreaseDist = Long.parseLong(br.readLine().trim());

		// runner가 집에 도착하기 위해 이동해야 하는 최소 횟수 탐색
		long movingCnt = getRunnerMovingCnt();
		
		// 아예 집 도착이 불가능한 경우
		if (movingCnt == -1) {
			sb.append(-1);
		}
		else {
			// 해당 횟수만큼 이동 시 catcher의 위치 확인
			long nextCatcherPos = catcherPos - (catcherDist * movingCnt);
			
			if (nextCatcherPos > 0) {
				sb.append(movingCnt);
			}
			else {
				sb.append(-1);
			}
		}

		System.out.println(sb);
	}

	private static long getRunnerMovingCnt() {
		long start = 0;
		long end = getMaxCnt();
		long cnt = -1;

		while (start <= end) {
			long mid = (long)Math.ceil((double)(start + end) / 2);

			// mid 횟수만큼 이동했을 때의 위치를 계산
			long nextRunnerPos = runnerPos - getMovingDist(mid);
			
			// 집에 도착한 경우
			if (nextRunnerPos <= 0) {
				cnt = mid;
				end = mid - 1;
			}
			// 집에 도착하지 않은 경우
			else {
				start = mid + 1;
			}
		}
		
		return cnt;
	}
	
	private static long getMaxCnt() {
		if (decreaseDist == 0) {
			return (long)Math.ceil((double)runnerPos/runnerDist);
		}
		
		return (long)Math.floor((double)runnerDist / decreaseDist) + 1;
	}
	
	private static long getMovingDist(long mid) {
		return mid * runnerDist - ((mid - 1) * mid * decreaseDist) / 2;
	}
}