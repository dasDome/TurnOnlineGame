package game.basis;

import java.io.Serializable;

public class Position implements Serializable{

	private static final long serialVersionUID = 1L;
	private int xKoordinate;
	private int yKoordinate;
	
	public Position(int x, int y){
		setX(x);
		setY(y);
	}
	public void setX(int x){
		this.xKoordinate=x;
	}
	public int getX(){
		return xKoordinate;
	}
	public void setY(int y){
		this.yKoordinate=y;
	}
	public int getY(){
		return yKoordinate;
	}
	@Override
	public String toString(){
		return "("+getX()+"/"+getY()+")";
	}
}
