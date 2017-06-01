public class CalculGraphe {
    TypeRobot t;
    public float graphe[][];
    public int h;
    public int l;

    public CalculGraphe(Carte m, TypeRobot tr) {
        h = m.getNbLignes();
        l = m.getNbColonnes();
        graphe = new float[m.getNbLignes()*m.getNbColonnes()][m.getNbLignes()*m.getNbColonnes()];
        t=tr;
        for (int i=0;i<h;i++) {
            for (int j=0;j<l;j++) {
                for (Direction D : Direction.values()) {
                    if (m.voisinExiste(m.getCase(i,j),D)){
                        Case current = m.getCase(i,j);
                        Case voisin = m.voisinCase(current,D);
                        if (estAccessible(t,current) && estAccessible(t,voisin)) {
                            int a = i*h + j;
                            int b = voisin.getLigne()*h+voisin.getColonne();
                            graphe[a][b] = (cout(t,current) + cout(t,voisin))/2;
                            graphe[b][a] = (cout(t,current) + cout(t,voisin))/2;
                        }
                    }
                }
            }
        }
    }

    static public float cout(TypeRobot t, Case C) {
        switch (t) {
            case DRONE:
                break;
            case CHENILLES:
                if (C.getNature()==NatureTerrain.FORET) {
                    return 2;
                }
                break;
            case ROUES:
                break;
            case PATTES:
                if (C.getNature()==NatureTerrain.ROCHE) {
                    return 3;
                }
                break;
        } 
        return 1;
    }

    static public boolean estAccessible(TypeRobot t, Case P) {
        switch (t) {
            case DRONE:
                break;
            case CHENILLES:
                if (P.getNature() == NatureTerrain.EAU || P.getNature() == NatureTerrain.ROCHE){
                    return false;
                }
                break;
            case ROUES:
                if (P.getNature() == NatureTerrain.EAU || P.getNature() == NatureTerrain.FORET || P.getNature() == NatureTerrain.ROCHE){
                    return false;
                }
                break;
            case PATTES:
                if (P.getNature() == NatureTerrain.EAU ){
                    return false;
                }
                break;
        }
        return true;
    }
}
