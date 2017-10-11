package game.objekte;

public abstract class Verflucht extends Gegenstand {
	private static final long serialVersionUID = 1L;
	private int runde;
	public int getRunde(){
		return runde;
	}
	public void zurücksetzenRunde(){
		runde=0;
	}
	public void erhöheRunde(){
		runde+=1;
	}
	@Override
	public abstract String toString();
		
	
}
