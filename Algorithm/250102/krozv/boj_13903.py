from collections import deque

def solution():
    import sys
    input = sys.stdin.readline()
    data = input()
    
    r, c = map(int, data[0].split())
    arr = [list(map(int, data[i + 1].split())) for i in range(r)]
    delta = [list(map(int, line.split())) for line in data[r + 1:]]
    
    def bfs(start, r, c, delta, arr, min_time):
        visited = set()
        queue = deque([(*start, 0)])  # (*start, 0) unpacks the start position and appends the time
        
        while queue:
            x, y, time = queue.popleft()

            if x == r - 1:
                return time

            key = f"{x}:{y}"
            if key in visited:
                continue
            visited.add(key)

            if time >= min_time:
                continue

            for dx, dy in delta:
                nx, ny = x + dx, y + dy
                if 0 <= nx < r and 0 <= ny < c and arr[nx][ny] == 1:
                    queue.append((nx, ny, time + 1))
        
        return -1

    min_time = float('inf')
    for j in range(c):
        if arr[0][j] == 1:
            time = bfs((0, j), r, c, delta, arr, min_time)
            if time != -1:
                min_time = min(min_time, time)

    print(-1 if min_time == float('inf') else min_time)

if __name__ == "__main__":
    solution()
