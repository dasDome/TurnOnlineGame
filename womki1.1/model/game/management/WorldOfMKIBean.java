package game.management;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import com.itextpdf.text.log.SysoLogger;

import rollenspielInterface.iWoMKISpiel;

import game.Pdf.PdfDatei;
import game.Pdf.PdfDatei2;
import game.basis.*;
import game.character.*;
import game.exceptions.SpielerException;
import game.exceptions.UngueltigePositionException;
import game.objekte.*;
import game.speicherung.CSVDatei;
import game.speicherung.DateiArten;
import game.speicherung.Serialisiert;
import game.speicherung.SerialisiertFigur;
import game.speicherung.XMLDatei;
import game.speicherung.iDatenzugriff;
import game.speicherung.iDatenzugriff2;
import game.speicherung.iDatenzugriff3;
import game.speicherung.iDatenzugriff4;

public class WorldOfMKIBean implements Serializable, iWoMKISpiel {
	private static final long serialVersionUID = 1L;
	private ArrayList<Figur> figuren = new ArrayList<Figur>();
	private Spielbrett spielbrett;
	private ArrayList<Gegenstand> gegenstände = new ArrayList<Gegenstand>();
	private ArrayList<Integer> reihenfolge = new ArrayList<Integer>();
	private iDatenzugriff datenzugriff;
	private int runde;
	private Figur spieler;
	boolean sieg;
	private ArrayList<String> Meldungen=new ArrayList<String>();

	private static boolean istkampf = false;
	private HashMap<String, Figur> angriff = new HashMap<String, Figur>();
	private Figur[] angriff2=null;

	public WorldOfMKIBean() {
		this(2, 2);
	}

	public WorldOfMKIBean(int m, int n) {
		spielbrett = new Spielbrett(m, n);
		runde = 0;
		GegenständeHinzufügen();
	}

	public WorldOfMKIBean(Spielbrett spielbrett) {
		setSpielbrett(spielbrett);
		runde = 0;
		GegenständeHinzufügen();
	}

	public WorldOfMKIBean(String dateiName, DateiArten art) throws IOException {
		WorldOfMKIBean spiel = laden(dateiName, art);
		figuren = spiel.getFiguren();
		spielbrett = spiel.getSpielbrett();
		gegenstände = spiel.gegenstände;
		reihenfolge = spiel.getReihenfolge();
		runde = spiel.getRunde();
	}

	public WorldOfMKIBean(String dateiName, iDatenzugriff d) throws IOException {
		WorldOfMKIBean spiel = d.laden(dateiName);
		figuren = (spiel.getFiguren());
		setSpielbrett(spiel.getSpielbrett());
		gegenstände = spiel.gegenstände;
		reihenfolge = spiel.getReihenfolge();
		spieler = spiel.getSpieler();
		runde = spiel.getRunde();
	}

	public void setFiguren(ArrayList<Figur> figuren) {
		if (figuren != null) {
			this.figuren = figuren;
		} else {
			throw new RuntimeException("Keine Figurenliste");
		}
	}

	public void setGegenstände(ArrayList<Gegenstand> gegenstände) {
		if (gegenstände != null) {
			this.gegenstände = gegenstände;
		}
	}

	public ArrayList<Gegenstand> getGegenstände() {
		return gegenstände;
	}

	public void setReihenfolge(ArrayList<Integer> reihenfolge) {
		if (reihenfolge != null) {
			this.reihenfolge = reihenfolge;
		} else {
			throw new RuntimeException("Ungültiges ReihenfolgeArray");
		}
	}

	public void setRunde(int runde) {
		if (runde >= 0) {
			this.runde = runde;
		} else {
			throw new RuntimeException("Ungültige Runde");
		}
	}

	public void setSpieler(Figur spieler) {
		this.spieler = spieler;
	}

	/**
	 * gibt eine Beschreibung des aktiven Spielers zurück
	 * 
	 * @return String mit der Beschreibung des Spielers
	 */
	public String aktiverSpieler() {
		if (spieler != null) {
			return spieler.toString();
		} else {
			this.bestimmeSpieler();
			return spieler.toString();
		}
	}

