class newArray:
    def __init__(self, size):
        self.size = size
        self.data = [None] * size
        self.count = 0

    def __getitem__(self, index):
        if index < 0 or index >= self.count:
            raise IndexError("인덱스 벗어남")
        return self.data[index]
    
    def __setitem__(self, index, value):
        if index < 0 or index >= self.count:
            raise IndexError("인덱스 벗어남")
        self.data[index] = value
    
    def __len__(self):
        return self.count

    def append(self, value):
        if self.count >= self.size:
            raise OverflowError("오버플로우")
        self.data[self.count] = value
        self.count += 1
    
    def pop(self):
        if self.count == 0:
            raise IndexError("배열 비어있음")
        value = self.data[self.count - 1]
        self.data[self.count - 1] = None
        self.count -= 1
        return value
    
    def __repr__(self):
        return f"Array({self.data[:self.count]})"
    
# example
arr = newArray(5)
arr.append(10)
arr.append(20)
print(arr)

arr[1] = 100
print(arr)

print(len(arr))

arr.pop()
print(arr)

print(arr[3])