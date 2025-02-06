import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
	static Map<String, List<Integer>> db = new HashMap<>();
	
    public static int[] solution(String[] info, String[] query) {
    	preprocess(info);
    	
        int[] result = new int[query.length];
        
        for (int i = 0; i < query.length; i++) {
			String[] q = query[i].replaceAll(" and ", "").split(" ");
			String key = q[0];
			int score = Integer.parseInt(q[1]);
			result[i] = countAll(key, score);
		}
        
        return result;
    }
    
    
    
    private static int countAll(String key, int score) {
		if (!db.containsKey(key)) return 0;
		
		List<Integer> scoreList = db.get(key);
		int left = 0;
		int right = scoreList.size();
		int mid = 0;
		
		while (left < right) {
			mid = (left + right) / 2;
			if (scoreList.get(mid) < score) left = mid + 1;
			else right = mid;
		}
		
		return scoreList.size() - left;
	}



	private static void preprocess(String[] info) {
		for (String string : info) {
			String[] p = string.split(" ");
			int score = Integer.parseInt(p[4]);
			hashing(p, score, 0, "");
		}
		
		for (String key : db.keySet()) {
			List<Integer> list = db.get(key);
			Collections.sort(list);
		}
	}



	private static void hashing(String[] p, int score, int depth, String curr) {
		if (depth == 4) {
			if (!db.containsKey(curr)) {
				List<Integer> list = new ArrayList<>();
				db.put(curr, list);
			}
			db.get(curr).add(score);
			return;
		}
		
		hashing(p, score, depth + 1, curr + "-");
		hashing(p, score, depth + 1, curr + p[depth]);
	}
}