import java.util.HashMap;
import java.util.Map;

class Node {
    Map<Character, Node> children;
    boolean isEndOfWord;

    public Node() {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }
}

class Trie {
    private Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new Node());
            node = node.children.get(c);
        }

        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        Node node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public void delete(String word) {
        deleteRecursive(root, word, 0);
    }

    private boolean deleteRecursive(Node node, String word, int index) {
        if (index == word.length()) {
            if (!node.isEndOfWord) return false;
            node.isEndOfWord = false;
            return node.children.isEmpty();
        }

        char ch = word.charAt(index);
        Node nextNode = node.children.get(ch);
        if (nextNode == null) return false;

        boolean shouldDelete = deleteRecursive(nextNode, word, index + 1);

        if (shouldDelete) {
            node.children.remove(ch);
            return node.children.isEmpty() && !node.isEndOfWord;
        }

        return false;
    }

    private Node findNode(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node;
    }
}