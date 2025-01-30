# 그르다 김가놈
"""
[조건]
꼬다리 양쪽에서 균일하게 Kcm
김밥의 길이 < 2K -> 한쪽만 꼬다리를 싹둑
김밥의 길이 <= K -> 김밥 폐기
김밥은 모두 일정한 길이 P
P를 최대한 길게, M개 이상
P = ?
이진탐색으로 P를 구함
"""
import sys
sys.stdin = open("input.txt", "r")
N, K, M = map(int, input().split())
len_list = []

# len_list에 손질된 김밥 길이들을 추가
for _ in range(N):
    L = int(input())
    if L <= K:
        continue
    elif L < 2*K:
        L -= K
    else:
        L -= (2*K)
    len_list.append(L)

# 김밥이 없을 경우
if not len_list:
    print(-1)
    sys.exit()

# 이진탐색
start = 1
end = max(len_list)
result = -1

while start <= end:
    target = (start + end) // 2
    cnt = 0

    for length in len_list:
        cnt += length // target
        if cnt >= M:
            break
    
    if cnt >= M:
        result = target
        start = target + 1
    else:
        end = target - 1
    
print(result)