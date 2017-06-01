import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.Simulable;
import gui.ImageElement;
import gui.Text;

import java.util.*;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;


public class TestSimulateur {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            // Initialisation de l'interface
            DonneesSimulation data = new DonneesSimulation((args[0]));
            GUISimulator gui = new GUISimulator(data.getCarte().getNbLignes()*20 + 20, data.getCarte().getNbColonnes()*20 + 20, Color.BLACK);

            // Initialisation des evenements
            SimulateurEvenement simuEvent = new SimulateurEvenement();
            // ajout d'un evenement robot 1 qui marche au nord
            Deplacement dep4 = new Deplacement(data.getRobots()[0], Direction.NORD, 1000);
            Deplacement dep3 = new Deplacement(data.getRobots()[0], Direction.NORD, 2000);
            Deplacement dep2 = new Deplacement(data.getRobots()[0], Direction.NORD, 3000);
            Deplacement dep1 = new Deplacement(data.getRobots()[0], Direction.NORD, 4000);
            simuEvent.ajouteEvenement(dep1);
            simuEvent.ajouteEvenement(dep4);
            simuEvent.ajouteEvenement(dep3);
            simuEvent.ajouteEvenement(dep2);

            // Création du simulateur
            Simulateur Simulator = new Simulateur(gui, data, simuEvent);
            //simuEvent.incrementeDate();
            //   System.out.println(dep.getRob().getPosition().getLigne());
            //   System.out.println(dep.getRob().getPosition().getColonne());





        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

    }
}


class Simulateur implements Simulable {

    private GUISimulator gui;
    private DonneesSimulation simu;
    private SimulateurEvenement simuEvent;

    public Simulateur(GUISimulator gui, DonneesSimulation sim, SimulateurEvenement simuEven) {
        this.gui = gui;
        gui.setSimulable(this);
        simu = sim;
        simuEvent = simuEven;
        drawCarte(simu.getCarte());
        drawFires(simu.getIncendies());
        drawAllRobot(simu.getRobots());
    }


    @Override
    public void restart() {
        ;
    }

    @Override 
    public void next() {
        this.getSimuEvent().incrementeDate();
        gui.reset();
        System.out.println(simu.getRobots()[0].getPosition().getLigne());
        System.out.println(simu.getRobots()[0].getPosition().getColonne());
        drawCarte(simu.getCarte());
        drawFires(simu.getIncendies());
        drawAllRobot(simu.getRobots());

    }

    public SimulateurEvenement getSimuEvent() {
        return this.simuEvent;
    }



    public void drawCarte( Carte map) {
        for ( int i = 0; i < map.getNbLignes(); i++) {
            for ( int j = 0; j < map.getNbColonnes(); j++) {
                drawCase(map.getCase(i,j));
            }
        }
    }


    public void drawCase(Case c) {
        if ( c.getNature() == NatureTerrain.TERRAIN_LIBRE) {
            File gazon = new File("./bin/Routetut.png");
            String mon_gazon = gazon.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*20,  c.getLigne()*20, mon_gazon,20,20, null );
            gui.addGraphicalElement(img);
        } else if ( c.getNature() == NatureTerrain.HABITAT ){
            File maison = new File("./bin/maison.png");
            String ma_maison = maison.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*20,  c.getLigne()*20, ma_maison,20,20, null );
            gui.addGraphicalElement(img);

        } else {
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
                    /*case TERRAIN_LIBRE:
                      couleur = Color.decode("#E5B26F");
                      break;
                      case HABITAT:
                      couleur = Color.decode("#837D77");
                      break;*/
            }

            gui.addGraphicalElement(new Rectangle(10 + c.getColonne()*20,10+ c.getLigne()*20, couleur, couleur, 20));
        }
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
                File drone = new File("./bin/drone.png");
                String mon_drone = drone.getAbsolutePath();
                ImageElement img = new ImageElement( posRobot.getColonne()*20,  posRobot.getLigne()*20, mon_drone,20,20, null );
                gui.addGraphicalElement(img);

                break;
        }
    }

    private void drawFires( ArrayList<Incendie> fires) {
        for ( Incendie feu : fires) {
            drawIncendie( feu);
        }
    }


    private void drawIncendie( Incendie fire) {
        File feu = new File("./bin/feu.png");
        String mon_feu = feu.getAbsolutePath();
        ImageElement img = new ImageElement( fire.getPosIncendie().getColonne()*20,  fire.getPosIncendie().getLigne()*20, mon_feu,20,20, null );
        gui.addGraphicalElement(img);

        //gui.addGraphicalElement( new Rectangle( 10 + fire.getPosIncendie().getColonne() * 20, 10 + fire.getPosIncendie().getLigne() * 20, Color.decode("#FE0000"), Color.decode("#FE0000"), 14));
    }



}

