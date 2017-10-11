package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.character.Figur;
import game.management.WorldOfMKIBean;

public class KarteAktualisieren2 {
	public static void aktualisierenKarte(WorldOfMKIBean game,HttpServletRequest request){
		HashMap<String,String> spielbrettDaten=game.getSpielfeldDaten();
		int x=Integer.valueOf(spielbrettDaten.get("Breite"));
		int y=Integer.valueOf(spielbrettDaten.get("Länge"));
		request.setAttribute("längeSpielbrett",y);
		request.setAttribute("breiteSpielbrett", x);
		HashMap<String,HashMap<String,String>> spielerDaten=game.getSpielerDaten();
		for(int i=1;i<=y;i++){
			for(int j=1;j<=x;j++){
				request.getSession().getServletContext().setAttribute("feld "+i+"|"+j, "Images/"+spielbrettDaten.get(i+";"+j));
			}
		}
		int anzahlSpieler=spielerDaten.keySet().size();
		for(int i=0;i<anzahlSpieler;i++){
			HashMap<String,String> figurDaten=spielerDaten.get("figur "+i);
			request.getSession().getServletContext().setAttribute("feld "+figurDaten.get("positionY")+"|"+figurDaten.get("positionX"), zuordnenBild(i+1,game, request));
		}

	}


private static String zuordnenBild(int figurenid,WorldOfMKIBean game, HttpServletRequest request){
	
	HashMap<String,HashMap<String,String>> daten=game.getSpielerDaten();
	HashMap<String,String> figurDaten=daten.get("figur "+(figurenid-1));
	String pfad= figurDaten.get("pfad");
	Figur f=(Figur)request.getSession(true).getAttribute("figur");


	
			if(pfad.contains("Ork")){
				if((f.getRasse().equals("Ork")) && (f.getName().equals(figurDaten.get("name")))){
				return "Images/ork.jpg";}
				else{
					return "Images/OrkKlein.png";
				}
			}
			
			if(pfad.contains("Mensch")){
				if(f.getRasse().equals("Mensch")&& (f.getName().equals(figurDaten.get("name")))){
				return "Images/mensch.jpg";}
				else{
					return "Images/MenschKlein.png";
				}
			}
			if(pfad.contains("Zwerg")){
				if(f.getRasse().equals("Zwerg")&& (f.getName().equals(figurDaten.get("name")))){
				return "Images/zwerg.jpg";}
				else{
					return "Images/ZwergKlein.png";
				}
			}
		
			return null;}
	
	
	
	
	/*if(pfad.contains("Ork")){
		return "Images/OrkKlein.png";
	}
	if(pfad.contains("Mensch")){
		return "Images/MenschKlein.png";
	}
	if(pfad.contains("Zwerg")){
		return "Images/ZwergKlein.png";
	}*/
	

}
