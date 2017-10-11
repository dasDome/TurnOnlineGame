package game.Pdf;

import game.basis.*;

import game.character.*;
import game.management.WorldOfMKIBean;
import game.objekte.Gegenstand;
import game.speicherung.iDatenzugriff;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfDatei implements iDatenzugriff {
	//Properties
//Document erzeugen und Titel und Tabellen hinzufügen
	public void speichern(String dateiName,WorldOfMKIBean spiel)throws IOException {
		Document document=null;
		try{
		document = new Document();
		if(dateiName.endsWith("pdf")){
			PdfWriter.getInstance(document, new FileOutputStream(dateiName));
		}else{
		PdfWriter.getInstance(document, new FileOutputStream(dateiName+".pdf"));
		}
		document.open();
		Paragraph titel= new Paragraph("World Of MKI");
		titel.setAlignment(Element.ALIGN_CENTER);
		titel.setSpacingAfter(10);
		document.add(titel);
		//Karte hinzufügen
		document.add(KarteErzeugen(arrayUmsortieren(spiel.getSpielbrett())));
		//neue Seite für Figuren erzeugen
		document.newPage();
		//Figurenbeschreibung hinzufügen
		document.add(new Paragraph("Figuren"));
		for(int i=0;i<spiel.getFiguren().size();i++){
			document.add(figurenBeschreiben(spiel.getFiguren().get(i)));
			PdfPTable table=(figurenItems(spiel.getFiguren().get(i)));
			if(table!=null){
				document.add(table);
			}
			document.add(new Paragraph(" "));
		}
		document.close();
	
	}catch(DocumentException e){
		throw new IOException(e.getMessage());
	}catch(MalformedURLException e){
		throw new IOException(e.getMessage());
	}finally{
		try{
		document.close();
		}catch(Exception e){
			throw new IOException("Fehler beimSchließen der Datei");
		}
	}
	}
	public WorldOfMKIBean laden(String dateiName)throws IOException{
		throw new IOException("Kein Laden möglich");
	}

	private Spielfeld[][] arrayUmsortieren(Spielbrett spielbrett) {
//		Spielfeld[][] array = new Spielfeld[spielbrett.getX()][spielbrett.getY()];
		Spielfeld[][] array = new Spielfeld[spielbrett.getY()][spielbrett.getX()];
		int yWert = 0;
		int xWert = 0;
		int s=spielbrett.getX();
		System.out.println(s);
		if(spielbrett.getX()==1){
			for(int i=0;i<spielbrett.getY();i++){
				array[0][i]=spielbrett.getSpielfeld(new Position(1,i+1));
			}
			return array;
		}
		if(spielbrett.getY()==1){
			for(int i=0;i<spielbrett.getX();i++){
				array[i][0]=spielbrett.getSpielfeld(new Position(i+1,1));
			}
			return array;
		}
		for (int j = spielbrett.getY() - 1; j >= 0; j--) {
			for (int i = 0; i < spielbrett.getX(); i++) {
				
				array[xWert][yWert++] = spielbrett.getSpielfeld(new Position(i + 1, j + 1));
				
			}
			yWert = 0;
			xWert++;
		}
		return array;
	}


	private PdfPTable KarteErzeugen(Spielfeld[][] array)throws BadElementException, MalformedURLException, IOException {
		
		PdfPTable karte = tabelleErzeugen(array.length);
		karte.getDefaultCell().setRowspan(2);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				PdfPCell cell= new PdfPCell();
			
				if (array[i][j] instanceof Berg) {
					cell.addElement(Image.getInstance(("src/game/Pdf/BergeSchwarzWeiss.png")));
					if(array[i][j].getFigur()!=null){
						cell.addElement(bildZuordnen(array[i][j].getFigur()));
					}
				} else {
					if (array[i][j] instanceof Sumpf) {
						cell.addElement(Image.getInstance("src/game/Pdf/SumpfSchwarzWeiss.png"));
						if(array[i][j].getFigur()!=null){
							cell.addElement(bildZuordnen(array[i][j].getFigur()));
						}
						
					} else {
						cell.addElement(Image.getInstance("src/game/Pdf/Ebene2.png"));
						if(array[i][j].getFigur()!=null){
							cell.addElement(Image.getInstance(bildZuordnen(array[i][j].getFigur())));
						}
					}
				}
				karte.addCell(cell);
			}
		}
		return karte;
	}
	private Image bildZuordnen(Figur figur) throws BadElementException, MalformedURLException, IOException{
		
		if(figur instanceof Zwerg){
			return Image.getInstance("src/game/Pdf/Zwerg.png");
		}
		if(figur instanceof Mensch){
			return Image.getInstance("src/game/Pdf/Mensch.png");
		}else{
			return Image.getInstance("src/game/Pdf/Ork.png");
		}
	}

	private  PdfPTable tabelleErzeugen( int zeile) {
		PdfPTable table = new PdfPTable(zeile);
		return table;
	}
	private PdfPTable figurenItems(Figur figur){
		ArrayList<Gegenstand> items= figur.getGegenstände();
		if(items.size()>0){
			PdfPTable table= new PdfPTable(items.size());

		for(int i=0;i<items.size();i++){
			table.addCell(new Paragraph(items.get(i).toString()));
			
		}
		
		return table;
		}else{
			return null;
		}
	}

	private PdfPTable figurenBeschreiben(Figur figur)throws BadElementException, MalformedURLException, IOException {
		
		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = new PdfPCell();
		PdfPCell bild = new PdfPCell();
		
		if(figur instanceof Mensch){
			cell=figurenAttribute(10,10,11,3,figur);
			bild.addElement(Image.getInstance("src/game/Pdf/Mensch.png"));
		}
		if(figur instanceof Zwerg){
			cell=figurenAttribute(12,10,10,2,figur);
			bild.addElement(Image.getInstance("src/game/Pdf/Zwerg.png"));
		}
		if(figur instanceof Ork){
			cell= figurenAttribute(11,10,10,3,figur);
			bild.addElement(Image.getInstance("src/game/Pdf/Ork.png"));
		}
		table.addCell(bild);
		table.addCell(cell);

		return table;

		
	}
	private PdfPCell figurenAttribute(int angriff, int gesundheit,int verteidigung,int bewegung,Figur figur){
		PdfPCell cell= new PdfPCell();
		cell.addElement(new Paragraph("Spieler "+figur.getID()+":"));
		cell.addElement(new Paragraph("Gesundheit:"+String.valueOf(figur.getGesundheit()) + "/"+gesundheit));
		cell.addElement(new Paragraph("Verteidigung:"+String.valueOf(figur.getVerteidigung())+"/"+verteidigung));
		cell.addElement(new Paragraph("Bewegung:"+String.valueOf(figur.getBewegung())+"/"+bewegung));
		cell.addElement(new Paragraph("Angriff:"+String.valueOf(figur.getAngriff())+"/"+angriff));
		return cell;
	}
}
