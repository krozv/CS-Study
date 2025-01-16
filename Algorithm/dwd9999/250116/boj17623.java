import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 거지같은 문제
 */
public class SA17623 {

    // 특정 val의 가장 낮은 DMAP 저장
    private static String[] result;
    private static StringBuilder sb;

    // 괄호 감싸기 계산 편의성을 위한 배열
    private static final char[][] TO_MULTIPLY = {
            {5, '5', '6'},
            {3, '3', '4'},
            {2, '1', '2'},
    };

    // 출력 편의성을 위한 배열
    private static final char[] TO_PRINT = {
            '(', ')',
            '{', '}',
            '[', ']',
    };

    private static final int MAX = 1_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        result = new String[MAX + 1];
        result[1] = "12";
        result[2] = "34";
        result[3] = "56";

        int now;
        int testCase = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < testCase; tc++) {
            now = Integer.parseInt(br.readLine());

            print(findMinDmap(now));
        }

        System.out.println(sb);
    }

    // 주어진 val을 가장 작은 DMAP값 반환
    private static String findMinDmap(int val) {
        if (result[val] != null) {
            return result[val];
        }

        // 괄호로 감싸고 있는 경우 순회
        for (int i = 0; i < TO_MULTIPLY.length; i++) {
            if (val % TO_MULTIPLY[i][0] == 0) {
                result[val] = compare(result[val], TO_MULTIPLY[i][1] + findMinDmap(val / TO_MULTIPLY[i][0]) + TO_MULTIPLY[i][2]);
            }
        }

        // 괄호 + 괄호로 이루어진 경우 순회
        for (int i = 1; i < val; i++) {
            result[val] = compare(result[val], findMinDmap(i) + findMinDmap(val - i));
        }

        return result[val];
    }

    // 2개 중 DMAP이 작은쪽을 반환하는 메서드
    private static String compare(String resultA, String resultB) {
        if (resultA == null) {
            return resultB;
        }

        if (resultA.length() < resultB.length()) {
            return resultA;
        } else if (resultA.length() > resultB.length()) {
            return resultB;
        }
        if (resultA.compareTo(resultB) < 0) {
            return resultA;
        } else {
            return resultB;
        }
    }

    // DMAP을 괄호로 변환하여 출력
    private static void print(String number) {
        for (int i = 0; i < number.length(); i++) {
            sb.append(TO_PRINT[number.charAt(i) - '1']);
        }
        sb.append("\n");
    }
}