import java.io.*;
import java.util.*;

public class Main_15704 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Road {
		int cross;	// 연결된 교차로 번호
		int cost;	// 통제 비용
		int limit;	// 상한선

		public Road(int cross, int cost, int limit) {
			this.cross = cross;
			this.cost = cost;
			this.limit = limit;
		}
	}

	static class Point implements Comparable<Point> {
		int cross;	// 현재 교차로 번호
		int cost;	// 사용 비용

		public Point(int cross, int cost) {
			this.cross = cross;
			this.cost = cost;
		}

		@Override
		public int compareTo(Point p) {
			return this.cost - p.cost;
		}
	}

	static int crossCnt;	// 교차로 수
	static int roadCnt;		// 도로 수
	static int budget;		// 예산
	static List<Road>[] roads;	// 각 교차로에 연결된 도로 정보

	static int maxParticapant;	// 가능한 최대 참가자 수
	static PriorityQueue<Point> queue;
	static long[] minCost;		// 현재 교차로에 도착하는데 발생하는 최소 비용

	public static void main(String[] args) throws Exception {
		input();

		queue = new PriorityQueue<>();
		minCost = new long[crossCnt + 1];

		System.out.println(solve());
	}

	private static long solve() {
		// 마라톤 참가 인원수를 이분 탐색을 통해 구한다.
		long start = 0;
		long end = maxParticapant;
		long result = -1;

		while (start < end) {
			long mid = (start + end) / 2;

			// 예산 안에서 모두 참가 가능한 경우
			if (isPossible(mid)) {
				result = mid;
				start = mid + 1;
			} 
			else {
				end = mid;
			}
		}

		return result;
	}

	private static boolean isPossible(long participantCnt) {
		queue.clear();
		Arrays.fill(minCost, -1);

		// 마라톤은 1번 교차로에서 시작한다.
		queue.add(new Point(1, 0));
		minCost[1] = 0;

		while (!queue.isEmpty()) {
			Point point = queue.poll();
			int curCross = point.cross;
			int curCost = point.cost;

			// 현재 교차로가 마지막 교차로인 경우, 마라톤 코스가 끝난다.(가능)
			if (curCross == crossCnt) {
				return true;
			}

			// 연결된 도로를 탐색한다.
			List<Road> list = roads[curCross];
			for (int idx = 0; idx < list.size(); idx++) {
				Road road = list.get(idx);

				// 도로 통행에 필요한 비용을 계산한다.
				long nextCost = getCost(participantCnt, road);

				// 예산을 초과하는 도로인 경우, 패스
				if (curCost + nextCost > budget) {
					continue;
				}

				// 현재 이동 경로가 더 적은 비용을 발생시키는 경우, 갱신
				if (minCost[road.cross] == -1 || curCost + nextCost < minCost[road.cross]) {
					minCost[road.cross] = curCost + nextCost;
					queue.add(new Point(road.cross, (int) minCost[road.cross]));
				}
			}
		}

		return false;
	}

	// 도로를 통행하는데 발생하는 비용을 계산하는 함수
	private static long getCost(long participantCnt, Road road) {
		if (participantCnt <= road.limit) {
			return 0;
		}

		return road.cost * (long) Math.pow((participantCnt - road.limit), 2);
	}

	private static void input() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		crossCnt = Integer.parseInt(st.nextToken());
		roadCnt = Integer.parseInt(st.nextToken());
		budget = Integer.parseInt(st.nextToken());

		roads = new ArrayList[crossCnt + 1];

		int minCost = Integer.MAX_VALUE;
		int maxLimit = 0;

		for (int idx = 0; idx < roadCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			int cross1 = Integer.parseInt(st.nextToken());
			int cross2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			int limit = Integer.parseInt(st.nextToken());

			// 각 교차로에 연결된 도로 정보를 저장한다.
			saveRoadInfo(cross1, cross2, cost, limit);

			minCost = Math.min(minCost, cost);
			maxLimit = Math.max(maxLimit, limit);
		}

		// 최대로 참가할 수 있는 참가자 수를 구한다.
		maxParticapant = (int) Math.sqrt(budget / minCost) + 1 + maxLimit;
	}

	private static void saveRoadInfo(int cross1, int cross2, int cost, int limit) {
		if (roads[cross1] == null)
			roads[cross1] = new ArrayList<>();

		if (roads[cross2] == null)
			roads[cross2] = new ArrayList<>();

		roads[cross1].add(new Road(cross2, cost, limit));
		roads[cross2].add(new Road(cross1, cost, limit));
	}
}