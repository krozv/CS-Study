/**
 * [이동 조건]
 * 1. 수빈이의 위치가 X일 때, 1초 후에 X-1 또는 X+1로 이동한다.
 * 2. 순간이동을 하는 경우, 0초 후에 2*X 위치로 이동한다.
 */
import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;
	
	static class Point implements Comparable<Point> {
		int pos;
		int time;
		
		public Point(int pos, int time) {
			this.pos = pos;
			this.time = time;
		}
		
		@Override
		public int compareTo(Point p) {
			return this.time - p.time;
		}
	}

	static final int MAX = 100_000;
	
	static int startPos, endPos;
	static int result;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine().trim());
		startPos = Integer.parseInt(st.nextToken());
		endPos = Integer.parseInt(st.nextToken());
		
		solve();
		
		System.out.println(result);
	}
	
	private static void solve() {
		PriorityQueue<Point> queue = new PriorityQueue<>();
		int[] minTime = new int[MAX+1];
		
		Arrays.fill(minTime, Integer.MAX_VALUE);
		result = Integer.MAX_VALUE;
		
		// 수빈이의 위치를 queue에 넣는다.
		queue.offer(new Point(startPos, 0));
		
		// 빈 큐가 될 때까지 탐색
		while (!queue.isEmpty()) {
			Point point = queue.poll();
			int curPos = point.pos;
			int curTime = point.time;
			
			// 동생의 위치에 도착한 경우, 결과값 갱신 후 탐색 종료
			if (curPos == endPos) { 
				result = curTime;
				break;
			}
			
			int nextPos;
			
			// 순간이동
			nextPos = curPos * 2;
			if (nextPos > 0 && nextPos <= MAX && minTime[nextPos] > curTime) {
				minTime[nextPos] = curTime;
				queue.add(new Point(nextPos, curTime));
			}
			
			// +1 이동
			nextPos = curPos + 1;
			if (nextPos <= MAX && minTime[nextPos] > curTime + 1) {
				minTime[nextPos] = curTime + 1;
				queue.add(new Point(nextPos, curTime+1));
			}
			
			// -1 이동
			nextPos = curPos - 1;
			if (nextPos >= 0 && minTime[nextPos] > curTime + 1) {
				minTime[nextPos] = curTime + 1;
				queue.add(new Point(nextPos, curTime+1));
			}
		}
	}
}