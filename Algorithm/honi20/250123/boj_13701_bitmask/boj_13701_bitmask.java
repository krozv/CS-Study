/**
 * - int 타입은 8바이트(= 32비트)
 * - 즉, int 타입의 원소 1개가 32개의 숫자 검사 가능
 * 
 * - 입력되는 수의 범위 = 2^25
 * - 2^25를 32로 나누면 약 2^20이다.
 * => 2^20의 크기를 같는 배열에 각 32개의 숫자를 검사한다.
 * 	   - arr[0] -> 0 ~ 31 (0*32 <= val < 1*32)
 *     - arr[1] -> 32 ~ 63 (1*32 <= val < 2*32)
 *     - ...
 */
import java.io.*;
import java.util.*;

public class Main_13701_bitmask {
	static BufferedReader br;
	static StringBuilder sb;
	static StringTokenizer st;

	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		arr = new int[1 << 20];
		
		st = new StringTokenizer(br.readLine().trim());
		while (st.hasMoreTokens()) {
			int num = Integer.parseInt(st.nextToken());
			int arr_idx = num / 32;
			int bits_idx = num % 32;
					
			// 숫자 포함되었는지 확인
			if (!containsBit(arr[arr_idx], bits_idx)) {
				arr[arr_idx] |= (1 << bits_idx);
				sb.append(num).append(" ");
			}
		}
		
		System.out.println(sb);
	}
	
	private static boolean containsBit(int value, int bit) {
		return (value & (1 << bit)) == (1 << bit);
	}
}