	// Rundenzähler
	private void nächsteRunde() {
		runde++;
	}

	public int getRunde() {
		return runde;
	}

	// Speichern
	public void speichern(String dateiName, DateiArten art) throws IOException {
		iDatenzugriff2 datenzu;

		switch (art) {

		case pdf:
			datenzugriff = new PdfDatei();
			datenzu = new PdfDatei2();
			datenzu.speichern(dateiName, this.getSpielerDaten(),
					this.getSpielfeldDaten());
			break;
		case csv:
			datenzugriff = new CSVDatei();
			datenzugriff.speichern(dateiName, this);
			break;
		case ser:
			datenzugriff = new Serialisiert();
			datenzugriff.speichern(dateiName, this);
			break;
		}

		// datenzugriff.speichern(dateiName, this);

	}

	public void speichern(String dateiName, iDatenzugriff d) throws IOException {
		datenzugriff = d;
		d.speichern(dateiName, this);
	}

	public void speichernFigur(String dateiName,DateiArten art,String figurName)throws IOException{
		iDatenzugriff3 d3=null;
		Properties p= new Properties();
		Spielfeld alt=null;
		Figur f=null;
		for(Figur figur:figuren){
			if(figur.getName().equals(figurName)){
			alt=figur.getSpielfeld();
			figur.bewegen(new Ebene(new Spielbrett(2,2),new Position(1,1)));
			f=figur;
			}
		}
		switch(art){
			case xml: d3=new XMLDatei();
						
						p.put("figur", f);
						break;
			default: throw new IOException("Keine gültige DateiArt um Figur zu speichern");
		}
		d3.speichern(dateiName, p);
		f.bewegen(alt);
	}
	
	public void speichernFigur2(String dateiName, DateiArten art,String figurName)throws IOException{
		iDatenzugriff4 d4=null;
		Spielfeld alt=null;
		Figur f=null;
		for(Figur figur:figuren){
			if(figur.getName().equals(figurName)){
				alt=figur.getSpielfeld();
				figur.bewegen(new Ebene(new Spielbrett(2,2),new Position(1,1)));
				f=figur;
			}
		}
		switch(art){
		case ser: d4=new SerialisiertFigur();
		d4.speichern(dateiName, f);
		default:
			break;
		}
		f.bewegen(alt);
	}
	
	public Figur ladenFigur2(String dateiName, DateiArten art)throws IOException, SpielerException{
		if(figuren.size()==(spielbrett.getX()*spielbrett.getY())){
			throw new SpielerException("Genug Figuren auf Spielbrett");
		}
		iDatenzugriff4 d4=new SerialisiertFigur();

		Figur f=d4.laden(dateiName);
		for(int i = 0; i < this.getanzFiguren(); i++){
			if(f.getName().equals(this.getFiguren().get(i).getName())){
				throw new SpielerException("Spieler mit gleichem Namen");
			}
		}

		Spielfeld sf=this.zufallsSpielfeld();
		while(sf.getFigur()!=null){
			sf=this.zufallsSpielfeld();
		}
		f.setSpielfeld(sf);
		f.SchritteAufNull();
		figuren.add(f);
		return f;
	}
	
	
	public Figur ladenFigur(String dateiName,DateiArten art)throws IOException,SpielerException{
		//if(figuren.size()==spielbrett.getX()*spielbrett.getY()){
		if(figuren.size()==(spielbrett.getX()*spielbrett.getY())){
			throw new SpielerException("Genug Figuren auf Spielbrett");
		}
		/*iDatenzugriff3 d3=null;
		Properties p=null;
		switch(art){
		case xml: d3=new XMLDatei();
					break;
		default:throw new IOException("Keine gültige DateiArt um Figur zu speichern");
					}*/
		iDatenzugriff3 d3=new XMLDatei();
		Properties p=null;

		p=d3.laden(dateiName);
		Figur f=(Figur)p.get("Figur");
		for(Figur spieler: figuren){
			if(f.getName().equals(spieler)){
				throw new SpielerException("Spieler mit gleichem Namen");
			}
		}
		Spielfeld sf=this.zufallsSpielfeld();
		while(sf.getFigur()!=null){
			sf=this.zufallsSpielfeld();
		}
		f.setSpielfeld(sf);
		f.SchritteAufNull();
		figuren.add(f);
		return f;
	}
	
	
	
	
	public String xmlvorhanden(String dateiName,DateiArten art)throws IOException,SpielerException{
		
		iDatenzugriff3 d3=new XMLDatei();
		Properties p=null;

		p=d3.laden(dateiName);
		Figur f=(Figur)p.get("Figur");		
		return f.getName();
	}
	
	
	public String servorhanden(String dateiName, DateiArten art)throws IOException, SpielerException{
		
		iDatenzugriff4 d4=new SerialisiertFigur();

		Figur f=d4.laden(dateiName);
		return f.getName();
	}
	
	
	
	
	
