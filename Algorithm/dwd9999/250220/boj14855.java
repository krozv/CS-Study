/**
 * 대표적인 Knapsack 문제
 * 밀가루만 있다면 만들 수 있는 스페셜 메뉴를 고려해 메뉴에 포함해주자
 */
public class boj14855 {

    private static int flourCount, manduCount;
    private static int[][] maxCost, mandu;

    public static void main(String[] args) throws Exception {
        flourCount = read();
        manduCount = read();
        maxCost = new int[manduCount + 2][flourCount + 1];

        mandu = new int[manduCount + 2][3];
        // 스페셜 메뉴 정보
        mandu[manduCount + 1] = new int[]{Integer.MAX_VALUE, read(), read()};

        // 제작 가능 개수, 밀가루 소모량, 가격 저장
        for (int i = 1; i <= manduCount; i++) {
            mandu[i] = new int[]{read() / read(), read(), read()};
        }

        int count, max;
        // i번째 만두까지 포함하고, 밀가루가 j개만큼 있을때
        for (int i = 1; i <= manduCount + 1; i++) {
            count = mandu[i][0];
            for (int j = 1; j <= flourCount; j++) {
                max = 0;
                for (int k = 0; k <= count; k++) {
                    // 밀가루가 부족해 더 보는게 무의미한 경우
                    if (k * mandu[i][1] > j) {
                        break;
                    }

                    // 만두를 k개 만드는 경우가 총액이 더 높으면 갱신
                    max = Math.max(max, k * mandu[i][2] + maxCost[i - 1][j - k * mandu[i][1]]);
                }

                maxCost[i][j] = max;
            }
        }

        System.out.println(maxCost[manduCount + 1][flourCount]);
    }

    private static int read() throws Exception {
        int c, n = System.in.read() & 15;
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return n;
    }
}
