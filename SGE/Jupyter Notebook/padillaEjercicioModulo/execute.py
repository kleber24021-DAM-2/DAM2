import sys
import os
from modulo.geometry.dinamic import *

if sys.argv[0] == "help"
    print("Para cargar un archivo: -")

din = dinamic()
din.loadStep("C60.xyz")
din.printAllAtoms()
print(str(din.stepArray[0].atomArray[0].dist(din.stepArray[0].atomArray[1])))
