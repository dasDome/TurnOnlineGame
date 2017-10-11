package servlets;

import game.management.WorldOfMKIBean;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/KarteServlet")
public class KarteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public KarteServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object spiel=request.getSession(true).getServletContext().getAttribute("aktuellesSpiel");
		if(spiel==null){
			//Neues Spiel erzeugen
			return;
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
		HashMap<String,String> spielbrettDaten=game.getSpielfeldDaten();
		int x=Integer.valueOf(spielbrettDaten.get("Breite"));
		int y=Integer.valueOf(spielbrettDaten.get("Länge"));
		HashMap<String,HashMap<String,String>> spielerDaten=game.getSpielerDaten();
		for(int i=1;i<=y;i++){
			for(int j=1;j<=x;j++){
				request.setAttribute("feld "+i+"|"+j, "Images/"+spielbrettDaten.get(y+";"+x));
			}
		}
		int anzahlSpieler=spielerDaten.keySet().size();
		for(int i=0;i<anzahlSpieler;i++){
			HashMap<String,String> figurDaten=spielerDaten.get("figur "+i);
			request.setAttribute("feld "+figurDaten.get("positionY")+"|"+figurDaten.get("positionX"), zuordnenBild(i+1,game, request));
		}
	}
	private String zuordnenBild(int figurenid,WorldOfMKIBean game, HttpServletRequest request){
		HashMap<String,HashMap<String,String>> daten=game.getSpielerDaten();
		HashMap<String,String> figurDaten=daten.get("figur "+(figurenid-1));
		String pfad= figurDaten.get("pfad");
		
		System.out.println("bla"+request.getSession().getAttribute("dran"));
		/*if(pfad.contains("Ork")){
			return "Images/OrkKlein.png";
		}
		if(pfad.contains("Mensch")){
			return "Images/MenschKlein.png";
		}
		if(pfad.contains("Zwerg")){
			return "Images/ZwergKlein.png";
		}*/
		return null;
	}

}
