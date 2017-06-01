import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.Simulable;
import gui.Text;

import java.util.*;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;


public class TestAStar {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            // Initialisation de l'interface
            DonneesSimulation data = new DonneesSimulation((args[0]));
            GUISimulator gui = new GUISimulator(data.getCarte().getNbLignes()*20 + 20, data.getCarte().getNbColonnes()*20 + 20, Color.BLACK);

            InitGraphe.init(data.getRobots(),data.getCarte());
            CalculChemin test = new CalculChemin(data.getRobots()[1],data.getIncendies().get(0).getPosIncendie(),0);


            // Initialisation des evenements
            SimulateurEvenement simuEvent = new SimulateurEvenement();
            data.getRobots()[1].setSimu(simuEvent);
            simuEvent.ajouteListeEvenement(test.getChemin());

            //TrouverEau.Eau(data.getRobots()[1],simuEvent.getDate());
            // Création du simulateur
            Simulateur Simulator = new Simulateur(gui, data, simuEvent);



        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

    }
}


class Simulateur implements Simulable {

    private GUISimulator gui;
    private DonneesSimulation sim;
    private SimulateurEvenement simEvent;

    public Simulateur(GUISimulator gui, DonneesSimulation simu, SimulateurEvenement simuEvent) {
        this.gui = gui;
        sim = simu;
        simEvent=simuEvent;
        gui.setSimulable(this);
        drawCarte(sim.getCarte());
        drawFires(sim.getIncendies());
        drawAllRobot(sim.getRobots());
    }

    @Override
    public void restart() {
        /*
        data = new DonneesSimulation((args[0]));
        SimulateurEvenement simuEvent = new SimulateurEvenement();
        */
    }

    @Override 
    public void next() {
        this.getSimuEvent().incrementeDate();
        gui.reset();
        drawCarte(sim.getCarte());
        drawFires(sim.getIncendies());
        drawAllRobot(sim.getRobots());
    }

    public SimulateurEvenement getSimuEvent() {
        return this.simEvent;
    }



    public void drawCarte( Carte map) {
        for ( int i = 0; i < map.getNbLignes(); i++) {
            for ( int j = 0; j < map.getNbColonnes(); j++) {
                drawCase(map.getCase(i,j));
            }
        }
    }


    public void drawCase(Case c) {
        Color couleur = Color.BLACK;
        switch ( c.getNature()) {
            case EAU:
                couleur = Color.decode("#0011FE");
                break;
            case FORET:
                couleur = Color.decode("#2B941A");
                break;
            case ROCHE:
                couleur = Color.decode("#713100");
                break;
            case TERRAIN_LIBRE:
                couleur = Color.decode("#E5B26F");
                break;
            case HABITAT:
                couleur = Color.decode("#837D77");
                break;
        }

        gui.addGraphicalElement(new Rectangle(10 + c.getColonne()*20,10+ c.getLigne()*20, couleur, couleur, 20));
    }


    private void drawAllRobot(Robot mesRobots[]) {
        for( Robot monRobot : mesRobots) {
            drawRobot(monRobot);
        }
    }

    public void drawRobot(Robot rob) {
        Case posRobot = rob.getPosition();
        switch( rob.getTypeRobot()) {
            case PATTES:
                gui.addGraphicalElement(new Rectangle(10 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20,  Color.WHITE, Color.BLACK, 6, 16));
                break;
            case ROUES:
                gui.addGraphicalElement(new Oval(10 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20, Color.WHITE, Color.BLACK, 15));
                break;
            case CHENILLES:
                gui.addGraphicalElement(new Rectangle( 6 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20, Color.WHITE, Color.BLACK, 8));
                gui.addGraphicalElement(new Rectangle( 14 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20, Color.WHITE, Color.BLACK, 8));
                break;
            case DRONE:
                gui.addGraphicalElement(new Oval( 10 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20, Color.WHITE, Color.BLACK, 14, 6));
                gui.addGraphicalElement(new Rectangle(10 + posRobot.getColonne()*20, 10 + posRobot.getLigne()*20, Color.WHITE, Color.BLACK, 14, 4));
                break;
        }
    }

    private void drawFires( ArrayList<Incendie> fires) {
        for ( Incendie feu : fires) {
            drawIncendie( feu);
        }
    }


    private void drawIncendie( Incendie fire) {
        gui.addGraphicalElement( new Rectangle( 10 + fire.getPosIncendie().getColonne() * 20, 10 + fire.getPosIncendie().getLigne() * 20, Color.decode("#FE0000"), Color.decode("#FE0000"), 14));
    }



}

