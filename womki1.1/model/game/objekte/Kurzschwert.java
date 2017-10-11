package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Kurzschwert")
public class Kurzschwert extends Schwert {

	private static final long serialVersionUID = 1L;
	public Kurzschwert(){
		super.setAngriff(1);
	}
	@Override
	public String toString(){
		return "Kurzschwert";
	}
	@Override
	public boolean equals(Object object){
		return object instanceof Kurzschwert; 
	}
	

}
