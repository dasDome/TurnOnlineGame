package game.speicherung;

import game.character.Figur;

import java.io.IOException;

public interface iDatenzugriff4 {

	public void speichern(String dateiName, Figur figur) throws IOException;

	public Figur laden(String dateiName) throws IOException;

}
