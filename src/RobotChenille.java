public class RobotChenille extends Robot{

	public RobotChenille(){
		super.setVitesse(60.0);
		super.setContenance(2000);
		super.setReserve(2000);
		super.setDebit(100);
		super.setTempsDebit(8);
		super.setTempsRemplissage(300);
		super.setTypeRobot(TypeRobot.CHENILLES);
        super.setBusy(false);
	}

	@Override
		public void setPosition(Case P){
			if (P.getNature() == NatureTerrain.EAU || P.getNature() == NatureTerrain.ROCHE){
				throw new IllegalArgumentException("Ce robot ne peut se déplacer  sur de l'eau ou des rochers");
			}else{
				super.setPosition(P);
			}
		}

	@Override
		public double getVitesse(NatureTerrain terrain){
			if (terrain == NatureTerrain.FORET){
				return 0.5*super.getVitesse(terrain);
			}
			else{
				return super.getVitesse(terrain);
			}
		}
}

