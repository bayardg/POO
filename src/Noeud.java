public class Noeud implements Comparable<Noeud> {
    private int x;
    private int y;
    private float g; //cout
    private float h; //heuristique

    public Noeud(int a, int b) {
        x=a;
        y=b;
        g=Float.POSITIVE_INFINITY;
        h=Float.POSITIVE_INFINITY;
    }

    @Override
    public int compareTo(Noeud e) {
        if (this.getF() > e.getF()) {
            return 1;
        }
        else if (this.getF()==e.getF()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    public void setG(float a) {
        this.g=a;
    }

    public void setH(float a) {
        this.h=a;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public float getG() {
        return this.g;
    }

    public float getH() {
        return this.h;
    }

    public float getF() {
        return this.g + this.h;
    }

    public String toString() {
        return("Noeud (" + this.getX() + "," + this.getY() + ")" + " Cout : " + this.getG());
    }
}
