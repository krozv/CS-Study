# 25598. Alive or Dead?
"""
N * N 격자
시작위치: (py, px)
명령어: U, D, L, R, S
벽의 위치: (wy, wx)
좀비 위치: (zy, zx)
- 상급 좀비, 하급 좀비
하급 좀비
- 현재 바라보고 있는 방향으로 한 칸씩 l번 이동
- 막혔을 경우, 정지 후 반대로 방향을 틈
상급 좀비
- 현재 바라보고 있는 방향으노 한 칸씩 h번 이동
- 막혔을 경우, 정지
- 벽 일 경우, 벽을 부셔
- 벽 부시고 정지.
- 상하좌우 중 벽이 가장 많은 방향으로 방향을 튼다.
- 벽의 개수 동일한 경우 -> 상, 우, 하, 좌 우선순위
- 벽의 개수는 해당 라인 총 개수
좀비는 생성(입력)된 순서로 이동함
한 칸에 여러 좀비 존재 가능

END CONDITION
- 플레이와 좀비가 같은 칸에 있는 경우 -> DIE, 게임 종료
- 플레이어와 좀비가 같은 칸에 있지 않는 경우 -> 다음 날
- 좀비가 이동 중에 플레이어를 만나도 플레이어 안 죽임

D일차의 생존 여부??
"""
import sys
# sys.stdin = open('input.txt', 'rt', encoding='UTF8')
input = sys.stdin.readline
debug = False

dir = {
    'U': (0, -1),
    'D': (0, 1),
    'L': (-1, 0),
    'R': (1, 0),
    'S': (0, 0)
}

class Player:
    def __init__(self, location):
        self.location = location #[px, py]
        self.alive = True

    # 플레이어 명령어대로 이동
    def move(self, command, field):
        N = len(field[0]) # n같이 줄까 말까 고민중
        dx, dy = dir[command]
        x, y = self.location[0] + dx, self.location[1] + dy
        # 벽이 아니거나 범위 내일 경우 이동
        if 0 <= x < N and 0 <= y < N and field[x][y] == 0:
            self.location[0], self.location[1] = x, y
        return

class Zombie:
    def __init__(self, idx, location, type, direction, velocity):
        self.idx = idx
        self.location = location # [zx, zy]
        self.type = type # 0: 하급, 1: 상급
        self.direction = direction # 방향, U, D, L, R
        self.velocity = velocity

    # 좀비 이동 + 벽 뿌시기
    def move(self, field):
        print_debug(f'{self.location}에서 좀비가 {self.direction}방향으로 이동한다!')
        # 하급좀비일 경우
        if self.type == 0:
            self.move_low_zombie(field)
        # 상급좀비일 경우
        else:
            self.move_high_zombie(field)
        return

    # 하급 좀비 이동
    def move_low_zombie(self, field):
        N = len(field[0])
        
        # 속도 감안해야함. while 사용?
        dx, dy = dir[self.direction]
        cnt = 0

        while cnt < self.velocity:
            x, y = self.location[0] + dx, self.location[1] + dy
            
            if 0<= x < N and 0 <= y < N and field[x][y] == 0:
                # 이동
                self.location[0], self.location[1] = x, y
                cnt += 1
            else:
                # 방향바꿈
                if self.direction == 'U': self.direction = 'D'
                elif self.direction == 'D': self.direction = 'U'
                elif self.direction == 'L': self.direction = 'R'
                else: self.direction = 'L'
                break
        return

    # 상급 좀비 이동
    def move_high_zombie(self, field):
        N = len(field[0])

        dx, dy = dir[self.direction]
        cnt = 0

        ### 0<=h<=10**8 이므로 최적화해야함. ###
        while cnt < self.velocity:
            x, y = self.location[0] + dx, self.location[1] + dy
            
            if 0<= x < N and 0 <= y < N and field[x][y] == 0:
                # 이동
                self.location[0], self.location[1] = x, y
                cnt += 1
            else:
                # 벽이면 뿌셔
                if 0 <= x < N and 0 <= y < N and field[x][y]:
                    field[x][y] = 0
                    print_debug(f"{self}가 벽 {x}, {y}를 뚫어버렸다.")
                # 방향 바꿔
                next_dir = self.find_direction(N, field)
                self.direction = next_dir
                break
        if cnt == self.velocity:
            next_dir = self.find_direction(N, field)
            self.direction = next_dir
        return
    
    # 상급 좀비 이동 방향 찾기
    def find_direction(self, N, field):
        """
        location: 좀비 위치
        return: 좀비가 이동할 방향 dir
        """
        x, y = self.location[0], self.location[1]
        
        priority = {'U': 3, 'R': 2, 'D': 1, 'L': 0} # 방향 우선순위
        dict = {'U': 0, 'R': 0, 'D': 0, 'L': 0}

        # x 고정, y 값으로 찾기
        for j in range(N):
            # 벽일 경우
            if field[x][j]:
                if j < y:
                    dict['U'] += 1
                if j > y:
                    dict['D'] += 1
        # y 고정, x 값으로 찾기
        for i in range(N):
            # 벽일 경우
            if field[i][y]:
                if i < x:
                    dict['L'] += 1
                if i > x:
                    dict['R'] += 1

        sorted_dict = sorted(dict.items(), key=lambda item: (-item[1], -priority[item[0]]))
        print_debug(sorted_dict)
        return sorted_dict[0][0]
    

    def __repr__(self):
        return f"{self.idx}번째 {"상급" if self.type == 1 else "하급"}좀비"

def print_debug(text):
    if debug == True:
        print(text)

def solution():
    N = int(input()) # 게임 필드의 크기 N
    field = [[0] * N for _ in range(N)]
    
    O = list(input()) # 명령어 O
    
    py, px = map(int, input().split())
    player = Player([px-1, py-1])

    W = int(input()) # 벽의 개수
    for _ in range(W):
        wy, wx = map(int, input().split())
        field[wx-1][wy-1] = 1
    
    Z = int(input()) # 좀비의 개수
    z_dict = {} # 좀비 담을 딕셔너리
    
    for idx in range(Z):
        zy, zx, ztype, zdir, zvel = input().split()
        zombie = Zombie(idx, [int(zx)-1, int(zy)-1], int(ztype), zdir, int(zvel))
        z_dict[idx] = zombie

    D = int(input()) # 영재가 알고 싶어하는 게임의 일차
    print_debug(z_dict)
    
    date = 1
    while (player.alive and date <= D):
        print_debug(f"------------{date}일차----------")
        # 플레이어 이동
        player.move(O[date-1], field)
        # 좀비 이동
        for zombie in z_dict.values():
            print_debug(zombie)
            zombie.move(field)

        # 생존 확인
        for zombie in z_dict.values():
            if player.location == zombie.location:
                player.alive = False
                break
        # 날짜 증가
        if player.alive: date += 1
    
    return "ALIVE!" if player.alive else f"{date}\nDEAD..."

if __name__ == "__main__":
    print(solution())
