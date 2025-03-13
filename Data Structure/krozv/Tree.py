class Tree:
    def __init__(self, value):
        self.value = value
        self.children = []

    def add(self, child_node):
        self.children.append(child_node)

    def print_tree(self, level=0):
        print(" " * level + str(self.value))
        for child in self.children:
            child.print_tree(level+1)

# example
root = Tree('a')
node_b = Tree('b')
node_c = Tree('c')
node_d = Tree('d')

root.add(node_b)
node_b.add(node_c)
node_b.add(node_d)

root.print_tree()
node_b.print_tree()