package game.basis;
import java.io.Serializable;

import game.exceptions.*;
public class Spielbrett implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x; 
	private int y; 
	private Spielfeld[][] spielfelder;

	
	public Spielbrett(int länge, int breite) {
		if(länge<1 && breite <1 && (länge==1 && breite ==1)){
			throw new SpielbrettException("Ungültige Breite oder Länge");
		}
		setX(länge);
		setY(breite);
		spielfelder = new Spielfeld[x][y];
		for (int i = 0; i < spielfelder.length; i++) {
			for (int j = 0; j < spielfelder[i].length; j++) {
				double feldart = Math.random();
				if (feldart <= 0.3) {
					spielfelder[i][j] = new Ebene(this,new Position(i+1,j+1));
				} else {
					if (feldart > 0.3 && feldart <= 0.6) {
						spielfelder[i][j] = new Berg(this, new Position(i+1,j+1));
					} else {
						spielfelder[i][j] = new Sumpf(this, new Position(i+1,j+1));
					}
				}
			}
		}
	}

	private void setX(int x) {
		if (x >= 1) {
			this.x = x;
		} else {
			throw new SpielbrettException("Ungültige Länge des Spielfeldes");
		}
	}

	public int getX() {
		return x;
	}

	private void setY(int y) {
		if (y >= 1) {
			this.y = y;
		} else {
			throw new SpielbrettException("Ungültige Breite des Spielfeldes");
		}
	}

	public int getY() {
		return y;
	}
	public Spielfeld getSpielfeld(Position position){
		if(existiertFeld(position)){
			return spielfelder[position.getX()-1][position.getY()-1];
		}else{
			throw new UngueltigePositionException("Position existiert nicht");
		}
	}
	public boolean istFeldFrei(Position position){
		if(getSpielfeld(position).getFigur()==null){
			return true;
		}else{
			return false;
		}
	}
	public boolean existiertFeld(Position position){
		if(position!=null){
			return((position.getX()-1)<spielfelder.length && (position.getY()-1)< spielfelder[0].length && position.getX()>0 && position.getY()>0);
		}
		return false;
	}
	//für CSV Laden
	public void setSpielfeld(Position position,Spielfeld spielfeld){
		if(position!=null && spielfeld!=null){
			spielfelder[position.getX()-1][position.getY()-1]=spielfeld;
		}
	}
}
