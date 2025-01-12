import java.io.*;
import java.util.*;

public class Main_17623 {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int MAX = 1000;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	
	static final String PARENTHESES = "12";
	static final String CURLY = "34";
	static final String SQUARE = "56";
	
	static final Map<Character, Character> brackets;
	
	static {
		brackets = Map.of(
				'1', '(',
				'2', ')',
				'3', '{',
				'4', '}',
				'5', '[',
				'6', ']'
				);
	}
	
	static int testCase;
	static int number;
	static String[] dp;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		testCase = Integer.parseInt(br.readLine().trim());
		
		// dp 설정
		init();
		
		while (testCase-- > 0) {
			number = Integer.parseInt(br.readLine().trim());
			
			sb.append(toString(dp[number])).append("\n");
		}
		
		System.out.println(sb);
	}
	
	private static void init() {
		dp = new String[MAX + 1];
		Arrays.fill(dp, "");
		dp[1] = PARENTHESES;
		dp[2] = CURLY;
		dp[3] = SQUARE;
		
		for (int idx = 2; idx <= MAX; idx++) {
			// +
			for (int pre = 1; pre < idx; pre++) {
				dp[idx] = getMinValue(dp[idx], concate(dp[pre], dp[idx-pre]));
			}
			
			// * 2
			dp[idx] = (idx % 2 == 0) ? getMinValue(dp[idx], surround(dp[idx/2], PARENTHESES)) : dp[idx];
			
			// * 3
			dp[idx] = (idx % 3 == 0) ? getMinValue(dp[idx], surround(dp[idx/3], CURLY)) : dp[idx];
			
			// * 5
			dp[idx] = (idx % 5 == 0) ? getMinValue(dp[idx], surround(dp[idx/5], SQUARE)) : dp[idx];
		}
	}
	
	// 문자열의 접합 : 변환값이 작도록 순서 결정
	private static String concate(String str1, String str2) {
		return getMinValue(str1 + str2, str2 + str1);
	}
	
	// 둘러싸인 문자열
	private static String surround(String str, String bracket) {
		return bracket.charAt(LEFT) + str + bracket.charAt(RIGHT);
	}
	
	// 두 문자열 중 숫자로 변환한 값이 더 작은 값 반환
	private static String getMinValue(String str1, String str2) {
		if (str1.length() == 0) {
			return str2;
		}
		
		// 두 문자열의 길이가 같다면, 상위 숫자가 더 작은 문자열 반환
		if (str1.length() == str2.length()) {
			for (int idx = 0; idx < str1.length(); idx++) {
				int num1 = str1.charAt(idx) - '0';
				int num2 = str2.charAt(idx) - '0';
				
				if (num1 == num2) continue;
				
				return (num1 < num2) ? str1 : str2;
			}
		}
		
		// 길이가 다르다면, 길이가 더 짧은 문자열 반환
		return (str1.length() < str2.length()) ? str1 : str2;
	}
	
	private static String toString(String str) {
		String result = "";
		
		for (int idx = 0; idx < str.length(); idx++) {
			result += brackets.get(str.charAt(idx));
		}
		
		return result;
	}
}
