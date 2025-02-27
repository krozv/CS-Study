import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 최솟값 우선순위 큐와 최댓갑 우선순위 큐를 각각 만든 후
 * 최솟값이 필요한 경우, 최댓값이 필요한 경우 나눠서 사용하는 문제
 * 반대쪽 큐에서 이미 제거된 값이 이후 포함되지 않도록 그때그때 같이 지워주자
 */
public class pg이중우선순위큐 {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        String[] operations = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};

        System.out.println(Arrays.toString(solution.solution(operations)));
    }

    private static class Solution {

        private static StringTokenizer st;

        public int[] solution(String[] operations) {
            // 정렬 조건 반대인 우선순위 큐 2개 생성
            PriorityQueue<Integer> smallQueue = new PriorityQueue<>();
            PriorityQueue<Integer> bigQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);

            String query;
            int num;
            for (int i = 0; i < operations.length; i++) {
                st = new StringTokenizer(operations[i]);
                query = st.nextToken();

                // 최댓값/최솟값 우선순위 큐에 전부 넣어줌
                if (query.equals("I")) {
                    num = Integer.parseInt(st.nextToken());
                    smallQueue.add(num);
                    bigQueue.add(num);

                } else {
                    // 최댓값 우선순위 큐에서 뽑은 후 최솟갑 우선순위 큐에서도 제거
                    if (!bigQueue.isEmpty() && st.nextToken().equals("1")) {
                        smallQueue.remove(bigQueue.poll());

                    } else if (!smallQueue.isEmpty()) {
                        bigQueue.remove(smallQueue.poll());
                    }
                }
            }

            // 비어있으면 [0, 0] 반환
            if (bigQueue.isEmpty() || smallQueue.isEmpty()) {
                return new int[]{0, 0};
            } else {
                return new int[]{bigQueue.peek(), smallQueue.peek()};
            }
        }
    }
}
