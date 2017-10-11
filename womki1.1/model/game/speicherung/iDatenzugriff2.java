package game.speicherung;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public interface iDatenzugriff2 {
	public void speichern(String dateiName,HashMap<String,HashMap<String,String>> spielerInfo,HashMap<String,String> spielbrett)throws IOException;
	public Properties laden(String dateiName)throws IOException;
}