	public WorldOfMKIBean laden(String dateiName, iDatenzugriff d)
			throws IOException {
		datenzugriff = d;
		return d.laden(dateiName);
	}

	// Laden
	public WorldOfMKIBean laden(String dateiName, DateiArten art)
			throws IOException {
		switch (art) {
		case pdf:
			datenzugriff = new PdfDatei();
			break;
		case csv:
			datenzugriff = new CSVDatei();
			break;
		case ser:
			datenzugriff = new Serialisiert();
			break;
		}
		return datenzugriff.laden(dateiName);
	}

	private void GegenständeHinzufügen() {
		gegenstände.add(new Kurzschwert());
		gegenstände.add(new Langschwert());
		gegenstände.add(new MantelDerUnsichtbarkeit());
		gegenstände.add(new Siebenmeilenstiefel());
		gegenstände.add(new VerfluchterUmhang());
		gegenstände.add(new Schild());

		setGegenstände();
	}

	public Spielbrett getSpielbrett() {
		return spielbrett;
	}

	// CSV-Speicherung
	public void setSpielbrett(Spielbrett spielbrett) {
		if (spielbrett != null) {
			this.spielbrett = spielbrett;
		} else {
			throw new RuntimeException("Man braucht ein Spielfeld");
		}
	}

	private void zufallsReihenfolge() {
		if (figuren.size() < 1) {
			throw new RuntimeException("Keine Spieler");
		}
		Random random = new Random();
		reihenfolge = new ArrayList<Integer>();
		while (reihenfolge.size() < figuren.size()) {
			int index = random.nextInt(figuren.size());
			if (!reihenfolge.contains(new Integer(index))) {
				reihenfolge.add(new Integer(index));
			}
		}
		this.bestimmeSpieler();
	}

	public ArrayList<Integer> getReihenfolge() {
		return reihenfolge;
	}

	public void neuerSpieler(Rollenfiguren charakter, String name)
			throws SpielerException {
		if (figuren.size() == (spielbrett.getX() * spielbrett.getY())) {
			throw new SpielerException("genügend Spieler");
		}
		for (Figur f : figuren) {
			if (f.getName().equals(name)) {
				throw new SpielerException("Name ist schon vorhanden");
			}
		}
		Spielfeld spielfeld = zufallsSpielfeld();
		while (spielfeld.getFigur() != null) {
			spielfeld = zufallsSpielfeld();
		}
		switch (charakter) {
		case Mensch:
			figuren.add(new Mensch(spielfeld, name));
			break;
		case Zwerg:
			figuren.add(new Zwerg(spielfeld, name));
			break;
		case Ork:
			figuren.add(new Ork(spielfeld, name));
			break;
		}

	}

	public Figur getSpieler() {
		if (figuren.size() <= 0) {
			throw new RuntimeException("Kein Spieler");
		}
		for (int i = 0; i < reihenfolge.size(); i++) {
			Figur f = figuren.get(reihenfolge.get(i));
			if (f.getSchritte() < f.getBewegung()) {
				return f;
			}
		}

		neueRundeStarten();
		for (Gegenstand g : gegenstände) {
			if (g instanceof Verflucht) {
				Verflucht v = (Verflucht) g;
				v.erhöheRunde();
			}
		}
		return getSpieler();
	}

