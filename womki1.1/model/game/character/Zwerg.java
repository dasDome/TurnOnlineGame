package game.character;


import javax.xml.bind.annotation.XmlRootElement;

import game.basis.Spielfeld;
import game.exceptions.SpielerException;
@XmlRootElement(name="Zwerg")
public class Zwerg extends Figur {

	private static final long serialVersionUID = 1L;
	public Zwerg(){
	}
	public Zwerg(Spielfeld spielfeld,String name)throws SpielerException {
		super(spielfeld,name);
		super.setAngriff(super.getAngriff() + 2);
		super.setBewegung(super.getBewegung() - 1);
		super.setRasse("Zwerg");
	}
	@Override
	public boolean equals(Object ob){
		if(ob instanceof Zwerg){
			Zwerg zwerg= (Zwerg) ob;
			return (zwerg.getID()== this.getID());
		}
		return false;
	}
}
