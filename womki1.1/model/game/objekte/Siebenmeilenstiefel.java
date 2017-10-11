package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Siebenmeilenstiefel")
public class Siebenmeilenstiefel extends Gegenstand {

	private static final long serialVersionUID = 1L;

	public Siebenmeilenstiefel() {
		super.setBewegung(3);
	}

	@Override
	public String toString() {
		return "Siebenmeilenstiefel";
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof Siebenmeilenstiefel;
	}
}