	private void neueRundeStarten() {
		nächsteRunde();
		for (int i = 0; i < figuren.size(); i++) {
			figuren.get(i).SchritteAufNull();
		}
		zufallsReihenfolge();

	}

	public Figur[] bewegenOben() {

		this.bestimmeSpieler();
		Position position = spieler.getSpielfeld().getPosition();
		Position neuePos = new Position(position.getX(), position.getY() + 1);
		bewegen(neuePos, spieler);
		if (istkampf) {
			return angriff2;
		}else{
			angriff2=new Figur[4];
			
			return angriff2;
		}
	}

	public Figur[] bewegenUnten() {

		this.bestimmeSpieler();
		Position position = spieler.getSpielfeld().getPosition();
		Position neuePos = new Position(position.getX(), (position.getY() - 1));
		bewegen(neuePos, spieler);
		if (istkampf) {
			return angriff2;
		}else{
			angriff2=new Figur[4];
			
			return angriff2;
		}

	}

	public Figur[] bewegenRechts() {
		this.bestimmeSpieler();
		Position position = spieler.getSpielfeld().getPosition();
		Position neuePos = new Position(position.getX() + 1, position.getY());
		bewegen(neuePos, spieler);
		if (istkampf) {
			return angriff2;
		}else{
			angriff2=new Figur[4];
			
			return angriff2;
		}

	}

	public Figur[] bewegenLinks() {
		this.bestimmeSpieler();
		Position position = spieler.getSpielfeld().getPosition();
		Position neuePos = new Position(position.getX() - 1, position.getY());
		bewegen(neuePos, spieler);
		if (istkampf) {
			return angriff2;
		}else{
			angriff2=new Figur[4];
			
			return angriff2;
		}

	}

	private void bestimmeSpieler() {
		if (spieler == null) {
			spieler = getSpieler();
		}
		if (spieler.getSchritte() >= spieler.getBewegung()) {
			spieler = getSpieler();
		}
	}

	public boolean erholen() {
		this.bestimmeSpieler();
		if (spieler.getSchritte() > 0) {
			return false;
		} else {
			ArrayList<Gegenstand> gegenständeS = spieler.getGegenstände();
			int boni = 0;
			for (int i = 0; i < gegenständeS.size(); i++) {
				boni += gegenständeS.get(i).getGesundheit();
			}
			if (spieler.getGesundheit() < (10 - boni - 2)) {
				spieler.setGesundheit(spieler.getGesundheit() + 2);
			}
			if (spieler.getGesundheit() == (10 - boni - 1)) {
				spieler.setGesundheit(spieler.getGesundheit() + 1);
			}
			spieler.einSchritt();
			spieler.einSchritt();
			spieler.einSchritt();
			bestimmeSpieler();
			return true;
		}
	}

	private Figur[] bewegen(Position neuePos, Figur spieler) {
		Figur[] kampfTest = null;
		istkampf=false;
		if (spielbrett.existiertFeld(neuePos)) {
			spieler.einSchritt();
			if (spielbrett.istFeldFrei(neuePos)) {
				spieler.bewegen(spielbrett.getSpielfeld(neuePos));
				//System.out.println("kampftest1");
				if (spieler.getGesundheit() <= 0) {
					sterben(spieler);

				}

			} else {
				kampfTest=angriff(spieler, spielbrett.getSpielfeld(neuePos).getFigur());
				//System.out.println("kampftest2");
			}
			this.bestimmeSpieler();
			if(kampfTest!=null){
				
				//System.out.println("kampftest3");
				return kampfTest;//angriff2;
			}
		} else {
			
			throw new UngueltigePositionException("Feld existiert nicht");
		}
		return null; // zu dem kommt es ja nie
	}

	public boolean fallenlassen(Objekte gegenstand) {
		this.bestimmeSpieler();
		return (spieler.fallenlassen(gegenstand));

	}

	public boolean fallenlassen(Gegenstand gegenstand) {
		this.bestimmeSpieler();
		return spieler.fallenlassen(gegenstand);
	}

