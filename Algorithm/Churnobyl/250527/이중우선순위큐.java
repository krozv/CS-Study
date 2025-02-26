import java.util.*;

class Node {
    int data;
    boolean isDeleted;
    
    public Node(int data) {
        this.data = data;
    }
}

class Solution {
    static int number;
    static PriorityQueue<Node> minQueue = new PriorityQueue<>(new Comparator<Node>() {
        @Override
        public int compare(Node a, Node b) {
            return Integer.compare(a.data, b.data);
        }
    });
    static PriorityQueue<Node> maxQueue = new PriorityQueue<>(new Comparator<Node>() {
        @Override
        public int compare(Node a, Node b) {
            return Integer.compare(b.data, a.data);
        }
    });
    
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        
        for (String op : operations) {
            StringTokenizer st = new StringTokenizer(op);
            String com = st.nextToken();
            
            switch (com) {
                case "I":
                    insert(Integer.parseInt(st.nextToken()));
                    break;
                case "D":
                    delete(Integer.parseInt(st.nextToken()));
                    break;
            }
        }
        
        if (number == 0) return new int[] {0 , 0};
        
        Node minNode = minQueue.poll();
        
        while (minNode != null && minNode.isDeleted) {
            minNode = minQueue.poll();
        }
        
        Node maxNode = maxQueue.poll();
        
        while (maxNode != null && maxNode.isDeleted) {
            maxNode = maxQueue.poll();
        }
        
        return new int[] {maxNode.data, minNode.data};
    }
    
    public void insert(int data) {
        Node node = new Node(data);
        minQueue.add(node);
        maxQueue.add(node);
        number++;
    }
    
    public void delete(int minMax) {
        if (minMax == 1) { // 최댓값 삭제
            Node candidate = maxQueue.poll();

            while (candidate != null && candidate.isDeleted) {
                candidate = maxQueue.poll();
            }
            
            if (candidate == null) return;
            
            candidate.isDeleted = true;
        } else { // 최솟값 삭제
            Node candidate = minQueue.poll();

            while (candidate != null && candidate.isDeleted) {
                candidate = minQueue.poll();
            }
            
            if (candidate == null) return;
            
            candidate.isDeleted = true;
        }
        
        number--;
    }
}