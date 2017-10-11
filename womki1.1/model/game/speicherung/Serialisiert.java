package game.speicherung;

import java.io.*;

import game.management.WorldOfMKIBean;

public class Serialisiert implements iDatenzugriff, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public void speichern(String dateiName, WorldOfMKIBean spiel)
			throws IOException {
		ObjectOutputStream oos = null;
		try {
			if(dateiName.endsWith("ser")){
				oos= new ObjectOutputStream(new FileOutputStream(dateiName));
			}else{
				oos = new ObjectOutputStream(new FileOutputStream(dateiName+".ser"));
			}
			oos.writeObject(spiel);
		} catch (FileNotFoundException e) {
			throw new IOException("Konnte " + dateiName + " nicht erzeugen");
		} finally {
			oos.close();
		}
	}

	@Override
	public WorldOfMKIBean laden(String dateiName) throws IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(dateiName+".ser"));
			Object ausgelesenesObject=ois.readObject();
			if (ausgelesenesObject instanceof WorldOfMKIBean) {
				return (WorldOfMKIBean)ausgelesenesObject;
			} else {
				throw new IOException(
						"Aus Datei kann kein WorldOFMKI-SPiel erzeugt werden");
			}
		} catch (FileNotFoundException e) {
			throw new IOException("Datei konnte nicht gefunden werden");
		} catch (ClassNotFoundException e) {
			throw new IOException(
					"Klasse eines Objektes konnte nicht gefunden werden");
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				throw new IOException("Fehler beimSchlieﬂen der Datei");
			}
		}

	}

}
