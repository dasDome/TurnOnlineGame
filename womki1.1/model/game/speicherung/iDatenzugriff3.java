package game.speicherung;

import java.io.IOException;
import java.util.Properties;

import game.management.WorldOfMKIBean;

public interface iDatenzugriff3 {
	public void speichern(String dateiName,Properties p)throws IOException;
	public Properties laden(String dateiName)throws IOException;
}
