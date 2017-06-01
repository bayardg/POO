public class Incendie {

	private Case position;

	private int Litres;

    private boolean enCours;

	public Incendie( Case x, int l) {
		position = x;
		Litres = l;
        setEnCours(false);
	}

    public Case getPosIncendie() {
        return this.position;
    }

    public int getLitreFeu() {
        return this.Litres;
    }

    public void setLitre(int a) {
        this.Litres = a;
    }

    public boolean getEnCours() {
        return this.enCours;
    }


    public void setEnCours(boolean b) {
        this.enCours = b;
    }

    public String toString() {
        return("Un incendie se trouve en (" + this.getPosIncendie().getLigne() + "," + this.getPosIncendie().getColonne() + ")\nIl faut "+ this.getLitreFeu() + "litres.");
    }
}
