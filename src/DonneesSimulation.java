import java.util.*;
import java.util.zip.DataFormatException;
import java.io.FileNotFoundException;

public class DonneesSimulation {
    private Carte carte;
    private ArrayList<Incendie> incendies;
    private Robot robots[];

    public DonneesSimulation(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        carte = lecteur.creerCarte();
        incendies = lecteur.creerIncendies(carte);
        robots = lecteur.creerRobots(carte);
        lecteur.fermer();
    }

    public Carte getCarte() {
        return this.carte;
    }

    public ArrayList<Incendie> getIncendies() {
        return this.incendies;
    }

    public Robot[] getRobots() {
        return this.robots;
    }

}
