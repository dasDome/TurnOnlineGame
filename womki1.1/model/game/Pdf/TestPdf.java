package game.Pdf;


import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.speicherung.DateiArten;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.DocumentException;

public class TestPdf {
	public static void main(String[] args) throws FileNotFoundException, DocumentException{
	

		PdfDatei2 pdf=new PdfDatei2();

		WorldOfMKIBean game= new WorldOfMKIBean(1,7);
		try{
		game.neuerSpieler(Rollenfiguren.Mensch,"Boromir");
		game.neuerSpieler(Rollenfiguren.Zwerg,"Gimli");
		game.neuerSpieler(Rollenfiguren.Ork,"Boromir");
		game.neuerSpieler(Rollenfiguren.Mensch,"Boromir");
		game.neuerSpieler(Rollenfiguren.Ork,"Boromir");
		}catch(SpielerException e){
			System.out.println(e.getMessage());
		}
		for(int i=0;i<game.getFiguren().size();i++){
			System.out.println(game.getFiguren().get(i));
		}try{
			game.speichern("TestPdf2", DateiArten.pdf);
//			pdf.speichern( "Spiel.pdf",);
		}catch(IOException e){
			System.err.println("Fehler: "+e.getMessage());
		}
	}
}
