package game.character;

import java.io.Serializable;
import java.util.*;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementRef;

import game.basis.*;
import game.exceptions.BesetztesFeldException;
import game.exceptions.SpielerException;
import game.objekte.*;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement(name="Figur")
@XmlType(propOrder={"rasse","name","id","angriff","gesundheit","verteidigung","bewegung","gegenstände"})


public abstract class Figur implements Serializable {
	@XmlTransient
	private static final long serialVersionUID = 1L;
	@XmlTransient
	private static int anzahl;
	private int id;
	private int angriff;
	private int gesundheit;
	private int verteidigung;
	private int bewegung;

	private String rasse;
	@XmlTransient
	private int schritte;
	@XmlTransient
	private Spielfeld spielfeld;
	@XmlElementWrapper(name="gegenstände")
//	@XmlElement(name="gegenstand")

	  @XmlElementRefs( 
			  { 
			    @XmlElementRef( type = Kurzschwert.class ), 
			    @XmlElementRef( type = Langschwert.class ), 
			    @XmlElementRef( type = MantelDerUnsichtbarkeit.class ), 
			    @XmlElementRef( type = Schild.class ), 
			    @XmlElementRef( type = Siebenmeilenstiefel.class ), 
			    @XmlElementRef( type = VerfluchterUmhang.class ), 
			  } ) 
	private ArrayList<Gegenstand> gegenstände = new ArrayList<Gegenstand>();
	private String name;
	
	public Figur(){
	}
	public Figur(Spielfeld spielfeld,String name)throws SpielerException{
		this();
		anzahl++;
		setID(anzahl);
		
		setName(name);
		
		setAngriff(10);
		setGesundheit(10);
		setVerteidigung(10);
		setBewegung(3);
		setSpielfeld(spielfeld);
	}
	public void setGegenstände(Gegenstand[] gegenstände){
		for(Gegenstand g:gegenstände){
			this.gegenstände.add(g);
		}
	}
	public void setGegenstände(ArrayList<Gegenstand> gegenstände){
		if(gegenstände!=null){
			throw new RuntimeException("Fehler");
		}
		this.gegenstände=gegenstände;
	}
	
	public Gegenstand[] getGegenstände2(){
		Gegenstand[] gegenstände=new Gegenstand[this.gegenstände.size()];
		for(int i=0;i<this.gegenstände.size();i++){
				gegenstände[i]=this.gegenstände.get(i);
		}
		return gegenstände;
	}
	public void setName(String name)throws SpielerException{
		if(name!=null && name.length()>2){
			this.name=name;
		}else{
			throw new SpielerException("Ungültiger Name");
		}
	}
	public String getName(){
		return name;
	}

	public void setID(int id) {
		this.id = anzahl;
	}

	public int getID() {
		return id;
	}
	public void bewegen(Spielfeld neuesSpielfeld){
		if(neuesSpielfeld!=null){
			this.erhöheAngriff(-(spielfeld.getAngriffBoni()));
			this.erhöheBewegung(-(spielfeld.getBewegungsBoni()));
			this.erhöheVerteidigung(-(spielfeld.getVerteidigungsBoni()));
			spielfeld.setFigur(null);
			setSpielfeld(neuesSpielfeld);
		}
	}
	public void setSpielfeld(Spielfeld spielfeld) {
		if (spielfeld != null) {
			if (spielfeld.getFigur() == null || spielfeld.getFigur().equals(this)) {
				if(this.getSpielfeld()!=null && this.getSpielfeld().equals(spielfeld)){
					return;
				}
				this.spielfeld = spielfeld;
				spielfeld.setFigur(this);
				this.erhöheAngriff(spielfeld.getAngriffBoni());
				this.erhöheBewegung(spielfeld.getBewegungsBoni());
				this.erhöheVerteidigung(spielfeld.getVerteidigungsBoni());
				for (int i = 0; i < spielfeld.getGegenstände().size(); i++) {
					this.add(spielfeld.getGegenstände().get(i));

				}
				spielfeld.removeGegenstände();
			} else {
				throw new BesetztesFeldException("Feld ist bereits besetzt");
			}
		} else {
			throw new RuntimeException("Ungültiges Feld");
		}
	}

	public Spielfeld getSpielfeld() {
		return spielfeld;
	}

	public int getAngriff() {
		return angriff;
	}

	public void setAngriff(int angriff) {
		this.angriff = angriff;
	}

	public int getGesundheit() {
		return gesundheit;
	}
	
