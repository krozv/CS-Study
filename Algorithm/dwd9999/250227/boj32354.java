import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj32354 {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb = new StringBuilder();
    private static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        int queryCount = Integer.parseInt(br.readLine());
        Deck deck = new Deck(queryCount);

        String query;
        for (int i = 0; i < queryCount; i++) {
            st = new StringTokenizer(br.readLine());
            query = st.nextToken();
            if (query.equals("push")) {
                deck.push(Integer.parseInt(st.nextToken()));
            } else if (query.equals("pop")) {
                deck.pop();
            } else if (query.equals("restore")) {
                deck.restore(Integer.parseInt(st.nextToken()));
            } else {
                deck.print();
            }
        }

        System.out.print(sb);
    }

    private static class Deck {
        private int turn;
        private Status[] record;

        public Deck(int maxTurn) {
            turn = 0;
            record = new Status[maxTurn + 1];
            record[0] = new Status(0, null);
        }

        void push(int card) {
            turn++;
            record[turn] = new Status(card, record[turn - 1]);
        }

        void pop() {
            turn++;
            record[turn] = record[turn - 1].prev;
        }

        void restore(int target) {
            turn++;
            record[turn] = record[target];
        }


        void print() {
            turn++;
            record[turn] = record[turn - 1];
            sb.append(record[turn].total).append("\n");
        }

        private static class Status {
            int top;
            long total;
            Status prev;

            public Status(int top, Status prev) {
                this.top = top;
                this.prev = prev;
                if (prev != null) {
                    this.total = prev.total + top;
                } else {
                    this.total = top;
                }
            }

        }
    }
}
