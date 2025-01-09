from itertools import combinations
def solution(relation):
    '''
    후보키의 개수를 count
    어떻게 할 건지?
    필요한 변수
    1. 후보키가 가능한 key의 인덱스 리스트 candidate_index_list
    2. 남은 key index 리스트 key_index_list
    3. 순회 중인 Key 개수 변수 key_cnt
    relation의 길이를 측정해서 key 개수 count
    첫번째 key부터 후보키 가능한지 순회하면서 확인.
    가능할 경우 -> 2번에서 pop, 1번 append
    불가능할 경우 -> pass
    전체 순회 후, 2번에서 남은 key에 대해 3번을 +1 증가해가면서 조합으로 순휘
    '''
    col_cnt = len(relation[0])
    key_index_list = [i for i in range(col_cnt)]

    candidate_index_list = []

    # 유일성 확인
    def check_unique(key_idx_comb):
        total = set()
        for row in relation:
            val = tuple(row[idx] for idx in key_idx_comb)
            if val in total:
                return False
            total.add(val)
        return True

    # 최소성 확인
    def check_minial(key_idx_comb):
        for candidate in candidate_index_list:
            # key_idx_comb가 최소성 X
            if set(candidate).issubset(key_idx_comb):
                return False
        return True


    for key_cnt in range(1, col_cnt+1):
        for key_idx_comb in combinations(key_index_list, key_cnt):
            if check_unique(key_idx_comb) and check_minial(key_idx_comb):
                candidate_index_list.append(key_idx_comb)

    return len(candidate_index_list)