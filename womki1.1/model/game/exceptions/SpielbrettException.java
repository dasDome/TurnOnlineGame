package game.exceptions;

//Fehler der bei der Spielbrettrzeugung auftritt
public class SpielbrettException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SpielbrettException(String message) {
		super(message);
	}
}
