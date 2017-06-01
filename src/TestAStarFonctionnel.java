import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.*;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;

import java.util.Arrays;


public class TestAStarFonctionnel {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            // Initialisation de l'interface
            DonneesSimulation data = new DonneesSimulation((args[0]));
            Robot r = data.getRobots()[1];
            CalculGraphe graphe = new CalculGraphe(data.getCarte(),data.getRobots()[1].getTypeRobot());

            CalculChemin test = new CalculChemin(r,data.getIncendies().get(0).getPosIncendie(),graphe,0);

            // Initialisation des evenements
            SimulateurEvenement simuEvent = new SimulateurEvenement();
            simuEvent.ajouteListeEvenement(test.getChemin());

            while (!simuEvent.simulationTerminee()) {
                simuEvent.incrementeDate();
            }

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

    }
}