	public String getStatus() {
		Figur spieler = getSpieler();

		return spieler.toString();
	}

	private void setGegenstände() {
		for (int i = 0; i < gegenstände.size(); i++) {
			Spielfeld spielfeld = zufallsSpielfeld();
			spielfeld.addGegenstand(gegenstände.get(i));
		}
	}

	private Spielfeld zufallsSpielfeld() {
		Random zufall = new Random();
		int xWert = zufall.nextInt(spielbrett.getX());
		int yWert = zufall.nextInt(spielbrett.getY());
		Position position = new Position(xWert + 1, yWert + 1);
		return spielbrett.getSpielfeld(position);
	}

	private Figur[] angriff(Figur angreifer, Figur verteidiger) {
		System.out.println("Angriff");
		istkampf = true;
		if (verteidiger.getVerteidigung() < angreifer.getAngriff()) {
			int neueGesundheit = verteidiger.getGesundheit()
					- angreifer.getAngriff() + verteidiger.getVerteidigung();
			verteidiger.setGesundheit(neueGesundheit);
			//System.out.println("Angriff2");
			if (verteidiger.getGesundheit() <= 0) {
				sterben(verteidiger, angreifer);
				angriff2=new Figur[4];
				angriff2[2]=verteidiger;
				angriff2[3]=angreifer;
				return angriff2;
			} else {
				angriff2=new Figur[4];
				angriff2[0]= angreifer;
				angriff2[1]=verteidiger;
				return angriff2;
			}
			
		} else {
			//System.out.println("Angriff3");
			if (verteidiger.getVerteidigung() > angreifer.getAngriff()) {
				int neueGesundheit = angreifer.getGesundheit()
						- (-angreifer.getAngriff()
								+ verteidiger.getVerteidigung() + 1);
				angreifer.setGesundheit(neueGesundheit);
				//System.out.println("Angriff4");
				if (angreifer.getGesundheit() <= 0) {
					sterben(angreifer);
					angriff2=new Figur[4];
					angriff2[2]=angreifer;
					angriff2[3]=verteidiger;
		
					return angriff2;

				} else {
					angriff2=new Figur[4];
					angriff2[0]=verteidiger;
					angriff2[1]=angreifer;
					
					return angriff2;
				}
			}
		}

		return null; // zu dem kommt es ja gar nie

	}

	private void sterben(Figur toter, Figur sieger) {
		ArrayList<Gegenstand> schätze = toter.getGegenstände();
		for (int i = 0; i < schätze.size(); i++) {
			sieger.add(schätze.get(i));
		}
		Spielfeld spielfeld = toter.getSpielfeld();
		int index = figuren.indexOf(toter);
		reihenfolge.remove(new Integer(index));
		figuren.remove(toter);
		spielfeld.setFigur(null);
		sieger.bewegen(spielfeld);
		if (spieler == toter) {
			spieler = null;
		}
		
		if(this.getAnzGegner()==1){
			setsieg(true);
		}
		else{
		this.zufallsReihenfolge();
	}
	}

	public void sterben(Figur toter) {
		ArrayList<Gegenstand> schätze = toter.getGegenstände();
		for (int i = 0; i < schätze.size(); i++) {
			toter.getSpielfeld().addGegenstand(schätze.get(i));
		}

		toter.getSpielfeld().setFigur(null);
		reihenfolge.remove(new Integer(figuren.indexOf(toter)));
		figuren.remove(toter);
		if(this.getAnzGegner()==1){
			setsieg(true);
		}
		else{
		this.zufallsReihenfolge();
		}
		if (spieler == toter) {
			spieler = null;
		}
		toter = null;

	}
	public boolean getsieg(){
		return sieg;
	}
	public void setsieg(boolean sieg){
		this.sieg=sieg;
	}

	public int getAnzGegner() {
		return figuren.size();
	}

	public ArrayList<Figur> getFiguren() {
		return figuren;
	}

