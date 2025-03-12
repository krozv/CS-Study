# Trie

- 문자열을 저장하고, 빠르게 탐색하기 위한 트리 형태의 자료구조
- 문자열 저장을 위한 메모리를 사용하지만, 탐색 속도가 매우 빠르다.
- 탐색 시간복잡도: O(N)
- root Node는 항상 비어있고, 자식노드는 각 단어들의 첫 글자들이다.

## Node

```java
class Node {
	Map<Character, Node> child;
	boolean endOfWord;

	public Node() {
		this.child = new HashMap<>();
		this.endOfWord = false;
	}
}
```

<br>

## Insert

```java
private void insert(String str) {
	Node node = root;

	for (int idx = 0; idx < str.length(); idx++) {
		char c = str.charAt(idx);

		node.child.putIfAbsent(c, new Node());

		// 자식 노드로 이동
		node = node.child.get(c);
	}

	node.endOfWord = true;
}
```

<br>

## Search

```java
private boolean search(String str) {
	Node node = root;

	for (int idx = 0; idx < str.length(); idx++) {
		char c = str.charAt(idx);

		// 자식노드에 해당 문자가 있는 경우, 계속 탐색
		if (node.child.containsKey(c)) {
			node = node.child.get(c);
		}
		// 해당 문자가 없는 경우
		else {
			return false;
		}
	}

	// 마지막 노드까지 도달한 경우
	return node.endOfWord;
}
```

<br>

## Delete

```java
private boolean delete(String str) {
	boolean result = delete(root, str, 0);
	return result;
}

private boolean delete(Node node, String str, int index) {
	char c = str.charAt(index);

	// 현재 노드의 자식 노드에서 문자를 지워야하는데 없는 경우
	if (!node.child.containsKey(c)) {
		return false;
	}

	Node cur = node.child.get(c);
	++index;

	// 문자열의 끝에 도달한 경우
	if (index == str.length()) {
		if (!cur.endOfWord) {
			return false;
		}

		cur.endOfWord = false;

		// 지우려는 문자열의 마지막에서 더 뻗어나가는 경우
		if (cur.child.isEmpty()) {
			node.child.remove(c);
		}
	}
	// 문자열의 끝에 도달하지 않은 경우
	else {
		// 삭제 실패
		if (!delete(cur, str, index)) {
			return false;
		}

		if (!cur.endOfWord && cur.child.isEmpty()) {
			node.child.remove(c);
		}
	}

	return true;
}
```
