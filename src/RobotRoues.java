public class RobotRoues extends Robot{

	public RobotRoues(){
		super.setVitesse(80.0);
		super.setContenance(5000);
		super.setReserve(5000);
		super.setDebit(100);
		super.setTempsDebit(5);
		super.setTempsRemplissage(600);
		super.setTypeRobot(TypeRobot.ROUES);
        super.setBusy(false);

	}

	@Override
		public void setPosition(Case P){
			if (P.getNature() == NatureTerrain.EAU || P.getNature() == NatureTerrain.FORET || P.getNature() == NatureTerrain.ROCHE){
				throw new IllegalArgumentException("Ce robot ne peut se déplacer que sur terrain libre ou habitat");
			}else{
				super.setPosition(P);
			}
		}
}

