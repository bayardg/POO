public class TestEvenements {
	public static void main(String[] args) {
		Carte map = new Carte(10, 10, 2 );
		Drone Walle = new Drone();
		Walle.setCarte(map);
		Case C = new Case(1, 1, NatureTerrain.ROCHE);
		System.out.println("C.ligne = " + C.getLigne() + " C.colonne = " + C.getColonne());

		Walle.setPosition(C);
		System.out.println("Walle.ligne = " + Walle.getPosition().getLigne() + " Walle.colonne = " + Walle.getPosition().getColonne());
		SimulateurEvenement SE = new SimulateurEvenement();
		Deplacement Dp = new Deplacement(Walle, Direction.SUD, 600);
		for (int i=0;i<12*300;i=i+300){
			//System.out.println("balise");
			Dp = new Deplacement(Walle, Direction.SUD, i*300);
			SE.ajouteEvenement(Dp);
		}
		//		Recharger R = new Recharger(300);
		//		Intervenir I = new Intervenir(900);
		//		SE.ajouteEvenement(R);
		//		SE.ajouteEvenement(I);
		int i = 0;
		while (SE.simulationTerminee() == false){
			System.out.println("i=" + i);
			System.out.println("balise incrementation");
			SE.incrementeDate();
			i=i+300;
		}
	}
}
