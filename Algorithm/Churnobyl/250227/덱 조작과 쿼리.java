import java.io.*;
import java.util.*;

class Log {
    Log prev;
    long sum;

    public Log(Log prev, long sum) {
        this.prev = prev;
        this.sum = sum;
    }
}

public class Main {
    static int N, lastIndex;
    static Log[] logs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        logs = new Log[N + 1];

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String com = st.nextToken();

            switch (com) {
                case "push": {
                    long x = Long.parseLong(st.nextToken());
                    logs[i] = new Log(logs[lastIndex], (logs[lastIndex] != null ? logs[lastIndex].sum : 0) + x);
                    lastIndex = i;
                    break;
                }
                case "pop": {
                    logs[i] = (logs[lastIndex] != null) ? logs[lastIndex].prev : null;
                    lastIndex = i;
                    break;
                }
                case "restore": {
                    int index = Integer.parseInt(st.nextToken());
                    logs[i] = logs[index];
                    lastIndex = i;
                    break;
                }
                case "print": {
                    sb.append(logs[lastIndex] != null ? logs[lastIndex].sum : 0).append("\n");
                    logs[i] = logs[lastIndex];
                    lastIndex = i;
                    break;
                }
            }
        }

        System.out.print(sb);
    }
}
