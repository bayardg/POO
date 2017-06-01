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

public class ChefPompierStupide {

    public static void SupprimerIncendie(ArrayList<Incendie> inc) {
        for (int i=inc.size()-1; i>-1; i--) {
            if (inc.get(i).getLitreFeu()==0) {
                inc.remove(i);
            }
        }
    }

    public static void InitSimu(Robot[] r, SimulateurEvenement simu) {
        for (int i =0; i<r.length; i++) {
            r[i].setSimu(simu);
        }

    }

    public static void EteindreFeu(Incendie inc,Robot[] r, SimulateurEvenement simu) {
        double coutmin= Double.POSITIVE_INFINITY;
        int j=-1;
        CalculChemin test;
        for (int i =0; i<r.length; i++) {
            if (!r[i].getBusy()) {
                test = new CalculChemin(r[i],inc.getPosIncendie(),simu.getDate());
                if (test.getCheminexiste() && test.getCout()<coutmin) {
                    coutmin=test.getCout();
                    j=i;
                }
            }
        }
        if (j>-1) {
            r[j].setBusy(true);
            CalculChemin afaire = new CalculChemin(r[j],inc.getPosIncendie(),simu.getDate());
            long date = afaire.getChemin().getLast().getDate();
            simu.ajouteListeEvenement(afaire.getChemin());
            inc.setEnCours(true);
            simu.ajouteEvenement(new Intervenir(date,r[j],inc));
            r[j].setBusy(true);
        }
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            // Initialisation de l'interface
            DonneesSimulation data = new DonneesSimulation((args[0]));
            GUISimulator gui = new GUISimulator(data.getCarte().getNbLignes()*30 + 30, data.getCarte().getNbColonnes()*30 + 30, Color.BLACK);

            InitGraphe.init(data.getRobots(),data.getCarte());

            // Initialisation des evenements
            SimulateurEvenement simuEvent = new SimulateurEvenement();
            InitSimu(data.getRobots(), simuEvent); 

            EteindreFeu(data.getIncendies().get(2),data.getRobots(), simuEvent);

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

    private int n;

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
        ;
    }

    @Override 
    public void next() {
        this.getSimuEvent().incrementeDate();
        ChefPompierStupide.SupprimerIncendie(sim.getIncendies());
        if (sim.getIncendies().size()>0) {
            Incendie ince = sim.getIncendies().get(n%sim.getIncendies().size());
            if (!ince.getEnCours()) {
                ChefPompierStupide.EteindreFeu(ince,sim.getRobots(), simEvent);
            }
        }
        gui.reset();
        drawCarte(sim.getCarte());
        drawFires(sim.getIncendies());
        drawAllRobot(sim.getRobots());
        n++;
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
        if ( c.getNature() == NatureTerrain.TERRAIN_LIBRE) {
            File gazon = new File("./bin/Routetut.png");
            String mon_gazon = gazon.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*30,  c.getLigne()*30, mon_gazon,30,30, null );
            gui.addGraphicalElement(img);
        } else if ( c.getNature() == NatureTerrain.HABITAT ){
            File maison = new File("./bin/maison.jpg");
            String ma_maison = maison.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*30,  c.getLigne()*30, ma_maison,30,30, null );
            gui.addGraphicalElement(img);

        } else if ( c.getNature() == NatureTerrain.EAU) {
            File eau = new File("./bin/eau.jpg");
            String ma_eau = eau.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*30,  c.getLigne()*30, ma_eau,30,30, null );
            gui.addGraphicalElement(img);

        } else if (c.getNature() == NatureTerrain.FORET) {
            File foret = new File("./bin/foret.jpg");
            String ma_foret = foret.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*30,  c.getLigne()*30, ma_foret,30,30, null );
            gui.addGraphicalElement(img);

        } else {
            File mont = new File("./bin/mont.jpg");
            String ma_mont = mont.getAbsolutePath();
            ImageElement img = new ImageElement( c.getColonne()*30,  c.getLigne()*30, ma_mont,30,30, null );
            gui.addGraphicalElement(img);

            

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
                File patte = new File("./bin/patte.png");
                String mon_patte = patte.getAbsolutePath();
                ImageElement img1 = new ImageElement( posRobot.getColonne()*30,  posRobot.getLigne()*30,
                        mon_patte,30,30, null );
                gui.addGraphicalElement(img1);
                break;
            case ROUES: 
                File roue = new File("./bin/roue.png");
                String mon_roue = roue.getAbsolutePath();
                ImageElement img2 = new ImageElement( posRobot.getColonne()*30,  posRobot.getLigne()*30,
                        mon_roue,30,30, null );
                gui.addGraphicalElement(img2);

                break;
            case CHENILLES:
                File chenille = new File("./bin/chenille.png");
                String mon_chenille = chenille.getAbsolutePath();
                ImageElement img3 = new ImageElement( posRobot.getColonne()*30,  posRobot.getLigne()*30,
                        mon_chenille,30,30, null );
                gui.addGraphicalElement(img3);
                break;
            case DRONE:
                File drone = new File("./bin/drone.png");
                String mon_drone = drone.getAbsolutePath();
                ImageElement img4 = new ImageElement( posRobot.getColonne()*30,  posRobot.getLigne()*30,
                        mon_drone,30,30, null );
                gui.addGraphicalElement(img4);
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
        ImageElement img = new ImageElement( fire.getPosIncendie().getColonne()*30,  fire.getPosIncendie().getLigne()*30, mon_feu,30,30, null );
        gui.addGraphicalElement(img);
    }

}

