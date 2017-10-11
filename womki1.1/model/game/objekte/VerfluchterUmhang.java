package game.objekte;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="VerfluchterUmhang")
public class VerfluchterUmhang extends Verflucht {
	private static final long serialVersionUID = 1L;

	public VerfluchterUmhang() {
		super.setAngriff(-2);
		super.setVerteidigung(-2);
		super.setGesundheit(-2);
	}

	@Override
	public String toString() {
		return "VerfluchterUmhang";
	}

	@Override
	public boolean equals(Object object) {
		return object instanceof VerfluchterUmhang;
	}
}
