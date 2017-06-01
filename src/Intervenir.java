public class Intervenir extends Evenement{

    private Robot Walle;

    private Incendie feu;

    public Intervenir(long d, Robot rob, Incendie fuego) {
        Walle = rob;
        feu = fuego;
        if ( fuego.getLitreFeu() >= rob.getLitreRobot() || rob.getTypeRobot() == TypeRobot.DRONE) {
            //pour eviter les erreurs d'arondi
            super.setDate(d + 50 + rob.getLitreRobot() * (rob.getTempsDebit() / rob.getDebit()));
        } else {
            super.setDate(d + 50 + (rob.getLitreRobot() - fuego.getLitreFeu()) * (rob.getTempsDebit() / rob.getDebit()));
        }
    }


    @Override
    public void execute(){
        this.Walle.deverserEau(this.feu);
        if ( this.Walle.getLitreRobot() == 0) {
            this.feu.setEnCours(false);
            TrouverEau.Eau( this.Walle, this.getDate()+5);
        }
        else {
            this.Walle.setBusy( false);
        }

    }

}
