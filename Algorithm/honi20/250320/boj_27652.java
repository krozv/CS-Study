import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int MAX = 1001;
	
	static class Node {
		Map<Character, Node> child;
		boolean endOfWord;
		int cnt;

		public Node() {
			this.child = new HashMap<>();
			this.endOfWord = false;
			this.cnt = 0;
		}
		
		public void updateCnt(int val) {
			this.cnt += val;
		}
	}

	static int queryCnt;
	static Node rootA;
	static Node rootB;
	static int[] lenA;
	static int[] lenB;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		rootA = new Node();
		rootB = new Node();
		lenA = new int[MAX];
		lenB = new int[MAX];

		queryCnt = Integer.parseInt(br.readLine().trim());
		while (queryCnt-- > 0) {
			String[] query = br.readLine().trim().split(" ");

			switch (query[0]) {
			case "add":
				add(query[1], query[2]);
				break;
			case "delete":
				delete(query[1], query[2]);
				break;
			case "find":
				sb.append(find(query[1])).append("\n");
				break;
			}
		}

		System.out.println(sb);
	}

	private static int find(String word) {
		int answer = 0;
		String reverseWord = reverse(word);

		Arrays.fill(lenA, 0);
		Arrays.fill(lenB, 0);
		
		search(rootA, word, lenA);
		search(rootB, reverseWord, lenB);
		
		for (int idx = 1; idx < word.length(); idx++) {
			int val = lenA[idx] * lenB[word.length() - idx];
		
			answer += val;
		}
		
		return answer;
	}

	private static void search(Node node, String word, int[] len) {
		for (int idx = 0; idx < word.length(); idx++) {
			char c = word.charAt(idx);

			if (!node.child.containsKey(c)) {
				return;
			}
			
			len[idx+1] = node.child.get(c).cnt;
			node = node.child.get(c);
		}
		
		return;
	}

	private static void delete(String type, String word) {
		Node root = (type.equals("A")) ? rootA : rootB;

		if (type.equals("B")) {
			word = reverse(word);
		}

		delete(root, word, 0);
	}

	private static void delete(Node node, String word, int index) {
		char c = word.charAt(index);

		// 현재 노드의 자식 노드에서 문자를 지워야하는데 없는 경우
		if (node.child.isEmpty() || !node.child.containsKey(c)) {
			return;
		}

		Node curNode = node.child.get(c);
		++index;

		// 문자열의 끝에 도달한 경우
		if (index == word.length()) {
			curNode.endOfWord = false;
			curNode.updateCnt(-1);
			
			if (curNode.child.isEmpty()) {
				node.child.remove(c);
			}
		}
		// 문자열의 끝에 도달하지 않은 경우
		else {
			delete(curNode, word, index);

			if (!curNode.endOfWord && curNode.child.isEmpty()) {
				node.child.remove(c);
			}
		}
		
		node.updateCnt(-1);
	}

	private static void add(String type, String word) {
		Node root = (type.equals("A")) ? rootA : rootB;

		if (type.equals("B")) {
			word = reverse(word);
		}

		insert(root, word);
	}

	private static void insert(Node node, String word) {
		node.updateCnt(1);
		
		for (int idx = 0; idx < word.length(); idx++) {
			char c = word.charAt(idx);

			node.child.putIfAbsent(c, new Node());
			
			node.child.get(c).updateCnt(1);
			
			node = node.child.get(c);
		}

		node.endOfWord = true;
	}

	private static String reverse(String word) {
		StringBuffer sbf = new StringBuffer(word);
		return sbf.reverse().toString();
	}
}
