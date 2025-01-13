# 김밥천국의 계단
"""
[조건]
N, K
N: 계단 개수
K: 계단을 오르는 횟수
1. 계단 1칸 올라가기
2. i+(i/2) 이하 번째 계단으로 이동
정확히 K번째에서 N번째 계단에 위치하면 -> 김밥 냠냠
1 <= n, k <= 1,000,000

[생각]
1. bfs나 dfs로 풀이 가능한가?
i+1
i+(i/2)
k번째 넘어가면 pass
현재 계단 위치가 n인데 k 미만일 경우 pass 
- dfs 불가능 (Recursion error)
- bfs 가능은 할듯? 시간복잡도 O(n^2): Time error
1초 = 1,000,000,000
- dp 
"""
import sys
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

n, k = map(int, input().split())
dp = [1e8] * (n+1) # dp[i]: i번째 계단을 오르는 데 필요한 횟수

dp[0], dp[1] = 0, 1

i = 1
while i <= n:
    if (i+i//2) <= n: 
        dp[i+i//2] = min(dp[i+i//2], dp[i] + 1)
    if (i+1) <= n: 
        dp[i+1] = min(dp[i+1], dp[i] + 1)
    i += 1

print("minigimbob" if dp[n] <= k else "water")