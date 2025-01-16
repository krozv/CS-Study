def solution(m, n, puddles):
    dp = [[0] * m for _ in range(n)]

    for puddle in puddles:
        if puddle:
            j, i = puddle
            dp[i-1][j-1] = -1
        
    for i in range(n):
        if dp[i][0] == -1: break
        dp[i][0] = 1
    for j in range(m):
        if dp[0][j] == -1: break
        dp[0][j] = 1
    
    for i in range(n):
        for j in range(m):
            if dp[i][j] == -1:
                dp[i][j] = 0
            elif (i-1) >= 0 and (j-1) >= 0:
                dp[i][j] = (dp[i-1][j] + dp[i][j-1]) % 1000000007
    return dp[n-1][m-1]