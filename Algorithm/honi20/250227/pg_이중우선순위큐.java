import java.util.*;

class Solution {
	private static class Node {
		int value;
		int index;

		public Node(int value, int index) {
			this.value = value;
			this.index = index;
		}
	}

	private static PriorityQueue<Node> maxQueue;
	private static PriorityQueue<Node> minQueue;
	private static Set<Integer> isSaved;	// queue에 저장된 수들의 식별자 모음

	public int[] solution(String[] operations) {
		int[] answer = new int[2];

		init();

		for (int idx = 0; idx < operations.length; idx++) {
			String[] operation = operations[idx].split(" ");

			switch (operation[0]) {
			case "I":
				add(Integer.parseInt(operation[1]), idx);
				break;
			case "D":
				remove(Integer.parseInt(operation[1]));
				break;
			}
		}

		if (!maxQueue.isEmpty() && !minQueue.isEmpty()) {
			answer[0] = maxQueue.peek().value;
			answer[1] = minQueue.peek().value;
		}

		return answer;
	}

	private void add(int value, int index) {
		maxQueue.offer(new Node(value, index));
		minQueue.offer(new Node(value, index));
		isSaved.add(index);
	}

	private void remove(int type) {
		// 최댓값 삭제
		if (type == 1 && !maxQueue.isEmpty()) {
			Node node = maxQueue.poll();

			// set에서 삭제된 수의 식별자 제거
			if (node != null) {
				isSaved.remove(node.index);
			}
		}
		// 최솟값 삭제
		else if (type == -1 && !minQueue.isEmpty()) {
			Node node = minQueue.poll();

			if (node != null) {
				isSaved.remove(node.index);
			}
		}

		updateQueue();
	}

	private void updateQueue() {
		// 가장 우선순위가 높은 값이 삭제된 값이 아닐 때까지 삭제
		while (!maxQueue.isEmpty()) {
			Node first = maxQueue.peek();

			if (first != null && isSaved.contains(first.index)) {
				break;
			}

			maxQueue.poll();
		}

		while (!minQueue.isEmpty()) {
			Node first = minQueue.peek();

			if (isSaved.contains(first.index)) {
				break;
			}

			minQueue.poll();
		}
	}

	private void init() {
		// 내림차순
		maxQueue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n2.value - n1.value;
			}
		});

		// 오름차순
		minQueue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.value - n2.value;
			}
		});

		isSaved = new HashSet<>();
	}
}