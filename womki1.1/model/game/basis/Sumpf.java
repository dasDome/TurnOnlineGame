package game.basis;

public class Sumpf extends Spielfeld{

	private static final long serialVersionUID = 1L;
	public Sumpf(Spielbrett spielbrett,Position position){
		super(spielbrett, position);
		super.setAngriffBoni(-1);
		super.setBewegungsBoni(-1);
		super.setVerteidigungsBoni(-1);
	}
	@Override
	public String toString(){
		return "Sumpf"+getPosition();
	}
}
