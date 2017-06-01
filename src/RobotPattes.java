public class RobotPattes extends Robot{


	public RobotPattes(){
		super.setVitesse(30.0);
		super.setTypeRobot(TypeRobot.PATTES);
        super.setBusy(false);
        super.setDebit(10);
        int i = Integer.MAX_VALUE;
        super.setTempsDebit(1);
        super.setContenance(i);
        super.setReserve(i);

	}

	@Override
		public void setPosition(Case P){
			if (P.getNature() == NatureTerrain.EAU ){
				throw new IllegalArgumentException("Ce robot ne peut se rendre sur l'eau");
			}
			else{
				super.setPosition(P);
			}
		}

	@Override
		public double getVitesse(NatureTerrain terrain){
			if (terrain == NatureTerrain.ROCHE){
				return 0.5*super.getVitesse(terrain);
			}
			else{
				return 10.0;
			}
		}

	@Override
		public void deverserEau( Incendie feu){
            feu.setLitre(0);
		}
}
