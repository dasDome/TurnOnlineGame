package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Langschwert")
public class Langschwert extends Schwert {

	private static final long serialVersionUID = 1L;

	public Langschwert() {
		super.setAngriff(3);
		super.setVerteidigung(-1);
	}

	@Override
	public String toString() {
		return "Langschwert";
	}

	@Override
	public boolean equals(Object object){
		return object instanceof Langschwert;
	}
}
