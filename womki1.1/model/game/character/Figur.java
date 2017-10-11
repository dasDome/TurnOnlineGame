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
@XmlType(propOrder={"rasse","name","id","angriff","gesundheit","verteidigung","bewegung","gegenst�nde"})


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
	@XmlElementWrapper(name="gegenst�nde")
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
	private ArrayList<Gegenstand> gegenst�nde = new ArrayList<Gegenstand>();
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
	public void setGegenst�nde(Gegenstand[] gegenst�nde){
		for(Gegenstand g:gegenst�nde){
			this.gegenst�nde.add(g);
		}
	}
	public void setGegenst�nde(ArrayList<Gegenstand> gegenst�nde){
		if(gegenst�nde!=null){
			throw new RuntimeException("Fehler");
		}
		this.gegenst�nde=gegenst�nde;
	}
	
	public Gegenstand[] getGegenst�nde2(){
		Gegenstand[] gegenst�nde=new Gegenstand[this.gegenst�nde.size()];
		for(int i=0;i<this.gegenst�nde.size();i++){
				gegenst�nde[i]=this.gegenst�nde.get(i);
		}
		return gegenst�nde;
	}
	public void setName(String name)throws SpielerException{
		if(name!=null && name.length()>2){
			this.name=name;
		}else{
			throw new SpielerException("Ung�ltiger Name");
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
			this.erh�heAngriff(-(spielfeld.getAngriffBoni()));
			this.erh�heBewegung(-(spielfeld.getBewegungsBoni()));
			this.erh�heVerteidigung(-(spielfeld.getVerteidigungsBoni()));
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
				this.erh�heAngriff(spielfeld.getAngriffBoni());
				this.erh�heBewegung(spielfeld.getBewegungsBoni());
				this.erh�heVerteidigung(spielfeld.getVerteidigungsBoni());
				for (int i = 0; i < spielfeld.getGegenst�nde().size(); i++) {
					this.add(spielfeld.getGegenst�nde().get(i));

				}
				spielfeld.removeGegenst�nde();
			} else {
				throw new BesetztesFeldException("Feld ist bereits besetzt");
			}
		} else {
			throw new RuntimeException("Ung�ltiges Feld");
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

	private void erh�heAngriff(int a) {
		this.setAngriff(getAngriff() + a);
	}

	private void erh�heGesundheit(int g) {
		this.setGesundheit(getGesundheit() + g);
	}

	private void erh�heVerteidigung(int v) {
		this.setVerteidigung(getVerteidigung() + v);
	}

	private void erh�heBewegung(int b) {
		this.setBewegung(getBewegung() + b);
	}
	
	public void add(Gegenstand gegenstand) {
		gegenst�nde.add(gegenstand);
		
		this.erh�heBewegung(gegenstand.getBewegung());
		this.erh�heGesundheit(gegenstand.getGesundheit());
		this.erh�heAngriff(gegenstand.getAngriff());
		this.erh�heVerteidigung(gegenstand.getVerteidigung());
		
		if(gegenstand instanceof Verflucht){
			Verflucht verflucht=(Verflucht) gegenstand;
			verflucht.zur�cksetzenRunde();
		}
	}
	private boolean fallenlassen(int i){
		int index=i; 
		if(index<0){
			throw new RuntimeException("Nicht enthalten");
		}
		Gegenstand r�ckgabe=gegenst�nde.get(index);
		if(!(r�ckgabe instanceof Verflucht)){
			gegenst�nde.remove(index);
			this.erh�heAngriff(-r�ckgabe.getAngriff());
			this.erh�heBewegung(-r�ckgabe.getBewegung());
			this.erh�heGesundheit(-r�ckgabe.getGesundheit());
			this.erh�heVerteidigung(-r�ckgabe.getVerteidigung());
			spielfeld.addGegenstand(r�ckgabe);
			return true;
		}else{
			Verflucht verfluchterGegenstand=(Verflucht) r�ckgabe;
			if(verfluchterGegenstand.getRunde()>=5){ //Beginn mit Runde 0
					gegenst�nde.remove(index);
					this.erh�heAngriff(-r�ckgabe.getAngriff());
					this.erh�heBewegung(-r�ckgabe.getBewegung());
					this.erh�heGesundheit(-r�ckgabe.getBewegung());
					this.erh�heVerteidigung(-r�ckgabe.getVerteidigung());
					spielfeld.addGegenstand(r�ckgabe);
					verfluchterGegenstand.zur�cksetzenRunde();
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
			for(int i=0;i< gegenst�nde.size();i++){
				if(gegenstand.equals(gegenst�nde.get(i))){
					return fallenlassen(i);
				}
			}
		}
		return false;
	}

	private int sucheGegenstand(Objekte gegenstand) {
		switch (gegenstand) {
		case Kurzschwert:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof Kurzschwert) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;
		case Langschwert:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof Langschwert) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;
		case MantelDerUnsichtbarkeit:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof MantelDerUnsichtbarkeit) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;

		case Siebenmeilenstiefel:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof Siebenmeilenstiefel) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;
		case VerfluchterUmhang:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof VerfluchterUmhang) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;
		case Schild:
			for (Gegenstand g : gegenst�nde) {
				if (g instanceof Schild) {
					return gegenst�nde.indexOf(g);
				}
			}
			break;
		default:
			return -1;
		}
		return -1;
	}

	public ArrayList<Gegenstand> getGegenst�nde() {
		return gegenst�nde;
	}

	public int getAnzGegenst�nde() {
		return gegenst�nde.size();
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
//		for (int i = 0; i < gegenst�nde.size(); i++) {
//			objekte += gegenst�nde.get(i) + " ";
//		}
//		return attribute +" "+ getSpielfeld() + " -"+objekte;
		return this.name;

	}

}
