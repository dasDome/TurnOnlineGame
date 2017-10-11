package game.speicherung;

import java.io.IOException;

import game.basis.Ebene;
import game.character.Figur;
import game.character.Mensch;
import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.objekte.Gegenstand;
import game.objekte.Kurzschwert;
import game.objekte.Schild;

public class TestFigurSerialisiert {

	
	public static void main(String[] args){
		SerialisiertFigur seri=new SerialisiertFigur();
		WorldOfMKIBean game=new WorldOfMKIBean();
		
		//Figur figur=new Mensch(new Ebene(new Spielbrett(),),"Hans");
		Gegenstand g1=new Kurzschwert();
		Gegenstand g2=new Schild();
		
		
		
		try{
			game.neuerSpieler(Rollenfiguren.Mensch, "Hans");
			Figur figur=game.getSpieler();
			figur.add(g1);
			figur.add(g2);
			
			seri.speichern("FigurSer3", figur);
			
			Figur f=seri.laden("FigurSer3");
			System.out.println(f.getName()+" "+f.getRasse()+" "+f.getAngriff()+" "+f.getBewegung()+" "+f.getGesundheit());
		}catch(IOException e){
			System.out.println("fehler "+e.getMessage());
		} catch (SpielerException ex) {
			System.out.println(ex.getMessage());
		}

	}

}
