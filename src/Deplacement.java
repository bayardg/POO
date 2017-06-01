public class Deplacement extends Evenement{

    private Robot Walle;

    //public pour debug
    public Direction direction;

    public Deplacement(Robot R, Direction D,long d) {
        Walle = R;
        direction = D;
        super.setDate(d);
    }

    public void execute(){
        this.Walle.setPosition(this.Walle.getCarte().voisinCase(this.Walle.getPosition(), this.direction));
    }

    public Robot getRob() {
        return this.Walle;
    }

    public String toString() {
        return this.direction.toString();
    }

}
