import java.io.*;
import java.util.*;

class Node {
    int count;
    Node[] alphabet = new Node[26];

    public Node() {
    }

    @Override
    public String toString() {
        return "Node{" +
                "count=" + count
                + "}";
    }
}

public class Main {
    static Node A, B;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        A = new Node();
        B = new Node();

        int Q = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String query = st.nextToken();

            switch (query) {
                case "add":
                    add(st.nextToken(), st.nextToken());
                    break;
                case "delete":
                    delete(st.nextToken(), st.nextToken());
                    break;
                case "find":
                    sb.append(find(st.nextToken())).append("\n");
                    break;
            }
        }

        System.out.println(sb);
    }

    private static int find(String data) {
        int sum = 0;

        int[] resultA = query(A, data);
        int[] resultB = query(B, new StringBuilder(data).reverse().toString());

        for (int i = 0; i < data.length() - 1; i++) {
            sum += resultA[i] * resultB[data.length() - i - 2];
        }

        return sum;
    }

    private static int[] query(Node set, String str) {
        int[] result = new int[str.length()];

            for (int i = 0; i < str.length(); i++) {
                set = set.alphabet[str.charAt(i) - 'a'];

                if (set == null) {
                    break;
                }

                result[i] = set.count;
            }

        return result;
    }

    public static void delete(String set, String data) {
        if (set.equals("A")) {
            deleteRecursive(A, A, data, 0);
        } else {
            deleteRecursive(B, B, data, data.length() - 1);
        }
    }

    private static void deleteRecursive(Node set, Node root, String data, int index) {
        if (root == A && index == data.length()) return;
        if (root == B && index == -1) return;

        int charIndex = data.charAt(index) - 'a';

        if (set.alphabet[charIndex].count > 0) {
            set.alphabet[charIndex].count--;
        } else {
            return;
        }

        if (root == A) {
            deleteRecursive(set.alphabet[charIndex], root, data, index + 1);
        } else {
            deleteRecursive(set.alphabet[charIndex], root, data, index - 1);
        }

    }

    public static void add(String set, String data) {
        if (set.equals("A")) {
            addRecursive(A, A, data, 0);
        } else {
            addRecursive(B, B, data, data.length() - 1);
        }
    }

    private static void addRecursive(Node set, Node root, String data, int index) {
        if (root == A && index == data.length()) return;
        if (root == B && index == -1) return;

        int charIndex = data.charAt(index) - 'a';

        if (set.alphabet[charIndex] == null) {
            set.alphabet[charIndex] = new Node();
        }

        set.alphabet[charIndex].count++;

        if (root == A) {
            addRecursive(set.alphabet[charIndex], root, data, index + 1);
        } else {
            addRecursive(set.alphabet[charIndex], root, data, index - 1);
        }
    }
}
