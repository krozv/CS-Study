import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static final int MAX = 33_554_432;
	
	static BitSet bitset = new BitSet();
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine().trim());
		while (st.hasMoreTokens()) {
			int num = Integer.parseInt(st.nextToken());
			
			// 입력된 수인지 확인
			if (!bitset.get(num)) {
				bitset.set(num);
				sb.append(num).append(" ");
			}
		}
		
		System.out.println(sb);
	}
}