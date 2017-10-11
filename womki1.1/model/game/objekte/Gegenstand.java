package game.objekte;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="gegenstand")
@XmlType(propOrder={"angriff","verteidigung","bewegung","gesundheit"})
public abstract class Gegenstand implements Serializable{
	@XmlTransient
	private static final long serialVersionUID = 1L;
	private int angriff;
	private int verteidigung;
	private int bewegung;
	private int gesundheit;

	public Gegenstand() {
		setAngriff(0);
		setVerteidigung(0);
		setBewegung(0);
		setGesundheit(0);
	}

	public int getAngriff() {
		return angriff;
	}

	public void setAngriff(int angriff) {
		this.angriff = angriff;
	}

	public int getVerteidigung() {
		return verteidigung;
	}

	public void setVerteidigung(int verteidigung) {
		this.verteidigung = verteidigung;
	}

	public int getBewegung() {
		return bewegung;
	}

	public void setBewegung(int bewegung) {
		this.bewegung = bewegung;
	}

	public void setGesundheit(int gesundheit) {
		this.gesundheit = gesundheit;
	}

	public int getGesundheit() {
		return gesundheit;
	}

	@Override
	public abstract String toString();
	@Override
	public abstract boolean equals(Object object);
}
