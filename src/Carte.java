public class Carte {
    private Case map[][];
    private int tailleCases;
    private int hauteur;
    private int largeur;

    public Carte(int ligne, int colonne, int tailleC ) {
        map = new Case[ligne][colonne];
        tailleCases=tailleC;
        hauteur = ligne;
        largeur = colonne;
    }
    
    // A IMPLEMENTER
    public void Evenement( long date) {
        ;
    }
    //
    


    public int getNbLignes() {
        return this.hauteur;
    }

    public int getNbColonnes() {
        return this.largeur;
    }

    public int getTailleCases() {
        return this.tailleCases;
    }

    //// Constructeur de la Carte ///
    public void setCase(int x, int y, NatureTerrain nat) {
        this.map[x][y] = new Case(x,y,nat);
    }
    ////////////////////////////////

    public Case getCase(int x, int y) {
        return this.map[x][y];
    }

    public boolean voisinExiste( Case C, Direction D) {
        int x = C.getLigne();
        int y = C.getColonne();
        switch (D) {
            case NORD:
                if (x == 0) {
                    //System.out.println("on est tout au nord");
                    return false;
                } else {
                    return true;
                }
            case SUD:
                if ( x == hauteur - 1) {
                    //System.out.println("on est tout au sud");
                    return false;
                } else {
                    return true;
                }

            case EST:
                if ( y == largeur - 1) {
                    //System.out.println("on est tout à l'est");
                    return false;
                } else {
                    return true;
                }

            case OUEST:
                if ( y == 0) {
                    //System.out.println("on est tout à l'ouest");
                    return false;
                } else {
                    return true;
                }

        }
        // jamais atteint
        return true;
    }


    public Case voisinCase(Case C, Direction D) {
        int x = C.getLigne();
        int y = C.getColonne();
        switch (D) {
            case NORD:
                if ( voisinExiste(C,D) ) {
                    // x-1 car le nord correspond à diminuer l'indice
                    return this.map[x-1][y];
                } else {
                    System.out.println("Pas de voisin dans cette direction. Arrêt");
                    System.exit(0);
                }
            case SUD:
                if ( voisinExiste(C,D) ) {
                    // x+1 car le sud correspond à augmenter l'indice
                    return this.map[x+1][y];
                } else {
                    System.out.println("Pas de voisin dans cette direction. Arrêt");
                    System.exit(0);
                }

            case EST:
                if ( voisinExiste(C,D) ) {
                    return this.map[x][y+1];
                } else {
                    System.out.println("Pas de voisin dans cette direction. Arrêt");
                    System.exit(0);
                }

            case OUEST:
                if ( voisinExiste(C,D) ) {
                    return this.map[x][y-1];
                } else {
                    System.out.println("Pas de voisin dans cette direction. Arrêt");
                    System.exit(0);
                }

        }
        return(C);
    }
    // jamais atteint

}
