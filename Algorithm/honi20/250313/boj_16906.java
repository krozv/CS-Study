import java.io.*;
import java.util.*;

public class Main_16906 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final String WORD = "01";

	static class Node {
		Map<Character, Node> child;
		boolean endOfWord;

		public Node() {
			this.child = new HashMap<>();
			this.endOfWord = false;
		}
	}

	static class Input implements Comparable<Input> {
		int index;
		int length;

		public Input(int index, int value) {
			this.index = index;
			this.length = value;
		}

		@Override
		public int compareTo(Input i) {
			return this.length - i.length;
		}
	}

	static int inputCnt;
	static List<Input> inputs;
	static String[] answer;
	static Node root;

	// 다른 단어의 접두사가 아니면서 자식 노드를 추가할 수 있는 노드 중 길이가 가장 긴 문자열 정보
	static Node baseNode;
	static int baseLen;
	static String baseStr;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		inputs = new ArrayList<>();
		root = new Node();

		inputCnt = Integer.parseInt(br.readLine().trim());
		answer = new String[inputCnt];

		st = new StringTokenizer(br.readLine().trim());
		for (int idx = 0; idx < inputCnt; idx++) {
			int length = Integer.parseInt(st.nextToken());
			inputs.add(new Input(idx, length));
		}

		// 길이 기준 오름차순 정렬 (길이가 짧은 단어부터 삽입)
		Collections.sort(inputs);

		for (Input input : inputs) {
			baseNode = null;
			baseLen = -1;
			baseStr = "";

			// 접두사를 만들지 않으면서 자식 노드를 추가할 수 있는 노드 중 길이가 가장 긴 base 노드를 구한다.
			solve(root, 0, "", input.length);

			// 만들 수 있는 경우가 없는 경우, -1 출력
			if (baseNode == null) {
				System.out.println(-1);
				return;
			}

			// 만들 수 있는 경우
			else {
				int len = input.length - baseLen;

				// 기준 노드의 자식 노드가 빈 경우, '0'을 남은 길이만큼 추가
				if (baseNode.child.isEmpty()) {
					insert(baseNode, len);
					answer[input.index] = baseStr + "0".repeat(len);
				}
				// 기준 노드의 자식 노드가 1개 존재하는 경우, '1' 추가 후 남은 길이만큼 '0' 추가
				else {
					baseNode.child.putIfAbsent('1', new Node());
					insert(baseNode.child.get('1'), len - 1);
					answer[input.index] = baseStr + "1" + "0".repeat(len - 1);
				}
			}
		}

		sb.append("1\n");
		for (String ans : answer) {
			sb.append(ans).append("\n");
		}

		System.out.println(sb);

	}

	private static void solve(Node curNode, int curLen, String curStr, int maxLen) {
		// 현재 길이가 추가를 원하는 문자의 길이와 같은 경우, 더이상 탐색 안함
		if (curLen == maxLen) {
			return;
		}

		// 기준 길이가 더 큰 경우, base 갱신
		if (curLen > baseLen) {
			if (curNode.child.isEmpty() || curNode.child.size() < 2) {
				baseNode = curNode;
				baseLen = curLen;
				baseStr = curStr;
			}
		}

		// 자식 노드로 탐색
		for (int idx = 0; idx < WORD.length(); idx++) {
			char c = WORD.charAt(idx);

			// 자식 노드에 해당 문자가 없는 경우, 패스
			if (!curNode.child.containsKey(c)) {
				continue;
			}

			// 자식 노드가 해당 문자가 다른 단어의 마지막 문자인 경우, 패스
			if (curNode.child.get(c).endOfWord) {
				continue;
			}

			solve(curNode.child.get(c), curLen + 1, curStr + c, maxLen);
		}
	}

	// 현재 노드를 기준으로 len만큼 0 문자 노드를 추가
	private static void insert(Node curNode, int len) {
		Node node = curNode;

		while (len-- > 0) {
			node.child.put('0', new Node());

			node = node.child.get('0');
		}

		node.endOfWord = true;
	}
}