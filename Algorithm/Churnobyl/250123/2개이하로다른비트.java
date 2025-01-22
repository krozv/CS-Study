import java.util.*;

class Solution {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            answer[i] = find(numbers[i]);
        }

        return answer;
    }

    private long find(long x) {
        // 이진수로 변환
        String binary = Long.toBinaryString(x);
        
        int lastIdx = -1; // 마지막으로 0인 위치 찾기

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') lastIdx = i;
        }

        if (lastIdx == -1) { // 0인 위치가 없으면
            // 맨앞의 1을 빼고 10으로 대체
            return Long.parseLong("10" + binary.substring(1), 2);
        } else if (lastIdx == binary.length() - 1) { // 0이 가장 마지막에 있으면
            // 0대신 1로 대체
            return Long.parseLong(binary.substring(0, lastIdx) + "1", 2); 
        } else { // 중간에 0이 있으면
            // 0빼고 10으로 대체
            return Long.parseLong(binary.substring(0, lastIdx) + "10" + binary.substring(lastIdx + 2), 2);
        }
    }
}
