import sys
from collections import deque
sys.stdin = open("input.txt", "r")
input = sys.stdin.readline

class Organism:

    def __init__(self, order):
        self.members: set[tuple[int, int]] = set()
        self.number: int = 0
        self.order: int = order

    def is_alive(self) -> bool:
        if not self.members:
            return False

        visited = set()
        queue = deque()
        queue.append(next(iter(self.members)))  # 아무 좌표나 시작점으로 넣기

        while queue:
            x, y = queue.popleft()  # 큐에서 꺼냄 (FIFO)

            if (x, y) in visited:
                continue

            visited.add((x, y))

            for dx, dy in [(-1,0), (1,0), (0,-1), (0,1)]:
                nx, ny = x + dx, y + dy
                if (nx, ny) in self.members and (nx, ny) not in visited:
                    queue.append((nx, ny))

        # 모든 좌표가 이어져 있다면 visited 크기 == member 크기
        return len(visited) == len(self.members)

    def move_place(self):
        return

    def __repr__(self):
        return f"{self.order}번째 미생물 | {self.number}개"

total: dict[int, Organism] = dict()

# 1. 미생물 투입
def input_organ(place, organ, order):
    x1, y1, x2, y2 = organ

    new_organ = Organism(order)
    total[order] = new_organ

    for y in range(y1, y2):
        for x in range(x1, x2):
            # 만약 기존에 다른 미생물이 있었다면
            if place[y][x]:
                prev_order = place[y][x]
                prev_organ: Organism = total[prev_order]
                prev_organ.members.remove((y, x))
                prev_organ.number -= 1

            place[y][x] = order
            new_organ.members.add((y, x))
            new_organ.number += 1

    for order, organ in total.items():
        if not organ.is_alive():
            for member in organ.members:
                y, x = member
                place[y][x] = 0

            organ.members = set()
            organ.number = 0

    print(total)
    for i in range(len(place)):
        print(place[i])
    print()
    return


# 2. 배양 용기 이동
def move(N) -> None:
    for key, organ in sorted(total.items(), key=lambda x: x[0], reverse=True):
        pass
    pass


# 3. 점수 계산
def calculate_score():
    pass


def solution():
    N, Q = map(int, input().split())
    place = list([0] * N for _ in range(N))

    for q in range(Q):
        order = q+1
        organ = map(int, input().split())
        input_organ(place, organ, order)
        move(N)

if __name__ == "__main__":
    solution()
