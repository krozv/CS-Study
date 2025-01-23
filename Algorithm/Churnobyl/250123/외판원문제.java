import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 외판원 순회 - 동적 계획법
 */
public class Main {
    static int N;
    static int[][] weight;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        weight = new int[N][N];
        dp = new int[N][1 << N];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE); // dp 배열 최댓값으로 초기화
        }

        dp[0][1] = 0; // 0번 도시에서 시작, 0번 도시를 방문 가능 상태로 설정 (초기 비용 0)

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                weight[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 모든 방문 상태를 탐색
        for (int visited = 1; visited < (1 << N); visited++) { // 방문 상태를 1부터 2^N - 1까지 탐색
            for (int currentCity = 0; currentCity < N; currentCity++) { // 현재 도시를 설정
                // 현재 도시가 방문할 경로 visited에 포함되어 있지 않다면 패스
                if ((visited & (1 << currentCity)) == 0) continue;

                // 다음 방문할 도시 찾기
                for (int nextCity = 0; nextCity < N; nextCity++) {
                    // 이미 방문한 도시거나, 경로가 없을 경우 패스
                    if ((visited & (1 << nextCity)) != 0 || weight[currentCity][nextCity] == 0) {
                        continue;
                    }

                    // dp값이 유효하지 않으면 패스(앞선 연산에서 경로가 없어서 업데이트가 안됐던 것)
                    if (dp[currentCity][visited] == Integer.MAX_VALUE) continue;

                    // 다음 도시를 방문한 상태의 최소 비용 업데이트
                    dp[nextCity][(visited | (1 << nextCity))] =
                            Math.min(
                                    dp[nextCity][visited | (1 << nextCity)], // 기존 값
                                    dp[currentCity][visited] + weight[currentCity][nextCity] // 현재 도시에서 다음 도시로 이동 비용 합산
                            );
                }
            }
        }

        int answer = Integer.MAX_VALUE; // 최종적으로 최소 비용을 저장할 변수

        // 모든 도시를 방문한 상태에서 시작 도시로 돌아가는 경로의 최소 비용 계산
        for (int i = 1; i < N; i++) { // 1번 도시부터 N - 1번 도시까지 확인
            // 시작 도시로 갈 수 있는 경로가 있고, 비용이 유효하다면
            if (weight[i][0] != 0 && dp[i][(1 << N) - 1] != Integer.MAX_VALUE) {
                answer = Math.min(answer, dp[i][(1 << N) - 1] + weight[i][0]); // 최소 비용 업데이트
            }
        }

        System.out.println(answer);
    }
}
