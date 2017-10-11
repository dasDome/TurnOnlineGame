package game.Pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Properties;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import game.speicherung.iDatenzugriff2;

public class PdfDatei2 implements iDatenzugriff2 {
	private HashMap<String, HashMap<String, String>> spielerInfo;
	private HashMap<String, String> spielfeldDaten;
	private String dateiName;

	@Override
	public void speichern(String dateiName,HashMap<String,HashMap<String,String>> spielerInfo,HashMap<String,String> spielbrett)throws IOException{
		setDateiName(dateiName);
		setSpielerInfo(spielerInfo);
		setSpielbrettInfos(spielbrett);
		Document document=null;
		try{
			document=new Document();
			if(dateiName.endsWith("pdf")){
				PdfWriter.getInstance(document, new FileOutputStream(dateiName));
			}else{
				PdfWriter.getInstance(document, new FileOutputStream(dateiName+".pdf"));
			}
			document.open();
			Paragraph titel= new Paragraph("WORLD Of MKI");
			titel.setAlignment(Element.ALIGN_CENTER);
			titel.setSpacingAfter(10);
			document.add(titel);
			document.add(karteErzeugen());
			document.newPage();
			document.add(new Paragraph("Figuren"));
			for(String key:spielerInfo.keySet()){
				document.add(figurenBeschreiben(key));
				PdfPTable table=(figurenItems(key));
				if(table!=null){
					document.add(table);
				}
				document.add(new Paragraph(" "));
			}
			document.close();
		}catch(DocumentException e){
			throw new IOException(e.getMessage());
		}catch(FileNotFoundException e){
			throw new IOException(e.getMessage());
		}
				
	}
	public void setDateiName(String dateiName)throws IOException{
		if(dateiName!=null && dateiName.length()>0){
			this.dateiName=dateiName;
		}else{
			throw new IOException("Ungültiger Dateipfad");
		}
	}
	public void setSpielerInfo(HashMap<String,HashMap<String,String>>spielerInfo)throws IOException{
		if(spielerInfo!=null){
			this.spielerInfo=spielerInfo;
		}else{
			throw new IOException("Keine SpeicherDaten zu den Spielern");
		}
	}
	public void setSpielbrettInfos(HashMap<String,String> spielbrettInfo)throws IOException{
		if(spielbrettInfo!=null){
		this.spielfeldDaten=spielbrettInfo;
		}else{
			throw new IOException("Keine SpeicherDaten zum Spielbrett");
		}
	}
	private PdfPTable figurenItems(String FigurKey){
		HashMap<String,String> figurWerte=this.spielerInfo.get(FigurKey);
		String gegenstände=figurWerte.get("gegenstände");
		String[] objekte=gegenstände.split(";");
		if(gegenstände.length()>0){
			PdfPTable table= new PdfPTable(objekte.length);

		for(int i=0;i<objekte.length;i++){
			table.addCell(new Paragraph(objekte[i]));
			
		}
		
		return table;
		}else{
			return null;
		}
	}

	private PdfPTable karteErzeugen() throws IOException {
		int breite = Integer.valueOf((spielfeldDaten.get("Breite")));
		int länge = Integer.valueOf((spielfeldDaten.get("Länge")));
		PdfPTable table = new PdfPTable(breite);
		for (int i = länge; i > 0; i--) {
			for (int j = 1; j <= breite; j++) {
				PdfPCell cell = new PdfPCell();
				String feld = spielfeldDaten.get(i + ";" + j);
				String pfad = null;
				if (feld.startsWith("B")) {
					pfad = spielfeldDaten.get("BergPfad");
				}
				if (feld.startsWith("E")) {
					pfad = spielfeldDaten.get("EbenePfad");
				}
				if (feld.startsWith("S")) {
					pfad = spielfeldDaten.get("SumpfPfad");
				}
				try {
					cell.addElement(Image.getInstance(pfad));
					for (String s : spielerInfo.keySet()) {
						int x = Integer.parseInt(spielerInfo.get(s).get(
								"positionX"));
						int y = Integer.parseInt(spielerInfo.get(s).get(
								"positionY"));

						if (x == j && y == i) {
							String p=spielerInfo.get(s).get("pfad");
							System.out.println(p);
							cell.addElement(Image.getInstance(spielerInfo.get(s).get("pfad")));

						}

					}
					table.addCell(cell);
				} catch (BadElementException e) {
					throw new IOException(e.getMessage());
				} catch (MalformedURLException e) {
					throw new IOException(e.getMessage());
				} catch (IOException e) {
					throw new IOException(e.getMessage());
				}
			}
		}
		return table;
	}
	private PdfPTable figurenBeschreiben(String figurKey)throws BadElementException, MalformedURLException, IOException {
		
		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = new PdfPCell();
		PdfPCell bild = new PdfPCell();
		String art=this.spielerInfo.get(figurKey).get("art");
		if(art.equals("Mensch")){
			cell=figurenAttribute(10,10,11,3, figurKey);
			
		}
		if(art.equals("Zwerg")){
			cell=figurenAttribute(12,10,10,2,figurKey);
			
		}
		if(art.equals("Ork")){
			cell= figurenAttribute(11,10,10,3,figurKey);
			
		}
		bild.addElement(Image.getInstance(this.spielerInfo.get(figurKey).get("pfad")));
		table.addCell(bild);
		table.addCell(cell);

		return table;

		
	}
	private PdfPCell figurenAttribute(int angriff, int gesundheit,int verteidigung,int bewegung,String figurKey){
		PdfPCell cell= new PdfPCell();
		HashMap<String,String> figurenWerte=this.spielerInfo.get(figurKey);
		int a=Integer.valueOf(figurenWerte.get("angriff"));
		int g=Integer.valueOf(figurenWerte.get("gesundheit"));
		int v=Integer.valueOf(figurenWerte.get("verteidigung"));
		int b=Integer.valueOf(figurenWerte.get("bewegung"));
		
		cell.addElement(new Paragraph("Spieler "+figurenWerte.get("name")+":"));
		cell.addElement(new Paragraph("Gesundheit:"+g + "/"+gesundheit));
		cell.addElement(new Paragraph("Verteidigung:"+v+"/"+verteidigung));
		cell.addElement(new Paragraph("Bewegung:"+b+"/"+bewegung));
		cell.addElement(new Paragraph("Angriff:"+a+"/"+angriff));
		return cell;
	}
	@Override
	public Properties laden(String dateiName)throws IOException{
		throw new IOException("Laden nicht möglich");
	}
}
