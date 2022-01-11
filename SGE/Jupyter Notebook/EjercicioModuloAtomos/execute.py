import sys
import os
from modulo.geometry.dinamic import *

din = dinamic()

def printHelp():
    print("Utilice el parámetro -i y la ruta hasta el archivo que contenga el paso \n")
    print("Para hallar la distancia entre dos átomos utilice el parámetro -d seguido de la posición de los dos átomos\n")
    print("Ejemplo: execute.py -i C60.xyz -d 5 42")
    
def printDistance(atom1, atom2):
    print(din.stepArray[0].atomArray[atom1].dist(din.stepArray[0].atomArray[atom2]))

def loadFile(pathToFile):
    din.loadStep(pathToFile)
                
if("-help" in sys.argv or len(sys.argv) == 0):
    printHelp()
elif("-i" in sys.argv and "-d" in sys.argv):
    indexD = sys.argv.index("-d")
    indexI = sys.argv.index("-i")
    loadFile(sys.argv[indexI+1])
    print("Distancia entre los átomos " + sys.argv[indexD+1] + " " +sys.argv[indexD+2] )
    printDistance(int(sys.argv[indexD+1]), int(sys.argv[indexD+2]))
else:
    print("Parámetros incorrectos. Introduzca help para ver la ayuda")