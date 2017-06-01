# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture testDonnees testSimulateur testAStar testEvent test

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

testDonnees:
	javac -d bin -sourcepath src src/TestDonneesSimulation.java

#testAStarFonctionnel:
#	javac -d bin -sourcepath src src/TestAStarFonctionnel.java

testSimulateur:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestSimulateur.java

testAStar:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestAStar.java

testEvent:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestEvenements.java
test:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/ChefPompierStupide.java


# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:bin/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map


exeDonnees: 
	java -classpath bin TestDonneesSimulation cartes/carteSujet.map

#exeAS: 
#	java -classpath bin TestAStarFonctionnel cartes/carteSujet.map

exeSimulateur:
	java -classpath bin:bin/gui.jar TestSimulateur cartes/carteSujet.map

exeAStar: 
	java -classpath bin:bin/gui.jar TestAStar cartes/carteSujet.map
exeEvent:
	java -classpath bin:bin/gui.jar TestEvenements cartes/carteSujet.map


exeMap1: 
	java -classpath bin:bin/gui.jar ChefPompierStupide cartes/carteSujet.map

exeMap2:
	java -classpath bin:bin/gui.jar ChefPompierStupide cartes/desertOfDeath-20x20.map

exeMap3: 
	java -classpath bin:bin/gui.jar ChefPompierStupide cartes/mushroomOfHell-20x20.map

exeMap4:
	java -classpath bin:bin/gui.jar ChefPompierStupide cartes/spiralOfMadness-50x50.map

clean:
	rm -rf bin/*.class
	rm -rf src/*.class
