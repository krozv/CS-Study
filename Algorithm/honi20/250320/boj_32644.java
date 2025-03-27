import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int arrSize;
	static int presentCnt;
	static int[] arr;
	static int[] tree;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		st = new StringTokenizer(br.readLine().trim());
		arrSize = Integer.parseInt(st.nextToken());
		presentCnt = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine().trim());
		arr = new int[arrSize + 1];
		for (int idx = 1; idx <= arrSize; idx++) {
			arr[idx] = Integer.parseInt(st.nextToken());
		}

		// tree 초기화
		init();

		st = new StringTokenizer(br.readLine().trim());
		while (st.hasMoreTokens()) {
			int memberNum = Integer.parseInt(st.nextToken());

			// 공 번호 탐색
			sb.append(query(1, 1, arrSize, 1, tree[1], memberNum)).append(" ");

			// 당첨 회원의 공 삭제
			update(1, 1, arrSize, memberNum);
		}

		System.out.println(sb);
	}

	private static int query(int node, int start, int end, int left, int right, int index) {
		if (start == end) {
			return left;
		}

		int mid = (start + end) / 2;
		if (index <= mid) {
			return query(node * 2, start, mid, left, left + tree[node * 2] - 1, index);
		} else {
			return query(node * 2 + 1, mid + 1, end, left + tree[node * 2], right, index);
		}
	}

	private static void update(int node, int start, int end, int index) {
		if (index > end || index < start) {
			return;
		}

		tree[node] -= arr[index];

		if (start != end) {
			int mid = (start + end) / 2;
			update(node * 2, start, mid, index);
			update(node * 2 + 1, mid + 1, end, index);
		}
	}

	private static void init() {
		int height = (int) Math.ceil(Math.log(arrSize) / Math.log(2));
		tree = new int[1 << (height + 1)];
		Arrays.fill(tree, 0);

		init(1, 1, arrSize);
	}

	private static int init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start];
		}

		int mid = (start + end) / 2;
		return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
	}
}
