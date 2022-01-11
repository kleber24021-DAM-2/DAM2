from modulo.geometry.atom import atom
from modulo.geometry.step import step

class dinamic:
    def __init__(self):
        self.stepArray = []
        
    def loadStep(self, filePath):
        text = open(filePath).readlines()
        totalAtoms = int(text[0])
        atomArray = []
        for i in range(1, totalAtoms+1):
            line = text[i]
            line = line.rstrip()
            line = line.split()
            atomArray.append(atom(line[0], float(line[1]), float(line[2]), float(line[3])))
            
        self.stepArray.append(step(atomArray))
    
    def printAllAtoms(self):
        for step in range(len(self.stepArray)):
            print("Step número " + str(step))
            for atom in self.stepArray[step].atomArray:
                print(atom.name + " " + str(atom.x) + " " + str(atom.y) + " " + str(atom.z))