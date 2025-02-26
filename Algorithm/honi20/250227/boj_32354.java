import java.io.*;
import java.util.*;

public class Main_32354 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static class Node {
		Node prev;
		long sum;

		Node(Node prev, long sum) {
			this.prev = prev;
			this.sum = sum;
		}
	}

	static List<Node> deck;
	static Node[] lastNode; // 명령 후 덱의 마지막 노드 저장

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		int queryCnt = Integer.parseInt(br.readLine().trim());

		deck = new ArrayList<>();
		lastNode = new Node[queryCnt + 1];

		// 빈 덱 상태 저장
		Node start = new Node(null, 0);
		deck.add(start);
		lastNode[0] = start;

		for (int idx = 1; idx <= queryCnt; idx++) {
			st = new StringTokenizer(br.readLine().trim());
			String command = st.nextToken();

			switch (command) {
			case "push":
				push(idx, Long.parseLong(st.nextToken()));
				break;
				
			case "pop":
				pop(idx);
				break;
				
			case "restore":
				restore(idx, Integer.parseInt(st.nextToken()));
				break;
				
			case "print":
				print(idx);
				break;
			}
		}

		System.out.print(sb);
	}

	private static void push(int index, long val) {
		// 이전 명령 후 마지막 노드 뒤에 새 노드를 추가한다.
		Node node = lastNode[index - 1];
		Node newNode = new Node(node, node.sum + val);
		lastNode[index] = newNode;
	}

	private static void pop(int index) {
		Node node = lastNode[index - 1];

		// 빈 덱인 경우, 패스
		if (node.prev == null) {
			return;
		}

		// 이전 명령 후 마지막 노드가 prev로 가리키는 노드를 가리키게 된다.(마지막 노드는 pop됨)
		lastNode[index] = node.prev;
	}

	private static void restore(int index, int step) {
		lastNode[index] = lastNode[step];
	}

	private static void print(int index) {
		Node node = lastNode[index - 1];
		sb.append(node.sum).append("\n");

		// 이전 명령 후 상태와 동일
		lastNode[index] = lastNode[index - 1];
	}
}
