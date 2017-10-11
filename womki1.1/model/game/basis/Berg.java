package game.basis;

public class Berg extends Spielfeld {
	private static final long serialVersionUID = 1L;

	public Berg(Spielbrett spielbrett, Position position) {
		super(spielbrett, position);
		super.setBewegungsBoni(-1);
		super.setVerteidigungsBoni(1);
	}

	@Override
	public String toString() {
		return "Berg" + getPosition();
	}

}
