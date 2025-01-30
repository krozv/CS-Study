def solution(info, query):
    """
    [조건]
    info: "개발언어 직군 경력 소울푸드 점수"
    1 <= info <= 50,000
    1 <= query <= 100,000
    [풀이]
    - info를 한번 순회하면서 dictionary 생성
    - query마다 해당 조건에 맞는 info 수 계산
    """
    
    # dictionary
    language_info = dict()
    position_info = dict()
    career_info = dict()
    food_info = dict()
    score_info = dict()
    
    for idx, applicant_info in enumerate(info):
        language, position, career, food, score = applicant_info.split(" ")
        # 데이터베이스 생성
        if not language_info.get(language): language_info[language] = []
        language_info[language].append(idx)
        if not position_info.get(position): position_info[position] = []
        position_info[position].append(idx)
        if not career_info.get(career): career_info[career] = []
        career_info[career].append(idx)
        if not food_info.get(food): food_info[food] = []
        food_info[food].append(idx)
        if not score_info.get(score): score_info[score] = []
        score_info[score].append(idx)
    
    # 쿼리 조회
    answer = []
    for q in query:
        *rest, score = q.rsplit(" ", maxsplit=1)
        language, position, career, food = rest[0].split(" and ")
        
        result = set(list(range(0, len(info))))
        if language != "-": result = set(language_info[language])
        if position != "-":
            result = result.intersection(set(position_info[position]))
        if career != "-":
            result = result.intersection(set(career_info[career]))
        if food != "-":
            result = result.intersection(set(food_info[food]))
        
        candidate = set()
        for key, value in score_info.items():
            if int(key) >= int(score):
                candidate = candidate.union(set(value))
        result = result.intersection(candidate)
        answer.append(len(result))
        
    return answer