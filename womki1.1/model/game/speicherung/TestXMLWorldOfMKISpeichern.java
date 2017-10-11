package game.speicherung;

import java.io.IOException;

import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;

public class TestXMLWorldOfMKISpeichern {
public static void main(String[] args){
	WorldOfMKIBean game= new WorldOfMKIBean(4,4);
	try{
	game.neuerSpieler(Rollenfiguren.Ork, "Gollum");
	game.speichernFigur("TestFigurSpeichern.xml", DateiArten.xml, "Gollum");
		
	}catch(SpielerException e){
		e.printStackTrace();
	}catch(IOException e){
		e.printStackTrace();
	}
}
}
