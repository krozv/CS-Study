class Solution {
    static final int LONG_TYPE_BITS = Long.BYTES * 8;
    
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        
        for (int idx = 0; idx < numbers.length; idx++) {
            long number = numbers[idx];
            
            // 하위 비트부터 0인 비트를 탐색
            for (int bit = 0; bit < LONG_TYPE_BITS; bit++) {
                // 비트 값이 0인 경우, 해당 비트의 값을 1로 바꾼다.
                if (isZeroBit(number, bit)) {
                    number = toggleBit(number, bit);
                    
                    // 최하위 비트가 아닌 비트가 0이라면, 바로 하위 비트의 값도 변경한다.
                    if (bit > 0) {
                        number = toggleBit(number, bit-1);
                    }
                    
                    break;
                }
            }
            
            answer[idx] = number;
        }

        return answer;
    }
    
    private long toggleBit(long number, int bit) {
        return number ^ (1L << bit);
    }
    
    private boolean isZeroBit(long number, int bit) {
        return (number & (1L << bit)) == 0;
    }
}