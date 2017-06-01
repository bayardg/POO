public class Case {

	private int ligne;

	private int colonne;

	private NatureTerrain nature;

	public Case(int x, int y, NatureTerrain nat) {
		ligne = x;
		colonne = y;
		nature = nat;
	}


	public int getLigne() {
		return this.ligne;
	}

	public int getColonne() {
		return this.colonne;
	}

	public NatureTerrain getNature() {
		return this.nature;
	}

}
