import java.util.*;

class Node {
    int count;
    Node[] children;

    public Node() {
        this.count = 0;
        this.children = new Node[26];
    }
}

class Solution {
    static Node root;
    
    public int solution(String[] words) {
        root = new Node();

        // Trie에 단어 삽입
        for (String word : words) {
            insert(word);
        }

        int totalInputCount = 0;

        for (String word : words) {
            totalInputCount += getMinInputLength(word);
        }

        return totalInputCount;
    }

    private void insert(String word) {
        Node node = root;

        for (char c : word.toCharArray()) {
            int index = c - 'a';

            if (node.children[index] == null) {
                node.children[index] = new Node();
            }

            node.children[index].count++;
            node = node.children[index];
        }
    }

    private int getMinInputLength(String word) {
        Node node = root;
        int inputCount = 0;

        for (char c : word.toCharArray()) {
            int index = c - 'a';
            inputCount++;

            if (node.children[index].count == 1) {
                break;
            }

            node = node.children[index];
        }

        return inputCount;
    }
}
