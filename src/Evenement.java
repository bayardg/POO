public abstract class Evenement implements Comparable<Evenement>{
	private long date;

    @Override
    public int compareTo(Evenement e) {
        if (this.getDate() > e.getDate()){
            return 1;
        }
        else if (this.getDate()==e.getDate()){
            return 0;
        }
        else {
            return -1;
        }
    }

//	public void(long date);

	public long getDate(){
		return this.date;
	}

    protected void setDate(long d) {
        this.date=d;
    }

	abstract public void execute();
}
