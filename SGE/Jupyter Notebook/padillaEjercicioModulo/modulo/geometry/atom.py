class atom:
    def __init__(self, name, x, y,z):
        self.name = name
        self.x = x
        self.y = y
        self.z = z
    
    def dist(atom2):
        import math
        totalX = (self.x - atom2.x) ** 2
        totalY = (self.y - atom2.y) ** 2
        totalZ = (self.z - atom2.z) ** 2
        dist = math.sqrt(totalX + totalY + totalZ)
        return dist