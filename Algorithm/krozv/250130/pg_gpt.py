from collections import defaultdict
from bisect import bisect_left

def solution(info, query):
    db = defaultdict(list)
    
    # 지원자 정보 파싱 및 저장
    for applicant in info:
        language, position, career, food, score = applicant.split()
        score = int(score)
        
        # 모든 조합을 key로 저장
        keys = [
            (language, position, career, food),
            (language, position, career, "-"),
            (language, position, "-", food),
            (language, position, "-", "-"),
            (language, "-", career, food),
            (language, "-", career, "-"),
            (language, "-", "-", food),
            (language, "-", "-", "-"),
            ("-", position, career, food),
            ("-", position, career, "-"),
            ("-", position, "-", food),
            ("-", position, "-", "-"),
            ("-", "-", career, food),
            ("-", "-", career, "-"),
            ("-", "-", "-", food),
            ("-", "-", "-", "-")
        ]
        
        for key in keys:
            db[key].append(score)
    
    # 점수 리스트 정렬 (이진 탐색을 위해)
    for key in db:
        db[key].sort()

    answer = []
    
    # 쿼리 처리
    for q in query:
        q = q.replace(" and ", " ")
        *conditions, score = q.split()
        score = int(score)
        
        key = tuple(conditions)
        
        if key in db:
            scores = db[key]
            idx = bisect_left(scores, score)  # score 이상인 첫 번째 위치 찾기
            answer.append(len(scores) - idx)
        else:
            answer.append(0)
    
    return answer
