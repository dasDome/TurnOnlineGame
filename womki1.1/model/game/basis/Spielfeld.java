package game.basis;

import java.io.Serializable;
import java.util.ArrayList;

import game.character.Figur;
import game.exceptions.*;
import game.objekte.Gegenstand;

public abstract class Spielfeld implements Serializable {

	private static final long serialVersionUID = 1L;

	private Position position;
	private int angriffBoni;
	private int verteidigungsBoni;
	private int bewegungBoni;
	private Spielbrett spielbrett;
	private Figur figur;
	private ArrayList<Gegenstand> gegenstände = new ArrayList<Gegenstand>();

	public Spielfeld(Spielbrett spielbrett, Position position) {
		gegenstände = new ArrayList<Gegenstand>();
		setSpielbrett(spielbrett);
		setPosition(position);
	}

	private void setPosition(Position position) {
		if (position != null) {
			this.position = position;
		} else {
			throw new SpielbrettException("Spielfeld muss eine Position haben");
		}
	}

	public Position getPosition() {
		return position;
	}

	protected void setAngriffBoni(int a) {
		this.angriffBoni = a;
	}

	public int getAngriffBoni() {
		return this.angriffBoni;
	}

	protected void setVerteidigungsBoni(int v) {
		this.verteidigungsBoni = v;
	}

	public int getVerteidigungsBoni() {
		return verteidigungsBoni;
	}

	protected void setBewegungsBoni(int b) {
		this.bewegungBoni = b;
	}

	public int getBewegungsBoni() {
		return bewegungBoni;
	}

	private void setSpielbrett(Spielbrett spielbrett) {
		if (spielbrett != null) {
			this.spielbrett = spielbrett;
		} else {
			throw new SpielbrettException(
					"Spielfeld muss zu einem Spielbrett gehören");
		}
	}

	public Spielbrett getSpielbrett() {
		return spielbrett;
	}

	public void setFigur(Figur figur) {
		if (figur == null) {
			this.figur = null;
		} else {
			if (this.figur == null) {
				this.figur = figur;
				figur.setSpielfeld(this);
			} else {
				if (this.figur.equals(figur) ) { 
					return;
				}
				else{
					throw new BesetztesFeldException("Feld ist bereits besetzt");
				}
			}
		}
	}

	public Figur getFigur() {
		return figur;
	}

	public void addGegenstand(Gegenstand gegenstand) {
		gegenstände.add(gegenstand);

	}

	public void removeGegenstände() {
		gegenstände.clear();
	}

	public ArrayList<Gegenstand> getGegenstände() {
		return gegenstände;
	}

	public int getAnzGegenstände() {
		return gegenstände.size();
	}
}
