/**
[조건]
- 유일성 : 릴레이션에 있는 모든 튜플에 대해 유일하게 식별 가능
- 최소성 : 릴레이션의 유일성을 유지하는데 꼭 필요한 속성들로만 이루어져야 함

[출력]
- 후보 키의 최대 개수
*/
import java.util.*;

class Solution {
    static int tupleCnt, attrCnt;
    static int bitCnt;
    static boolean[] isMinimal;
    
    public int solution(String[][] relation) {
        int answer = 0;
        
        tupleCnt = relation.length;
        attrCnt = relation[0].length;
        bitCnt = (1 << attrCnt);
        
        isMinimal = new boolean[bitCnt];
        Arrays.fill(isMinimal, true);
        
        // 속성의 후보 집합들을 탐색한다.
        for (int bits = 1; bits < bitCnt; bits++) {
            // 최소성과 유일성을 만족하는지 확인
            if (isMinimal[bits] && isUnique(bits, relation)) {
                // 현재 집합을 포함하는 후보 집합의 최소성 여부 false 처리
                updateMinimal(bits, 0);
                
                ++answer;
            }
        }
        
        return answer;
    }
    
    private boolean isUnique(int bits, String[][] relation) {
        if (tupleCnt == 1) {
            return true;
        }
        
        for (int tuple1 = 1; tuple1 < tupleCnt; tuple1++) {
            for (int tuple2 = 0; tuple2 < tuple1; tuple2++) {
                boolean isSame = true;
                
                for (int attr = 0; attr < attrCnt; attr++) {
                    // 현재 속성이 후보 집합에 포함됨 && 속성에 대한 튜플 값이 서로 다름 -> 유일성 만족
                    if (isIncludedInBits(bits, attr) && isDifferent(relation[tuple1][attr], relation[tuple2][attr])) {
                        isSame = false;
                        break;
                    }
                }
                
                // 튜플의 값이 같은 쌍이 하나라도 있으면 유일성 만족 X
                if (isSame) {
                    return false;
                }
            }   
        }
        
        return true;
    }
    
    private void updateMinimal(int curBits, int curIdx) {
        if (curIdx > attrCnt) {
            return;
        }
        
        // 최소성 만족하지 않음 표시
        isMinimal[curBits] = false;
            
        // 1. 현재 속성이 이미 후보 집합에 포함되어 있는 경우, 다음 속성으로 이동
        if (isIncludedInBits(curBits, curIdx)) {
            updateMinimal(curBits, curIdx+1);
        }
        
        // 2. 현재 속성이 후보 집합에 포함되어 있지 않은 경우
        else {
            // 2-1. 속성을 포함하지 않고 다음 속성으로 이동
            updateMinimal(curBits, curIdx+1);
            
            // 2-2. 속성을 포함하고 다음 속성으로 이동
            int nextBits = changeBit(curBits, curIdx);
            updateMinimal(nextBits, curIdx+1);
        }
    }
    
    public int changeBit(int bits, int idx) {
        return bits | (1 << idx);
    }
    
    private boolean isDifferent(String value1, String value2) {
        if (value1.equals(value2)) {
            return false;
        }
        
        return true;
    }
    
    private boolean isIncludedInBits(int bits, int idx) {
        if ((bits & (1 << idx)) == 0) {
            return false;
        }
        
        return true;
    }
}