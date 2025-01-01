// 13903. 출근
// const input = require("fs").readFileSync("./input.txt").toString().split("\n");
const input = require("fs").readFileSync("/dev/stdin").toString().split("\n");
class Deque {
    constructor() {
        this.queue = [];
        this.front = 0;
        this.rear = 0;
    }

    push(element) {
        this.queue[this.rear++] = element;
    }

    shift() {
        if (this.isEmpty()) return undefined;
        return this.queue[this.front++];
    }

    isEmpty() {
        return this.front === this.rear;
    }

    length() {
        return this.rear - this.front;
    }
}

function solution() {
    // input
    const [r, c] = input[0].split(" ").map(Number);
    const arr = input.slice(1, r+1).map((row) => row.split(" ").map(Number))
    const delta = input.slice(r+2).map((row) => row.split(" ").map(Number))

    // bfs
    let minTime = Infinity;

    for (let j = 0; j < c; j++){
        if (arr[0][j] === 1) {
            const time = bfs([0, j], r, c, delta, arr, minTime);
            if (time !== -1)
                minTime = Math.min(minTime, time);
        }
    }
    console.log(minTime === Infinity ? -1 : minTime);
}

function bfs(start, r, c, delta, arr, minTime) {
    let visited = new Set();
    let queue = new Deque();
    queue.push([...start, 0])

    while (!queue.isEmpty()) {
        const [x, y, time] = queue.shift();

        if (x === r-1) return time;

        const key = `${x}:${y}`;
        if (visited.has(key)) continue;
        visited.add(key);

        if (time >= minTime) continue;

        for (const d of delta){
            const [nx, ny] = [x + d[0], y + d[1]];

            if (0 <= nx && nx < r && 0 <= ny && ny < c && arr[nx][ny] === 1) {
                queue.push([nx, ny, time + 1]);
            }
        }
    }
    return -1;
}

solution();
module.exports = solution;