	public void setGesundheit(int gesundheit) {
		this.gesundheit = gesundheit;
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

	public int getSchritte() {
		return schritte;
	}

	public void einSchritt() {
		schritte++;
	}

	public void SchritteAufNull() {
		this.schritte = 0;
	}

	private void erhöheAngriff(int a) {
		this.setAngriff(getAngriff() + a);
	}

	private void erhöheGesundheit(int g) {
		this.setGesundheit(getGesundheit() + g);
	}

	private void erhöheVerteidigung(int v) {
		this.setVerteidigung(getVerteidigung() + v);
	}

	private void erhöheBewegung(int b) {
		this.setBewegung(getBewegung() + b);
	}
	
	public void add(Gegenstand gegenstand) {
		gegenstände.add(gegenstand);
		
		this.erhöheBewegung(gegenstand.getBewegung());
		this.erhöheGesundheit(gegenstand.getGesundheit());
		this.erhöheAngriff(gegenstand.getAngriff());
		this.erhöheVerteidigung(gegenstand.getVerteidigung());
		
		if(gegenstand instanceof Verflucht){
			Verflucht verflucht=(Verflucht) gegenstand;
			verflucht.zurücksetzenRunde();
		}
	}
	private boolean fallenlassen(int i){
		int index=i; 
		if(index<0){
			throw new RuntimeException("Nicht enthalten");
		}
		Gegenstand rückgabe=gegenstände.get(index);
		if(!(rückgabe instanceof Verflucht)){
			gegenstände.remove(index);
			this.erhöheAngriff(-rückgabe.getAngriff());
			this.erhöheBewegung(-rückgabe.getBewegung());
			this.erhöheGesundheit(-rückgabe.getGesundheit());
			this.erhöheVerteidigung(-rückgabe.getVerteidigung());
			spielfeld.addGegenstand(rückgabe);
			return true;
		}else{
			Verflucht verfluchterGegenstand=(Verflucht) rückgabe;
			if(verfluchterGegenstand.getRunde()>=5){ //Beginn mit Runde 0
					gegenstände.remove(index);
					this.erhöheAngriff(-rückgabe.getAngriff());
					this.erhöheBewegung(-rückgabe.getBewegung());
					this.erhöheGesundheit(-rückgabe.getBewegung());
					this.erhöheVerteidigung(-rückgabe.getVerteidigung());
					spielfeld.addGegenstand(rückgabe);
					verfluchterGegenstand.zurücksetzenRunde();
					return true;
				}

				return false;	
			}
		}
	

	public boolean fallenlassen(Objekte gegenstand) {
		int index = sucheGegenstand(gegenstand);
		if (index < 0) {
			return false;
		}
		return fallenlassen( index);
	}
	public boolean fallenlassen(Gegenstand gegenstand){
		if(gegenstand!=null){
			for(int i=0;i< gegenstände.size();i++){
				if(gegenstand.equals(gegenstände.get(i))){
					return fallenlassen(i);
				}
			}
		}
		return false;
	}

	private int sucheGegenstand(Objekte gegenstand) {
		switch (gegenstand) {
		case Kurzschwert:
			for (Gegenstand g : gegenstände) {
				if (g instanceof Kurzschwert) {
					return gegenstände.indexOf(g);
				}
			}
			break;
		case Langschwert:
			for (Gegenstand g : gegenstände) {
				if (g instanceof Langschwert) {
					return gegenstände.indexOf(g);
				}
			}
			break;
		case MantelDerUnsichtbarkeit:
			for (Gegenstand g : gegenstände) {
				if (g instanceof MantelDerUnsichtbarkeit) {
					return gegenstände.indexOf(g);
				}
			}
			break;

		case Siebenmeilenstiefel:
			for (Gegenstand g : gegenstände) {
				if (g instanceof Siebenmeilenstiefel) {
					return gegenstände.indexOf(g);
				}
			}
			break;
		case VerfluchterUmhang:
			for (Gegenstand g : gegenstände) {
				if (g instanceof VerfluchterUmhang) {
					return gegenstände.indexOf(g);
				}
			}
			break;
		case Schild:
			for (Gegenstand g : gegenstände) {
				if (g instanceof Schild) {
					return gegenstände.indexOf(g);
				}
			}
			break;
		default:
			return -1;
		}
		return -1;
	}

	public ArrayList<Gegenstand> getGegenstände() {
		return gegenstände;
	}

	public int getAnzGegenstände() {
		return gegenstände.size();
	}

	public String getRasse(){
		return rasse;
	}
	
	public void setRasse(String rasse){
		this.rasse=rasse;
	}
	
	
	@Override
	public abstract boolean equals(Object o);
	
	@Override
	public String toString() {

//		String attribute = "(" + "a" + this.getAngriff() + "|" + "b"
//				+ this.getBewegung() + "|" + "g" + this.getGesundheit() + "|"
//				+ "v" + this.getVerteidigung() + ")";
//		String objekte = "";
//		for (int i = 0; i < gegenstände.size(); i++) {
//			objekte += gegenstände.get(i) + " ";
//		}
//		return attribute +" "+ getSpielfeld() + " -"+objekte;
		return this.name;

	}

}
