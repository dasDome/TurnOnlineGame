package game.speicherung;

import java.io.IOException;

import game.basis.Position;
import game.basis.Spielbrett;
import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;

public class TestSerialisiert {
	public static void main(String[] args) {
		Serialisiert seri = new Serialisiert();
		WorldOfMKIBean game = new WorldOfMKIBean(2, 2);
		try {
			game.neuerSpieler(Rollenfiguren.Mensch,"Boromir");

			seri.speichern("game2.ser", game);
			WorldOfMKIBean spielLaden = seri.laden("game2.ser");

			Spielbrett spielbrett = spielLaden.getSpielbrett();
			spielbrettAusgabe(spielbrett);
			spielbrett = game.getSpielbrett();
			System.out.println();
			spielbrettAusgabe(spielbrett);
			System.out.println(game.getFiguren());
			System.out.println(spielLaden.getFiguren());
		} catch (IOException e) {
			System.err.println("Fehler " + e.getMessage());
		} catch (SpielerException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void spielbrettAusgabe(Spielbrett spielbrett) {
		for (int i = 0; i < spielbrett.getX(); i++) {
			for (int j = 0; j < spielbrett.getY(); j++) {
				System.out.println(spielbrett.getSpielfeld(new Position(i + 1,
						j + 1)));
			}
		}
	}
}
