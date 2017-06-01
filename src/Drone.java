public class Drone extends Robot{

	public Drone(){
		super.setVitesse(100.0);
		super.setContenance(10000);
		super.setReserve(10000);
		super.setDebit(10000);
		super.setTempsDebit(30);
		super.setTempsRemplissage(1800);
		super.setTypeRobot(TypeRobot.DRONE);
        super.setBusy(false);
	}
}
