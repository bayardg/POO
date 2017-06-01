import java.util.*;

public class TrouverEau {
    public static void Eau(Robot Walle,long date) {
        LinkedList<Evenement> li = new LinkedList<Evenement>();
        Carte map = Walle.getCarte();
        int h = map.getNbLignes();
        int l = map.getNbColonnes();
        CalculChemin ch;
        for (int i = 0; i <h; i++) {
            for(int j = 0; j<l; j++) {
                if (TrouverEau.PouvoirRecharger(map.getCase(i,j),Walle)) {
                    ch = new CalculChemin(Walle,map.getCase(i,j),date);
                    if (ch.getCheminexiste()) {
                        li.addAll(ch.getChemin());
                        long d = li.getLast().getDate();
                        li.add(new Recharger(Walle,d));
                        Walle.addEvents(li);
                        return;
                    }
                }
            }
        }
        //Normalement impossible
    }

    private static boolean PouvoirRecharger(Case P,Robot Walle) {
        switch (Walle.getTypeRobot()) {
            case DRONE:
                return P.getNature()==NatureTerrain.EAU;
            default:
                for (Direction dir : Direction.values()) {
                    if (Walle.getCarte().voisinExiste(P,dir) && Walle.getCarte().voisinCase(P,dir).getNature()==NatureTerrain.EAU) {
                        return true;
                    }
                }
        }
        return false;
    }
}
