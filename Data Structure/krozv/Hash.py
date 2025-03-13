class HashTable:
    def __init__(self, size=10):
        self.size = size
        self.table = [[] for _ in range(size)]  # 체이닝을 위한 리스트 사용

    def _hash(self, key):
        return sum(ord(char) for char in str(key)) % self.size

    # 삽입
    def insert(self, key, value):
        index = self._hash(key)
        for pair in self.table[index]:

            if pair[0] == key:
                pair[1] = value
                return
        self.table[index].append([key, value])  # 새 키 추가

    def search(self, key):
        index = self._hash(key)
        for pair in self.table[index]:
            if pair[0] == key:
                return pair[1]  # 값 반환
        return None  # 찾지 못하면 None 반환

    def delete(self, key):
        index = self._hash(key)
        for i, pair in enumerate(self.table[index]):
            if pair[0] == key:
                del self.table[index][i]
                return True
        return False  # 삭제할 키가 없으면 False 반환

    def display(self):
        for i, bucket in enumerate(self.table):
            print(f"{i}: {bucket}")


# example
hash_table = HashTable()

# 데이터 삽입
hash_table.insert("a", 100)
hash_table.insert("b", 200)
hash_table.insert("c", 300)
hash_table.insert("d", 400)
