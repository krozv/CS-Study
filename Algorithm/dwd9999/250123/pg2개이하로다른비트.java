import java.util.Arrays;

public class pg2개이하로다른비트 {

    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        long[] numbers = new long[]{2, 7};
        System.out.println(Arrays.toString(solution.solution(numbers)));
    }

    /**
     * 숫자가 더 커야하니 가장 마지막에 있는 0을 1로 바꿔주자
     * 이때 작을수록 좋으니 그 이후에 다음으로 나오는 1이 있다면 그것을 0으로 바꾸자
     */
    private static class Solution {
        private long[] solution(long[] numbers) {
            long[] answer = new long[numbers.length];

            String now;
            StringBuilder result;
            int index;
            for (int i = 0; i < numbers.length; i++) {
                now = Long.toBinaryString(numbers[i]);
                result = new StringBuilder(now);

                // 가장 낮은 자릿수의 0 찾기
                index = now.lastIndexOf('0');

                if (index == -1) {
                    // 0이 없으면 맨 앞에 1 넣기
                    result.insert(0, '1');

                    // 방금 넣은 1을 제외한 가장 높은 자릿수의 1을 0으로 바꾸기
                    index = result.indexOf("1", 1);
                    if (index != -1) {
                        result.setCharAt(index, '0');
                    }
                } else {
                    // 찾은 0을 1로 바꾸기
                    result.setCharAt(index, '1');

                    // 방금 넣은 1을 제외한 가장 높은 자릿수의 1을 0으로 바꾸기
                    index = result.indexOf("1", index + 1);
                    if (index != -1) {
                        result.setCharAt(index, '0');
                    }
                }
                answer[i] = Long.parseLong(result.toString(), 2);
            }

            return answer;
        }
    }
}
