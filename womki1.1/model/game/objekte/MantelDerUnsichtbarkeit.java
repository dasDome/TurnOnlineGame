package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MantelDerUnsichtbarkeit")
public class MantelDerUnsichtbarkeit extends Gegenstand{
	
	private static final long serialVersionUID = 1L;
	public MantelDerUnsichtbarkeit(){
		super.setVerteidigung(5);
	}
	@Override
	public String toString(){
		return "MantelDerUnsichtbarkeit";
	}
	@Override
	public boolean equals(Object object){
		return object instanceof MantelDerUnsichtbarkeit;
	}
}