	public HashMap<String, HashMap<String, String>> getSpielerDaten() {
		HashMap<String, HashMap<String, String>> p = new HashMap<String, HashMap<String, String>>();
		for (int i = 0; i < getAnzGegner(); i++) {
			Figur f = figuren.get(i);
			p.put("figur " + i, getFigurenDaten(f));
		}
		return p;
	}

	public HashMap<String, String> getAktiverSpielerDaten() {
		if (spieler != null) {
			return this.getFigurenDaten(spieler);
		} else {
			return null;
		}
	}

	private HashMap<String, String> getFigurenDaten(Figur f) {
		if (f == null) {
			throw new RuntimeException("Keine Figur");
		}
		HashMap<String, String> daten = new HashMap<String, String>();
		String art;
		if (f instanceof Mensch) {
			art = "Mensch";
		} else {
			if (f instanceof Zwerg) {
				art = "Zwerg";
			} else {
				art = "Ork";
			}
		}
		daten.put("art", art);
		daten.put("name", f.getName());
		daten.put("id", Integer.toString(f.getID()));
		daten.put("angriff", Integer.toString((f.getAngriff())));
		daten.put("verteidigung", Integer.toString(f.getVerteidigung()));
		daten.put("gesundheit", Integer.toString(f.getGesundheit()));
		daten.put("bewegung", Integer.toString(f.getBewegung()));
		daten.put("schritte", Integer.toString(f.getSchritte()));
		daten.put("positionX",
				Integer.toString(f.getSpielfeld().getPosition().getX()));
		daten.put("positionY",
				Integer.toString(f.getSpielfeld().getPosition().getY()));
		daten.put("pfad", getBildPfad(art));
		String gegenstände = "";
		for (Gegenstand g : f.getGegenstände()) {
			gegenstände += g.toString() + ";";
		}
		daten.put("gegenstände", gegenstände);
		return daten;
	}

	private String getBildPfad(String name) {
		if (name.startsWith("Me")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\Mensch.png";
		}
		if (name.startsWith("Zw")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\Zwerg.png";
		}
		if (name.startsWith("Ork")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\Ork.png";
		}

		if (name.startsWith("Be")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\BergeSchwarzWeiss.png";
		}
		if (name.startsWith("Su")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\SumpfSchwarzWeiss.png";
		}
		if (name.startsWith("Eb")) {
			return "D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\Images\\Ebene2.png";
		}
		return null;
	}

	private String bildName(String spielfeld) {
		if (spielfeld.startsWith("E")) {

			return "Ebene2Klein.png";
		}
		if (spielfeld.startsWith("S")) {
			return "SumpfSchwarzWeiss2Klein.png";
		}
		if (spielfeld.startsWith("B")) {

			return "BergKlein.png";
		}
		throw new RuntimeException("Kein passender Pfad konnte gefunden werden");
	}

	public HashMap<String, String> getSpielfeldDaten() {
		HashMap<String, String> spielfeldDaten = new HashMap<String, String>();
		for (int i = 0; i < spielbrett.getY(); i++) {
			for (int j = 0; j < spielbrett.getX(); j++) {

				spielfeldDaten.put((i + 1) + ";" + (j + 1), bildName(spielbrett
						.getSpielfeld(new Position(j + 1, i + 1)).toString()));
			}
		}
		spielfeldDaten.put("Breite", Integer.toString(spielbrett.getX()));
		spielfeldDaten.put("Länge", Integer.toString(spielbrett.getY()));
		spielfeldDaten.put("BergPfad", getBildPfad("Berg"));
		spielfeldDaten.put("EbenePfad", getBildPfad("Ebene"));
		spielfeldDaten.put("SumpfPfad", getBildPfad("Sumpf"));
		return spielfeldDaten;
	}
	public int getanzSpielfelder(){
		int anzfelder=spielbrett.getX()*spielbrett.getY();
		return anzfelder;
	}
	public int getanzFiguren(){
		int anzahlfiguren=figuren.size();
		return anzahlfiguren;
	}
	public void setMeldung(String meldung){
		Meldungen.add(meldung);
	}
	public ArrayList<String> getMeldungen() {
		return Meldungen;
	}
}
