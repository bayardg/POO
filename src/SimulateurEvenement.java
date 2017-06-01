import java.util.*;

public class SimulateurEvenement {

    private long dateSimulation;

    private PriorityQueue<Evenement> listeEvenements;

    public SimulateurEvenement(){

        this.dateSimulation = 0;

        this.listeEvenements = new PriorityQueue<Evenement>();
    }

    public void ajouteEvenement( Evenement e ){
        this.getListeEvenement().add(e);
    }


    public PriorityQueue<Evenement> getListeEvenement() {
        return this.listeEvenements;
    }

    public void ajouteListeEvenement(LinkedList<Evenement> c) {
        this.getListeEvenement().addAll(c);
    }

    public long getDate() {
        return this.dateSimulation;
    }


    public void incrementeDate(){
        this.dateSimulation+=100;
        Evenement e = this.listeEvenements.peek();
        while (this.listeEvenements.size()>0 && e.getDate()<=this.dateSimulation){
            e = this.listeEvenements.remove();
            e.execute();
            e = this.listeEvenements.peek();
        }
    }


//System.out.println("balise d'execution");
//				e.execute();


	public boolean simulationTerminee(){
		if (this.listeEvenements.size()>0){
			return false;
		}
		return true;
	}
}
