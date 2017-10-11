package rollenspielInterface;

import java.io.IOException;
import java.util.HashMap;

import game.character.Figur;
import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.objekte.Gegenstand;
import game.objekte.Objekte;
import game.speicherung.DateiArten;

public interface iWoMKISpiel{
	public void neuerSpieler(Rollenfiguren charakter,String name)throws SpielerException;
	
	public Figur[] bewegenOben();
	public Figur[] bewegenUnten();
	public Figur[] bewegenRechts();
	public Figur[] bewegenLinks();
	public boolean erholen();
	
	public boolean fallenlassen(Objekte objekt);
	public boolean fallenlassen(Gegenstand gegenstand);
	
	public void speichern(String dateiName,DateiArten art)throws IOException;
	public WorldOfMKIBean laden(String dateiName,DateiArten art)throws IOException;
	
	public HashMap<String,String> getSpielfeldDaten();
	public HashMap<String,String> getAktiverSpielerDaten();
	public HashMap<String,HashMap<String,String>> getSpielerDaten();
	
	public int getRunde();
	public String aktiverSpieler();
}
