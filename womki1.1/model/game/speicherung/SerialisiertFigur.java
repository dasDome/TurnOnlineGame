package game.speicherung;

import game.character.*;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerialisiertFigur implements iDatenzugriff4,Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public void speichern(String dateiName, Figur figur)throws IOException {
		ObjectOutputStream oos = null;
		try {
			if(dateiName.endsWith("ser")){
				oos= new ObjectOutputStream(new FileOutputStream(dateiName));
			}else{
				oos = new ObjectOutputStream(new FileOutputStream(dateiName+".ser"));
			}
			oos.writeObject(figur);
		} catch (FileNotFoundException e) {
			throw new IOException("Konnte " + dateiName + " nicht erzeugen");
		} finally {
			oos.close();
		}
	}

	@Override
	public Figur laden(String dateiName) throws IOException {
		ObjectInputStream ois = null;
		String s="D:\\Uni\\Inf2\\praktikum\\womki1.1\\";//WebContent\\saves\\
		
		try {
			System.out.println("in SerialisiertFigur"+s+dateiName);
			ois = new ObjectInputStream(new FileInputStream(s+dateiName));
			
			Object ausgelesenesObject=ois.readObject();
			if (ausgelesenesObject instanceof Figur) {	
				System.out.println("erfolgreich geladen!");
				return (Figur)ausgelesenesObject;	
			} else {
				throw new IOException(
						"Aus Datei kann keine Figur erzeugt werden");
			}
			
		} catch (FileNotFoundException e) {
			throw new IOException("Datei konnte nicht gefunden werden");
		} catch (ClassNotFoundException e) {
			throw new IOException(
					"Klasse eines Objektes konnte nicht gefunden werden");
		} finally {
			try {
				if(ois!=null){
				ois.close();}
			} catch (Exception e) {
				throw new IOException("Fehler beim Schlieﬂen der Datei");
			}
		}

	}
	
	
	
}
