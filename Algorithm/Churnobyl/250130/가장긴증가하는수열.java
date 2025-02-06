import java.io.*;
import java.util.*;

/**
 * LIS - DP + 이분탐색으로 풀기2
 */
public class Main {
    static int N;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        setting(); // 입력 세팅

        List<Integer> sequence = solve();

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        sb.append(sequence.size()).append("\n");

        for (Integer elem : sequence) {
            sb.append(elem).append(" ");
        }

        System.out.println(sb);
    }

    private static List<Integer> solve() {
        List<Integer> LIS = new ArrayList<>(); // LIS 리스트 초기화
        int[] prev = new int[N]; // LIS에 속하는 원소를 추적하기 위한 prev 배열
        List<Integer> indexList = new ArrayList<>(); // LIS 리스트에 속한 원소의 실제 arr에서의 인덱스 저장
        Arrays.fill(prev, -1); // 이전 인덱스가 없음을 확인하기 위해 -1로 초기화

        // 원소들을 순회하면서
        for (int i = 0; i < N; i++) {
            int idx = Collections.binarySearch(LIS, arr[i]); // 배열 안에 원소가 들어갈 위치를 찾기 위해 이분탐색
            if (idx < 0) idx = -(idx + 1); // lower_bound 위치 찾기

            if (idx == LIS.size()) { // idx가 배열의 인덱스보다 더 큰 인덱스를 가리키면 어느 값보다도 큰 값이란 뜻
                LIS.add(arr[i]); // 배열에 추가
                indexList.add(i); // indexList에도 동일한 위치에 i도 저장
            }
            else { // 아니면
                LIS.set(idx, arr[i]); // 배열 안에 해당 원소를 업데이트
                indexList.set(idx, i); // indexList에도 동일한 위치에 i로 업데이트
            }
            // LIS 리스트에 첫번째로 추가된 값을 제외하고
            if (idx > 0) prev[i] = indexList.get(idx - 1); // LIS배열에 idx - 1에 저장되어 있는 인덱스 저장
        }

        // LIS 원소 역추적
        List<Integer> lisSequence = new ArrayList<>();
        int lastIdx = indexList.get(indexList.size() - 1); // LIS에 저장된 마지막 인덱스 가져오기
        while (lastIdx != -1) { // 가장 처음으로 돌아갈 때까지
            lisSequence.add(arr[lastIdx]);
            lastIdx = prev[lastIdx]; // 이전 인덱스로 업데이트
        }
        Collections.reverse(lisSequence); // 마지막부터 거꾸로 삽입했으므로 다시 바꾸기

        return lisSequence;
    }

    private static void setting() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }
}
