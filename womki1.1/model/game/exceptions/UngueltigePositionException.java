package game.exceptions;

public class UngueltigePositionException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UngueltigePositionException(String message){
		super(message);
	}
}
