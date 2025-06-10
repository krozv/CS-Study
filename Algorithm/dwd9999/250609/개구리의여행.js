const fs = require('fs');
const input = fs.readFileSync('/dev/stdin').toString().trim().split('\n');

const isOutOfRange = (x, y) => x < 0 || x >= MAP_SIZE || y < 0 || y >= MAP_SIZE;

const MAX_POWER = 5;
const MOVE = [[0, 1], [1, 0], [0, -1], [-1, 0]]

class Edge {
    constructor(x, y, power, weight) {
        this.x = x;
        this.y = y;
        this.power = power;
        this.weight = weight;
    }
}

// 입력 받기
const MAP_SIZE = parseInt(input.shift());
const map = input.splice(0, MAP_SIZE).map(row => row.split(''));
const queryCount = parseInt(input.shift());
const query = input.map(row => row.split(' '));

const graph =
    Array(MAP_SIZE).fill(null).map(() =>
        Array(MAP_SIZE).fill(null).map(() =>
            Array(MAX_POWER + 1).fill(null).map(() => [])));

// 가능한 그래프 간선 추가
for (let x = 0; x < MAP_SIZE; x++) {
    for (let y = 0; y < MAP_SIZE; y++) {
        if (map[x][y] === '.') {
            // 점프하기
            for (let power = 1; power <= MAX_POWER; power++) {
                for (let dir = 0; dir < 4; dir++) {
                    const nextX = x + MOVE[dir][0] * power;
                    const nextY = y + MOVE[dir][1] * power;
                    if (!isOutOfRange(nextX, nextY) && map[nextX][nextY] === '.') {
                        let tempX = x;
                        let tempY = y;
                        let isSafe = true;
                        for (let i = 1; i < power; i++) {
                            tempX += MOVE[dir][0];
                            tempY += MOVE[dir][1];
                            if (map[tempX][tempY] === '#') {
                                isSafe = false;
                                break;
                            }
                        }
                        if (isSafe) {
                            graph[x][y][power].push(new Edge(nextX, nextY, power, 1));
                        }
                    }
                }

                // 점프력 상승
                if (power < MAX_POWER) {
                    graph[x][y][power].push(new Edge(x, y, power + 1, (power + 1) ** 2))
                }

                // 점프력 감소
                if (power > 1) {
                    for (let i = power - 1; i > 0; i--) {
                        graph[x][y][power].push(new Edge(x, y, i, 1));
                    }
                }
            }
        }
    }
}

// Dial's Algorithm 사용 (해당 노드까지 가는 거리가 N 이라면 buckets[N]에 저장)
let answer = "";
const MAX_BUCKET = MAP_SIZE * MAP_SIZE * 15;

for (let i = 0; i < queryCount; i++) {
    const [startX, startY, endX, endY] = query[i].map((num) => parseInt(num) - 1);

    const distance = Array(MAP_SIZE).fill(null).map(() =>
        Array(MAP_SIZE).fill(null).map(() =>
            Array(MAX_POWER + 1).fill(null).map(() => Infinity)));
    const buckets = Array(MAX_BUCKET + 1).fill(null).map(() => []);

    distance[startX][startY][1] = 0;
    buckets[0].push(new Edge(startX, startY, 1, 0));

    // 현재 거리
    let idx = 0;

    // 현재 거리가 가능한 최대 거리보다 작고, 목적지 까지의 최단거리보다 작으면 계속 진행
    while (idx <= MAX_BUCKET && idx < distance[endX][endY][MAX_POWER]) {

        // 버킷이 비어있으면 패스
        if (buckets[idx].length === 0) {
            idx++;
            continue;
        }

        const now = buckets[idx].shift();

        // 현재 노드의 최단거리보다 가중치가 커져버렸으면 패스
        if (distance[now.x][now.y][now.power] < now.weight) {
            continue;
        }

        // 현재 노드에서 갈 수 있는 노드를 순회하며 버킷에 추가
        for (const edge of graph[now.x][now.y][now.power]) {
            const newDist = distance[now.x][now.y][now.power] + edge.weight;
            if (distance[edge.x][edge.y][edge.power] > newDist) {
                distance[edge.x][edge.y][edge.power] = newDist;
                buckets[newDist].push(new Edge(edge.x, edge.y, edge.power, newDist));
            }
        }
    }

    const min = Math.min(...distance[endX][endY]);
    let result = (min === Infinity ? -1 : min);
    answer += result + "\n";
}

console.log(answer.trim())