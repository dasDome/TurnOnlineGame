package game.speicherung;

import java.io.*;
import java.util.ArrayList;

import game.basis.*;
import game.management.WorldOfMKIBean;

public class CSVDatei implements iDatenzugriff {
	
	public void speichern(String dateiName, WorldOfMKIBean game)
			throws IOException {
		Spielbrett spielbrett = game.getSpielbrett();
		PrintWriter pw = null;
		try {
			
			if(dateiName.endsWith("csv")){
				
				pw= new PrintWriter(new FileWriter(dateiName));
			}else{
				pw = new PrintWriter(new FileWriter(dateiName+".csv"));
			}
			for (int i = spielbrett.getY() - 1; i >= 0; i--) {
				for (int j = 0; j < spielbrett.getX(); j++) {
					Spielfeld spielfeld = spielbrett.getSpielfeld(new Position(
							j + 1, i + 1));
					if (spielfeld instanceof Berg) {
						pw.print("Berg" + ";");
					} else {
						if (spielfeld instanceof Sumpf) {
							pw.print("Sumpf" + ";");
						} else {
							pw.print("Ebene" + ";");
						}
					}
				}
				pw.println();
				pw.flush();
			}
		} catch (FileNotFoundException e) {
			throw new IOException("Datei " + dateiName
					+ " konnte nicht gefunden werden");
		} finally {
			try {if(pw!=null){
				pw.close();}
			} catch (Exception e) {
				throw new IOException("Fehler beim Schließen der Datei");
			}
		}

	}

	public WorldOfMKIBean laden(String dateiName) throws IOException {
		BufferedReader reader = null;
		try {
			String pfad="D:\\Uni\\Inf2\\praktikum\\womki1.1\\";
			reader = new BufferedReader(new FileReader(pfad+dateiName));//+".csv"));
			System.out.println(pfad+dateiName+".csv");
			String line = reader.readLine();
			ArrayList<String> feldarten = new ArrayList<String>();
			int zeile = 0;
			int spalte = 0;
			String[] feld;
			while (line != null) {
				feld = line.split(";");
				spalte = feld.length;
				for (String s : feld) {
					feldarten.add(s);
				}
				zeile++;
				line = reader.readLine();
			}
			int x = 1;
			int y = zeile;
			Spielbrett spielbrett = new Spielbrett(spalte, zeile);
			for (String s : feldarten) {

				if (x > spalte) {
					x = 1;
					y--;
				}
				if (y == 0) {
					return new WorldOfMKIBean(spielbrett);
				}
				if (s.contentEquals("Berg")) {
					spielbrett.setSpielfeld(new Position(x, y), new Berg(
							spielbrett, new Position(x++, y)));
				} else {
					if (s.equals("Sumpf")) {
						spielbrett.setSpielfeld(new Position(x, y), new Sumpf(
								spielbrett, new Position(x++, y)));
					} else {
						if (s.equals("Ebene")) {
							spielbrett
									.setSpielfeld(new Position(x, y),
											new Ebene(spielbrett, new Position(
													x++, y)));
						} else {
							throw new IOException(
									"Datei kann nicht gelesen werden");
						}
					}
				}

			}
			return new WorldOfMKIBean(spielbrett);

		} catch (FileNotFoundException e) {
			throw new IOException("Datei " + dateiName
					+ " konnte nicht gefunden werden");
		} finally {
			if(reader!=null){
			reader.close();}
		}

	}
}
