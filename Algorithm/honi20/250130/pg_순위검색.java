import java.util.*;

class Solution {
    static final String[] LANG = {"-", "cpp", "java", "python"};
    static final String[] TYPE = {"-", "backend", "frontend"};
    static final String[] EXPER = {"-", "junior", "senior"};
    static final String[] FOOD = {"-", "chicken", "pizza"};
    static final int SCORE = 4;
    
    static Map<String, List<Integer>> scores;
    
    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        
        // 각각의 조건에 만족하는 지원자들의 점수 리스트를 hashMap에 저장
        initScores(info);
        
        // query에 맞춰 기준 점수 이상인 인원수 탐색
        for (int idx = 0; idx < query.length; idx++) {
            answer[idx] = getAnswer(query[idx]);
        }
        
        return answer;
    }
    
    private int getAnswer(String query) {
        String[] arr = query.split(" ");
        String condition = arr[0] + arr[2] + arr[4] + arr[6];
        int score = Integer.parseInt(arr[7]);
        
        // map을 통해 현재 조건을 만족하는 지원자 점수 리스트를 가져온다.
        List<Integer> list = scores.get(condition);
        
        // 기준 점수보다 큰 지원자가 없는 경우, 0 반환
        if (list.size() == 0 || list.get(list.size()-1) < score) {
            return 0;
        }
        
        // 이분 탐색을 통해 기준 점수 이상의 지원자 수 반환
        return list.size() - binarySearch(list, score);
    }
    
    // 리스트에서 현재 원소값과 같거나 첫 번째로 큰 원소의 인덱스를 반환하는 함수
    private int binarySearch(List<Integer> list, int element) {
        int start = 0;
        int end = list.size()-1;
        
        while (start < end) {
            int mid = (start + end) / 2;
            
            if (list.get(mid) < element) {
                start = mid + 1;
            }
            else {
                end = mid;
            }
        }
        
        return end;
    }
    
    private void initScores(String[] info) {
        scores = new HashMap<>();
        
        // 가능한 모든 조건의 경우를 탐색한다.
        for (int lang = 0; lang < LANG.length; lang++) {
            for (int type = 0; type < TYPE.length; type++) {
                for (int exper = 0; exper < EXPER.length; exper++) {
                    for (int food = 0; food < FOOD.length; food++) {
                        // 조건을 만족하는 지원자의 점수 리스트를 저장하는 hashMap을 구한다.
                        findFitPerson(info, LANG[lang], TYPE[type], EXPER[exper], FOOD[food]);
                    }
                }
            }
        }
    }
    
    // 현재 조건을 만족하는 지원자의 점수 리스트를 hashMap에 저장하는 함수
    private void findFitPerson(String[] info, String lang, String type, String exper, String food) {
        String condition = lang + type + exper + food;
        List<Integer> list = new ArrayList<>();
        
        for (int idx = 0; idx < info.length; idx++) {
            if (isFitPerson(info[idx], lang, type, exper, food)) {
                list.add(Integer.parseInt(info[idx].split(" ")[SCORE]));
            }
        }
        
        // 이분탐색을 위해 점수 오름차순 정렬
        Collections.sort(list);
        scores.put(condition, list);
    }
    
    // 현재 지원자가 주어진 조건을 만족하는지 반환하는 함수
    private boolean isFitPerson(String person, String lang, String type, String exper, String food) {
        String[] arr = person.split(" ");
        
        if (!lang.equals("-") && !lang.equals(arr[0])) {
            return false;
        }
        if (!type.equals("-") && !type.equals(arr[1])) {
            return false;
        }
        if (!exper.equals("-") && !exper.equals(arr[2])) {
            return false;
        }
        if (!food.equals("-") && !food.equals(arr[3])) {
            return false;
        }
        
        return true;
    }
}