import java.io.*;
import java.util.*;

public class Main_14003 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Point {
		int index;
		int value;
		
		public Point(int index, int value) {
			this.index = index;
			this.value = value;
		}
	}
	
	static int elementCnt;
	static int[] arr;
	static int[] parent;
	
	public static void main(String[] args) throws Exception {
		input();
		
		// LIS에 속하는 마지막 원소 인덱스
		int elementIdx = LIS();
		
		// LIS에 속하는 부분 수열
		List<Integer> result = getLISList(elementIdx);
	
		sb.append(result.size()).append("\n");
		for (int idx = result.size() - 1; idx >= 0; idx--) {
			sb.append(result.get(idx)).append(" ");
		}
		
		System.out.println(sb);
	}
	
	private static List<Integer> getLISList(int elementIdx) {
		List<Integer> result = new ArrayList<>();
		
		// 마지막 원소부터 시작하여 parent 배열을 통해 부모 원소(부분 수열에서 이전 인덱스 값)를 찾는다.
		while (true) {
			if (elementIdx == -1) {
				break;
			}
			
			result.add(arr[elementIdx]);
			elementIdx = parent[elementIdx];
		}
		
		return result;
	}
	
	private static int LIS() {
		int lastIdx = 0;
		
		// 수열의 크기가 1인 경우
		if (elementCnt == 1) {
			return lastIdx;
		}
		
		List<Point> list = new ArrayList<>();
		list.add(new Point(0, arr[0]));
		
		for (int idx = 1; idx < elementCnt; idx++) {
			Point lastElement = list.get(list.size() - 1);
			
			// 현재 원소 > 리스트의 마지막 원소
			if (arr[idx] > lastElement.value) {
				// 맨 뒤에 추가
				list.add(new Point(idx, arr[idx]));
				lastIdx = idx;
				
				// 부모 원소 인덱스 저장
				parent[idx] = lastElement.index;
			}
			
			// 현재 원소 <= 리스트의 마지막 원소
			else {
				// 이분 탐색을 통해 들어갈 위치 탐색 후 갱신
				int insertPoint = binarySearch(list, arr[idx]);
				list.set(insertPoint, new Point(idx, arr[idx]));
				
				// 부모 원소 인덱스 저장
				parent[idx] = (insertPoint == 0) ? -1 : list.get(insertPoint-1).index;
			}
		}
		
		return lastIdx;
	}
	
	private static int binarySearch(List<Point> list, int element) {
		int start = 0;
		int end = list.size() - 1;
		
		while (start < end) {
			int mid = (start + end) / 2;
			
			if (element > list.get(mid).value) {
				start = mid + 1;
			}
			else {
				end = mid;
			}
		}
		
		return end;
	}
	
	private static void input() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		elementCnt = Integer.parseInt(br.readLine().trim());
		
		arr = new int[elementCnt];
		parent = new int[elementCnt];
		Arrays.fill(parent, -1);
		
		st = new StringTokenizer(br.readLine().trim());
		for (int idx = 0; idx < elementCnt; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}
	}
}