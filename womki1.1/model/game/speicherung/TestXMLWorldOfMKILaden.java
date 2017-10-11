package game.speicherung;

import java.io.IOException;

import game.exceptions.SpielerException;
import game.management.WorldOfMKIBean;

public class TestXMLWorldOfMKILaden {
public static void main(String[] args){
	WorldOfMKIBean game= new WorldOfMKIBean(3,3);
	try{
	game.ladenFigur("TestFigurSpeichern.xml", DateiArten.xml);
	System.out.println(game.aktiverSpieler());
	}catch(IOException e){
		e.printStackTrace();
	}catch(SpielerException e){
		e.printStackTrace();
	}
}
}
