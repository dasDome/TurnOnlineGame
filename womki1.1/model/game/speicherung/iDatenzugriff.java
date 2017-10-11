package game.speicherung;

import java.io.IOException;

import game.management.WorldOfMKIBean;

public interface iDatenzugriff {
	public void speichern(String dateiName,WorldOfMKIBean spiel)throws IOException;
	public WorldOfMKIBean laden(String dateiName)throws IOException;
}
