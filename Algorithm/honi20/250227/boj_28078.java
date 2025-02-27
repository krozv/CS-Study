import java.io.*;
import java.util.*;

public class Main_28078 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final char BALL = 'b';
	static final char WALL = 'w';
	static final char CLOCKWISE = 'r';

	static final int RIGHT = 0;
	static final int BOTTOM = 1;
	static final int TOP = 3;

	static int queryCnt;
	static Deque<Character> deque;
	static int frontPos; // 큐의 앞이 존재하는 위치(상하좌우)
	static int ballCnt, wallCnt;

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();

		deque = new ArrayDeque<>();
		frontPos = RIGHT;
		ballCnt = 0;
		wallCnt = 0;

		queryCnt = Integer.parseInt(br.readLine().trim());

		while (queryCnt-- > 0) {
			st = new StringTokenizer(br.readLine().trim());
			String command = st.nextToken();

			switch (command) {
			case "push":
				push(st.nextToken().charAt(0));
				break;

			case "pop":
				pop();
				break;

			case "rotate":
				rotate(st.nextToken().charAt(0));
				break;

			case "count":
				sb.append(count(st.nextToken().charAt(0))).append("\n");
				break;
			}
		}

		System.out.println(sb);
	}

	// 큐에 공이나 가림막을 넣고 개수 갱신 -> 큐 업데이트
	private static void push(char command) {
		deque.offer(command);
		updateCnt(command, 1);

		updateDeque();
	}

	// 큐에 맨 앞의 값을 빼고 개수 갱신 -> 큐 업데이트
	private static void pop() {
		if (deque.isEmpty())
			return;

		char item = deque.poll();
		updateCnt(item, -1);

		updateDeque();
	}

	// 큐 앞의 위치 갱신 -> 큐 업데이트
	private static void rotate(char command) {
		// 시계방향
		if (command == CLOCKWISE) {
			frontPos = (frontPos + 1) % 4;
		} 
		else {
			frontPos = (frontPos + 3) % 4;
		}

		updateDeque();
	}

	private static int count(char command) {
		if (command == BALL) {
			return ballCnt;
		}

		return wallCnt;
	}

	private static void updateDeque() {
		// 앞이 아래에 위치 -> 앞에서부터 가림막 전의 모든 공 제거
		if (frontPos == BOTTOM) {
			while (!deque.isEmpty() && deque.peekFirst() != WALL) {
				char item = deque.pollFirst();
				updateCnt(item, -1);
			}
		}
		
		// 앞이 위에 위치 -> 뒤에서부터 가림막 전의 모든 공 제거
		else if (frontPos == TOP) {
			while (!deque.isEmpty() && deque.peekLast() != WALL) {
				char item = deque.pollLast();
				updateCnt(item, -1);
			}
		}
	}

	private static void updateCnt(char item, int val) {
		if (item == BALL) {
			ballCnt += val;
		} 
		else {
			wallCnt += val;
		}
	}
}
