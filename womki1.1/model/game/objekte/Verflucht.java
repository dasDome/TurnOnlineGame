package game.objekte;

public abstract class Verflucht extends Gegenstand {
	private static final long serialVersionUID = 1L;
	private int runde;
	public int getRunde(){
		return runde;
	}
	public void zur�cksetzenRunde(){
		runde=0;
	}
	public void erh�heRunde(){
		runde+=1;
	}
	@Override
	public abstract String toString();
		
	
}
