import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int MAX = 33_554_432;
	
	static boolean[] isInput;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		isInput = new boolean[MAX];
		Arrays.fill(isInput, false);
		
		st = new StringTokenizer(br.readLine().trim());
		while (st.hasMoreTokens()) {
			int num = Integer.parseInt(st.nextToken());
			
			// 입력된 수인지 확인
			if (!isInput[num]) {
				sb.append(num).append(" ");
				isInput[num] = true;
			}
		}
		
		System.out.println(sb);
	}
}