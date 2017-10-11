package game.speicherung;


import java.io.IOException;

import game.basis.Position;
import game.management.WorldOfMKIBean;

public class TestCSVDatei {
	public static void main(String[] args) {
		CSVDatei csv= new CSVDatei();
		WorldOfMKIBean game= new WorldOfMKIBean(2,2);
		
		for(int i=0;i<game.getSpielbrett().getX();i++){
			for(int j=0;j<game.getSpielbrett().getY();j++){
				System.out.println(game.getSpielbrett().getSpielfeld(new Position(i+1,j+1)));
			}
		}try{
		csv.speichern("Csv.csv", game);
		
		System.out.println("speichern erfolgreich");
		WorldOfMKIBean spiel= csv.laden("Csv.csv");
		System.out.println("laden erfolgreich");
		for(int i=0;i<spiel.getSpielbrett().getX();i++){
			for(int j=0;j<spiel.getSpielbrett().getY();j++){
				System.out.println(spiel.getSpielbrett().getSpielfeld(new Position(i+1,j+1)));
			}
		}
		}catch(IOException e){
			System.err.print("Fehler: "+e.getMessage());
		}
	}
}
