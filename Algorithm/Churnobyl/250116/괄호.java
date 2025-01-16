import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  static String[] dp; // DP 배열
  static char[] c = { '(', ')', '{', '}', '[', ']' }; // 괄호 매핑

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(br.readLine());

    // 테스트 케이스 최대값 계산
    int maxN = 0;
    int[] testCases = new int[t];
    for (int i = 0; i < t; i++) {
      testCases[i] = Integer.parseInt(br.readLine());
      maxN = Math.max(maxN, testCases[i]);
    }

    // DP 테이블 계산
    computeDP(maxN);

    // 결과 출력
    StringBuilder sb = new StringBuilder();
    for (int n : testCases) {
      StringBuilder result = new StringBuilder();
      for (int j = 0; j < dp[n].length(); j++) {
        result.append(c[dp[n].charAt(j) - '1']);
      }
      sb.append(result).append("\n");
    }

    System.out.println(sb);
  }

  private static void computeDP(int maxN) {
    dp = new String[maxN + 1];

    // 초기값 설정
    dp[1] = "12"; // ()
    dp[2] = "34"; // {}
    dp[3] = "56"; // []

    // DP 계산
    for (int i = 4; i <= maxN; i++) {
      dp[i] = null;

      // x2, x3, x5 괄호 감싸기 연산
      if (i % 2 == 0) {
        dp[i] = cmp(dp[i], "1" + dp[i / 2] + "2"); // (X)
      }
      if (i % 3 == 0) {
        dp[i] = cmp(dp[i], "3" + dp[i / 3] + "4"); // {X}
      }
      if (i % 5 == 0) {
        dp[i] = cmp(dp[i], "5" + dp[i / 5] + "6"); // [X]
      }

      // 덧셈 연산
      for (int j = 1; j < i; j++) {
        dp[i] = cmp(dp[i], dp[j] + dp[i - j]);
      }
    }
  }

  private static String cmp(String a, String b) {
    if (a == null || a.length() > b.length()) return b; // a가 없거나 길이가 더 크면 b 선택
    if (b.length() > a.length()) return a; // b의 길이가 더 크면 a 선택
    return a.compareTo(b) <= 0 ? a : b; // 길이가 같으면 사전 순 비교
  }
}
