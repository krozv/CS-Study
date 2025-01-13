def solution(m, n, puddles):
    dp = [0] * m
    dp[0] = 1
    
    puddle_set = set((x-1, y-1) for y, x in puddles)
    
    for i in range(n):
        for j in range(m):
            if (i, j) in puddle_set:
                dp[j] = 0
            elif j > 0:
                dp[j] = (dp[j] + dp[j-1]) % 1000000007
    return dp[m-1] 