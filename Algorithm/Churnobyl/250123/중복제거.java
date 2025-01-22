import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 중복 제거
 */
public class Main {
    static Set<Integer> set = new LinkedHashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (st.hasMoreTokens()) {
            int data = Integer.parseInt(st.nextToken());
            if (!set.contains(data)) {
                set.add(data);
                sb.append(data).append(" ");
            }
        }
    }
}
