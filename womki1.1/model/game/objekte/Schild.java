package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Schild")
public class Schild extends Gegenstand{

	private static final long serialVersionUID = 1L;
	public Schild(){
		super.setVerteidigung(3);
	}
	@Override
	public String toString(){
		return "Schild";
	}
	@Override
	public boolean equals(Object object){
		return object instanceof Schild;
	}
}
