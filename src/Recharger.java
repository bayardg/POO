public class Recharger extends Evenement{
	private Robot Walle;

	public Recharger(Robot r, long dateinit) {
        this.Walle=r;
		super.setDate(dateinit + this.Walle.getTempsRemplissage());
	}

	@Override
		public void execute(){
			this.Walle.setReserve(this.Walle.getContenance());
            /* Ceci est lie a la strategie elementaire choisie actuellement */
            this.Walle.setBusy(false);
		}
}
