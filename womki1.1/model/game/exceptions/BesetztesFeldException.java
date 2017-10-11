package game.exceptions;

public class BesetztesFeldException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BesetztesFeldException(String message){
		super(message);
	}
}
