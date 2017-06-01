/* Cet algorithme copie le fonctionnement de l'algo decrit sur la page Wikipedia anglaise */

import java.util.*;
import static java.lang.Math.*;

public class AStar {
    private Noeud depart;
    private Noeud arrivee;
    private Noeud[][] matNoeud;
    private Noeud[][] matNoeudPrec;
    private boolean cheminexiste;
    private LinkedList<Noeud> chemin;

    private LinkedList<Noeud> closedSet;
    private PriorityQueue<Noeud> openSet;

    private CalculGraphe graphe;

    public float getCout() {
        return arrivee.getG();
    }

    public AStar(Case start,Case goal,CalculGraphe graph){
        graphe=graph;
        matNoeud = new Noeud[graphe.h][graphe.l];
        matNoeudPrec = new Noeud[graphe.h][graphe.l];
        closedSet = new LinkedList<Noeud>();
        openSet = new PriorityQueue<Noeud>();
        

        depart = new Noeud(start.getLigne(),start.getColonne());
        depart.setG(0);
        depart.setH(0);
        arrivee = new Noeud(goal.getLigne(),goal.getColonne());
        cheminexiste = false;
        
        for (int i=0;i<graphe.h;i++) {
            for (int j=0;j<graphe.h;j++) {
                matNoeud[i][j] = new Noeud(i,j);
                matNoeud[i][j].setH(heuristic(matNoeud[i][j],arrivee));
            }
        }

        matNoeud[start.getLigne()][start.getColonne()] = depart;
        matNoeud[goal.getLigne()][goal.getColonne()] = arrivee;

        openSet.add(depart);

        while (openSet.size()>0) {
            Noeud current = openSet.remove();
            if (current==arrivee) {
                cheminexiste = true;
                openSet.clear();
                this.construireChemin();
            }
            else {
                closedSet.add(current);
                int posGraphe = current.getX()*graphe.h + current.getY();
                for (int i=0;i<graphe.h*graphe.l;i++) {
                    if (graphe.graphe[posGraphe][i]>0) {
                        Noeud neighbor = matNoeud[i/graphe.h][i%graphe.h];
                        if (!closedSet.contains(neighbor)) {
                            float tryg = current.getG() + graphe.graphe[posGraphe][i];
                            boolean cond = !openSet.contains(neighbor);
                            if (cond) {
                                openSet.add(neighbor);
                            }
                            if (!(!cond && tryg>=neighbor.getG())) {
                                matNoeudPrec[neighbor.getX()][neighbor.getY()] = current;
                                neighbor.setG(tryg);
                            }
                        }
                    }
                }
            }
        }
    }

    private void construireChemin() {
        Noeud current = this.arrivee;
        this.chemin = new LinkedList<Noeud>();
        while (current!=depart) {
            this.chemin.addFirst(current);
            current = matNoeudPrec[current.getX()][current.getY()];
        }
        this.chemin.addFirst(current);
    }

    public LinkedList<Noeud> getChemin() {
        return this.chemin;
    }

    public boolean getExiste() {
        return this.cheminexiste;
    }

    // Vol d'oiseau
    static private int heuristic(Noeud A, Noeud B) {
        return (abs(A.getX()-B.getX())+abs(A.getY()-B.getY()));
    }
}
