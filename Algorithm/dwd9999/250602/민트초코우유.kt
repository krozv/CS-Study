import java.io.BufferedReader
import java.util.PriorityQueue

fun 민트초코우유() {
    val br: BufferedReader = System.`in`.bufferedReader()
    fun getIntList(): List<Int> = br.readLine().split(" ").map { it.toInt() }

    val (temp, turn) = getIntList()
    size = temp

    total = IntArray(1 shl 3)

    likes = Array(size) {
        val list: MutableList<Int> = mutableListOf()
        val line: String = br.readLine()
        for (food in line) {
            list.add(
                when (food) {
                    'T' -> 1 shl 0
                    'C' -> 1 shl 1
                    'M' -> 1 shl 2
                    else -> 3
                }
            )
        }
        list.toIntArray()
    }

    studentsFaith = Array(size) { l ->
        val list = getIntList()
        for (i in list.indices) {
            total[likes[l][i]] += list[i]
        }
        list.toIntArray()
    }


    val answer: StringBuilder = StringBuilder()
    for (day in 1..turn) {
        findingGroups()

        spread()

        for (idx in answerIdx) {
            answer.append(total[idx]).append(" ")
        }
        answer.append("\n")
    }

    println(answer)
}

fun findingGroups() {
    groupQueue = PriorityQueue()

    val visited: Array<BooleanArray> = Array(size) { BooleanArray(size) }

    var food: Int
    var memberCount: Int
    var dir: Dir
    var nextDir: Dir
    var leader: Leader
    var bfsQueue: ArrayDeque<Dir>
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (!visited.check(i, j)) {
                memberCount = 0
                leader = Leader(Dir(i, j), studentsFaith[i][j])
                food = likes[i][j]

                bfsQueue = ArrayDeque()
                bfsQueue.add(Dir(i, j))
                while (!bfsQueue.isEmpty()) {
                    dir = bfsQueue.removeFirst()
                    if (!visited.check(dir) && food == likes.get(dir)) {
                        memberCount++
                        visited[dir.x][dir.y] = true

                        for (move in moveIdx) {
                            nextDir = Dir(dir.x + move[0], dir.y + move[1])
                            if (!isOutOfBounds(nextDir) && !visited.check(nextDir) && food == likes.get(nextDir)) {
                                bfsQueue.add(nextDir)
                                if (leader.faith < studentsFaith.get(nextDir) || (leader.faith == studentsFaith.get(nextDir) && leader.dir > nextDir)) {
                                    leader = Leader(nextDir, studentsFaith.get(nextDir))
                                }
                            }
                        }
                    }
                }
                studentsFaith[leader.dir.x][leader.dir.y] += memberCount
                total[food] += memberCount
                leader = Leader(leader.dir, leader.faith + memberCount)
                groupQueue.add(Group(food, leader))
            }
        }
    }
}

fun spread() {
    val visited: Array<BooleanArray> = Array(size) { BooleanArray(size) }

    while (!groupQueue.isEmpty()) {
        val group: Group = groupQueue.poll()
        if (visited.check(group.leader.dir)) {
            continue
        }

        val dir: List<Int> = moveIdx[group.leader.faith % 4]
        var x: Int = group.leader.dir.x + dir[0]
        var y: Int = group.leader.dir.y + dir[1]

        var power: Int = group.leader.faith - 1
        studentsFaith[group.leader.dir.x][group.leader.dir.y] -= power
        total[group.food] -= power

        while (!isOutOfBounds(x, y) && power > 0) {
            if (group.food != likes[x][y]) {
                visited[x][y] = true
                if (power > studentsFaith[x][y]) {
                    total[likes[x][y]] -= studentsFaith[x][y]
                    studentsFaith[x][y] += 1
                    total[group.food] += studentsFaith[x][y]
                    likes[x][y] = group.food
                    power -= studentsFaith[x][y]
                } else {
                    val newFood: Int = likes[x][y] or group.food
                    total[likes[x][y]] -= studentsFaith[x][y]
                    studentsFaith[x][y] += power
                    total[newFood] += studentsFaith[x][y]
                    likes[x][y] = newFood
                    power = 0
                }
            }

            x += dir[0]
            y += dir[1]
        }

    }
}


var size: Int = 0

lateinit var total: IntArray
lateinit var likes: Array<IntArray>
lateinit var studentsFaith: Array<IntArray>
lateinit var groupQueue: PriorityQueue<Group>

val priority: MutableList<Int> = mutableListOf(0, 1, 1, 2, 1, 2, 2, 3)
val answerIdx: List<Int> = listOf(7, 3, 5, 6, 4, 2, 1)
val moveIdx: List<List<Int>> = listOf(listOf(-1, 0), listOf(1, 0), listOf(0, -1), listOf(0, 1))

fun Array<IntArray>.get(dir: Dir): Int = this[dir.x][dir.y]

fun isOutOfBounds(x: Int, y: Int): Boolean = x < 0 || x >= size || y < 0 || y >= size
fun isOutOfBounds(dir: Dir): Boolean = dir.x < 0 || dir.x >= size || dir.y < 0 || dir.y >= size
fun Array<BooleanArray>.check(x: Int, y: Int): Boolean = this[x][y]
fun Array<BooleanArray>.check(dir: Dir): Boolean = this[dir.x][dir.y]

fun isBigger(dir1: Dir, dir2: Dir): Boolean {
    return if (dir1.x != dir2.x) {
        dir1.x < dir2.x
    } else {
        dir1.y < dir2.y
    }
}

data class Group(val food: Int, val leader: Leader) : Comparable<Group> {
    override fun compareTo(other: Group): Int {
        if (priority[this.food] != priority[other.food]) {
            return priority[this.food] - priority[other.food]
        } else {
            return this.leader.compareTo(other.leader)
        }
    }
}

data class Leader(val dir: Dir = Dir(0, 0), val faith: Int = 0) : Comparable<Leader> {
    override fun compareTo(other: Leader): Int {
        if (this.faith != other.faith) {
            return other.faith - this.faith
        } else {
            return this.dir.compareTo(other.dir)
        }
    }
}

data class Dir(val x: Int, val y: Int) : Comparable<Dir> {
    override fun compareTo(other: Dir): Int {
        if (this.x != other.x) {
            return this.x - other.x
        } else {
            return this.y - other.y
        }
    }
}
