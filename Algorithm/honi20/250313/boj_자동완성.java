
import java.util.*;

class Solution {
	static class Node {
		Node parent;
		Map<Character, Node> child;
		boolean endOfWord;

		public Node(Node parent) {
			this.parent = parent;
			this.child = new HashMap<>();
			this.endOfWord = false;
		}
	}

	static Node root;
	static List<Node> lastNode;	// 각 단어의 마지막 문자 노드 저장

	public int solution(String[] words) {
		int answer = 0;

		root = new Node(null);
		lastNode = new ArrayList<>();

		// 단어를 Trie에 삽입
		for (String word : words) {
			insert(word);
		}

		for (int idx = 0; idx < words.length; idx++) {
			// 검색어에서 skip 가능한 길이
			int skip = solve(lastNode.get(idx));

			answer += (words[idx].length() - skip);
		}

		return answer;
	}

	private int solve(Node curNode) {
		Node node = curNode;
		int cnt = 0;

		// 단어의 마지막 문자 노드를 시작으로 parent 노드를 통해 root로 탐색
		while (node.parent != null) {
			Node parent = node.parent;

			// 현재 단어가 다른 단어에 속한 경우 (기준: word / 다른단어: wordlist)
			if (node.endOfWord && !node.child.isEmpty()) {
				break;
			}

			// 부모 노드가 다른 단어의 마지막 문자인 경우 (기준: word / 다른단어: wor)
			if (parent.endOfWord) {
				break;
			} 
			// 부모 노드로 시작하는 단어가 1개 이상인 경우 (기준: word / 다른단어: work)
			else if (parent.child.size() > 1) {
				break;
			} 
			else {
				cnt++;
				node = parent;
			}
		}

		return cnt;
	}

	private void insert(String str) {
		Node node = root;

		for (int idx = 0; idx < str.length(); idx++) {
			char c = str.charAt(idx);

			// 자식 노드에 해당 문자가 없으면 추가
			node.child.putIfAbsent(c, new Node(node));

			// 자식 노드로 이동
			node = node.child.get(c);
		}

		node.endOfWord = true;
		lastNode.add(node);
	}
}