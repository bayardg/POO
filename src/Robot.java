import java.util.*;

public abstract class Robot {

    private double vitesse;

    private Case position;	

    private int contenance; //taille du reservoire

    private int litres; //litres contenus dans le reservoir

    private int debit;

    private int temps_debit;

    private int temps_remplissage;

    private TypeRobot type;

    private Carte map;

    private CalculGraphe graphe;

    private boolean busy;

    //public pour debug
    public SimulateurEvenement simuEvent;

    public void setCarte(Carte m) {
        map = m;
    }

    public Carte getCarte() {
        return this.map;
    }

    public TypeRobot getTypeRobot(){
        return this.type;
    }

    public void setTypeRobot(TypeRobot t){
        type = t;
    }


    public Case getPosition(){
        return this.position;
    }

    public void setContenance(int contenance){
        this.contenance=contenance;
    }

    public void setReserve(int eau){
        if (eau>this.contenance) {
            throw new IllegalArgumentException("Reservoir trop petit");
        }
        else{
            this.litres=eau;
        }
    }

    public int getLitreRobot() {
        return this.litres;
    }

    public void setDebit(int d){
        this.debit = d;
    }

    public int getDebit() {
        return this.debit;
    }

    public void setTempsDebit(int temps){
        this.temps_debit = temps;
    }

    public int getTempsDebit() {
        return this.temps_debit;
    }

    public void setTempsRemplissage(int temps){
        this.temps_remplissage = temps;
    }

    public int getTempsRemplissage(){
        return this.temps_remplissage;
    }

    public void setPosition(Case P){
        this.position = P;
    }

    public void setVitesse(double v){
        this.vitesse = v;
    }

    public int getContenance(){
        return this.contenance;
    }

    public double getVitesse(NatureTerrain terrain){
        return this.vitesse;
    }

    public void deverserEau(Incendie feu){
        if ( feu.getLitreFeu() >= this.getLitreRobot() ) {
            feu.setLitre(feu.getLitreFeu() - this.getLitreRobot());
            this.setReserve(0);
        } else {
            feu.setLitre(0);
            this.setReserve(this.getLitreRobot() - feu.getLitreFeu() );
        }
    }
    /*
       public void remplissage(int duree){
       int i=0;
       while (i<duree && this.litres<this.contenance){
       if (i+this.temps_debit<duree){
       if (this.litres+this.debit>this.contenance){
       this.litres=this.contenance;
       }
       else{
       setReserve(this.litres + this.debit);
       i=i+this.temps_debit;
       }
       }
       else
       {
       if (this.litres+this.debit*(duree-i)/this.temps_debit>this.contenance){
       this.litres=this.contenance;
       }
       else{
       setReserve(this.litres + this.debit*(duree-i)/this.temps_debit);
       i=i+this.temps_debit;
       }

       }
       }
       }
       */
    @Override
    public String toString() {
        return("Un robot de type " + this.getTypeRobot() + " se situe en coordonees (" + this.getPosition().getLigne() + "," + this.getPosition().getColonne()+")");
    }

    public boolean getBusy() {
        return this.busy;
    }

    public void setBusy(boolean b) {
        this.busy = b;
    }

    public void setSimu(SimulateurEvenement s) {
        this.simuEvent = s;
    }

    public void addEvent(Evenement e) {
        this.simuEvent.ajouteEvenement(e);
    }

    public void addEvents(LinkedList<Evenement> e) {
        this.simuEvent.ajouteListeEvenement(e);
    }

    public void setGraphe(CalculGraphe g) {
        this.graphe=g;
    }

    public CalculGraphe getGraphe() {
        return this.graphe;
    }

//	abstract void remplirReservoir();


/* Gene actuellement
   public boolean estAccessible(Case C){
   if (Math.abs(this.position.getLigne()- C.getLigne()) == 1){
   return true;
   }
   if (Math.abs(this.position.getColonne() - C.getColonne()) == 1){
   return true;
   }
   return false;
   } */

}
