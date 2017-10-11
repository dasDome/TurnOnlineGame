package game.character;

import javax.xml.bind.annotation.XmlRootElement;

import game.basis.*;
import game.exceptions.SpielerException;

@XmlRootElement(name="Mensch")
public class Mensch extends Figur {

	private static final long serialVersionUID = 1L;
	public Mensch(){
	}
	public Mensch(Spielfeld spielfeld,String name) throws SpielerException {
		super(spielfeld,name);
		super.setVerteidigung(super.getVerteidigung() + 1);
		super.setRasse("Mensch");
	}
	@Override
	public boolean equals(Object ob){
		if( ob instanceof Mensch ){
			Mensch mensch= (Mensch) ob;
			return (mensch.getID()==this.getID());
		}else{
			return false;
		}
	}
}
