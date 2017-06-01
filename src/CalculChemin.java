import java.util.*;

public class CalculChemin {
    private Robot Walle;
    private boolean cheminexiste;
    private double cout;
    private LinkedList<Evenement> chemin;

    public String toString(){
        String s = new String();
        Iterator i = chemin.iterator();
        while (i.hasNext()) {
            s += ((Deplacement) i.next()).direction.toString();
        }
        return s;
    }

    public double getCout() {
        return this.cout;
    }

    public boolean getCheminexiste() {
        return this.cheminexiste;
    }

    public LinkedList<Evenement> getChemin() {
        return this.chemin;
    }

    public CalculChemin() {
        ;
    }


    public CalculChemin(Robot r, Case arrivee, long dateinit) {
        Walle = r;
        AStar res = new AStar(Walle.getPosition(),arrivee,Walle.getGraphe());
        chemin = new LinkedList<Evenement>();
        cheminexiste=res.getExiste();
        if (cheminexiste) {
            cout=3.6*res.getCout()*r.getCarte().getTailleCases()/Walle.getVitesse(NatureTerrain.TERRAIN_LIBRE);
            LinkedList<Noeud> ListeNoeud = res.getChemin();
            if (ListeNoeud.size()>0) {
                Noeud current = ListeNoeud.remove();
                Noeud next;
                while (ListeNoeud.size()>0) {
                    next = ListeNoeud.remove();
                    Direction dir = this.CalculDir(current,next);
                    chemin.addLast(new Deplacement(Walle,dir,dateinit+(long) (3.6 *next.getG()*r.getCarte().getTailleCases()/Walle.getVitesse(NatureTerrain.TERRAIN_LIBRE))));
                    current = next;
                }
            }
        }
    }

    private Direction CalculDir(Noeud A, Noeud B) {
        for (Direction dir : Direction.values()) {
            //Ne pas essayer de lire la condition ci-dessous
            if (this.Walle.getCarte().voisinExiste(this.Walle.getCarte().getCase(A.getX(),A.getY()),dir) && (this.Walle.getCarte().voisinCase(this.Walle.getCarte().getCase(A.getX(),A.getY()),dir) == this.Walle.getCarte().getCase(B.getX(),B.getY()))) {
                return dir;
            }
        }
        //Impossible a atteindre
        return Direction.NORD;
    }
}
