package game.basis;

public class Ebene extends Spielfeld {

	private static final long serialVersionUID = 1L;

	public Ebene(Spielbrett spielbrett, Position position) {
		super(spielbrett, position);
	}

	@Override
	public String toString() {
		return "Ebene " + super.getPosition();
	}
}
