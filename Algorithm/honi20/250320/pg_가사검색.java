import java.util.*;

class Solution {
	final int MAX = 100000;

	class Node {
		Map<Character, Node> child;
		boolean endOfWord;
		Map<Integer, Integer> cnt; // key만큼의 길이를 갖는 단어 개수

		public Node() {
			child = new HashMap<>();
			endOfWord = false;
			cnt = new HashMap<>();
		}
	}

	Node root;
	List<Node>[] depth; // 해당 깊이의 노드 리스트
	Map<String, Integer> dict; // 쿼리에 대한 답

	public int[] solution(String[] words, String[] queries) {
		int[] answer = new int[queries.length];

		root = new Node();
		depth = new ArrayList[MAX + 1];
		for (int idx = 0; idx <= MAX; idx++) {
			depth[idx] = new ArrayList<>();
		}
		dict = new HashMap<>();

		// 단어를 Trie에 넣음
		for (String word : words) {
			insert(word);
		}

		for (int idx = 0; idx < queries.length; idx++) {
			String query = queries[idx];
			int len = query.length();

			// 이미 구한 쿼리인 경우
			if (dict.containsKey(query)) {
				answer[idx] = dict.get(query);
				continue;
			}

			// ?가 접두사
			if (query.charAt(0) == '?') {
				int lastIdx = query.lastIndexOf('?');

				// ?로만 이루어진 경우
				if (lastIdx == len - 1) {
					if (root.cnt.containsKey(len)) {
						answer[idx] = root.cnt.get(len);
					}
				}
				// {? + 알파벳}으로 이루어진 경우
				else {
					String subQuery = query.substring(lastIdx + 1);
					List<Node> nodes = depth[lastIdx];

					for (Node curNode : nodes) {
						Node node = search(subQuery, curNode);

						if (node != null && node.endOfWord) {
							++answer[idx];
						}
					}
				}
			}

			// ?가 접미사
			else {
				int firstIdx = query.indexOf('?');
				String subQuery = query.substring(0, firstIdx);

				Node node = search(subQuery, root);

				if (node != null) {
					if (node.cnt.containsKey(len - firstIdx)) {
						answer[idx] = node.cnt.get(len - firstIdx);
					}
				}
			}

			dict.put(query, answer[idx]);
		}

		return answer;
	}

	private Node search(String query, Node curNode) {
		Node node = curNode;

		for (int idx = 0; idx < query.length(); idx++) {
			char c = query.charAt(idx);

			if (node.child.containsKey(c)) {
				node = node.child.get(c);
			} else {
				return null;
			}
		}

		return node;
	}

	private void insert(String word) {
		int len = word.length();
		Node node = root;

		for (int idx = 0; idx < len; idx++) {
			node.cnt.putIfAbsent(len - idx, 0);
			node.cnt.put(len - idx, node.cnt.get(len - idx) + 1);

			char c = word.charAt(idx);

			// 해당 문자 노드가 없다면 생성
			if (!node.child.containsKey(c)) {
				node.child.putIfAbsent(c, new Node());
				depth[idx].add(node.child.get(c));
			}

			node = node.child.get(c);
		}

		node.endOfWord = true;
	}
}