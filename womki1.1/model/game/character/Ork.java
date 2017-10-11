package game.character;

import javax.xml.bind.annotation.XmlRootElement;

import game.basis.*;
import game.exceptions.SpielerException;
@XmlRootElement(name="Ork")
public class Ork extends Figur {

	private static final long serialVersionUID = 1L;
	public Ork(){
	}
	public Ork(Spielfeld spielfeld,String name)throws SpielerException {
		super(spielfeld,name);
		super.setAngriff(super.getAngriff() + 1);
		super.setRasse("Ork");
	}
	@Override
	public boolean equals(Object ob){
		if(ob instanceof Ork){
			Ork ork= (Ork) ob;
			return(ork.getID()==this.getID());
		}
		return false;
	}

}
