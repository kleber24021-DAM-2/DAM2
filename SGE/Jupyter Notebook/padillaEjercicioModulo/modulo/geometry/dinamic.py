class dinamic:
    def __init__(self):
        self.stepArray = []
    
    def loadStep(self, filePath):
        limport atom
        text = open(filePath).readlines()
        totalAtoms = int(text[0])
        atomArray = []
        for i in range(1, totalAtoms+1):
            line = text[i].split()
            atomArray.append(atom(text[0], int(text[1]), int(text[2]), int([3])))
            
        stepArray.append(step(atomArray))
    
    def printAllAtoms(self):
        import atom
        import step
        for step in self.stepArray:
            for atom in step:
                print(atom.name + " " + atom.x + " " + atom.y + " " + atom.z)
