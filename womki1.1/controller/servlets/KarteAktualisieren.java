package servlets;

import game.management.WorldOfMKIBean;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KarteAktualisieren
 */
@WebServlet("/KarteAktualisieren")
public class KarteAktualisieren extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KarteAktualisieren() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=(int)request.getSession().getAttribute("spielId");
		WorldOfMKIBean game=(WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+id);

		aktualisierenKarte(game,request);
		
		
	}

	
	
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
			request.getSession().getServletContext().setAttribute("feld "+figurDaten.get("positionY")+"|"+figurDaten.get("positionX"), zuordnenBild(i+1,game));
		}

	}


private static String zuordnenBild(int figurenid,WorldOfMKIBean game){
	HashMap<String,HashMap<String,String>> daten=game.getSpielerDaten();
	HashMap<String,String> figurDaten=daten.get("figur "+(figurenid-1));
	String pfad= figurDaten.get("pfad");
	if(pfad.contains("Ork")){
		return "Images/OrkKlein.png";
	}
	if(pfad.contains("Mensch")){
		return "Images/MenschKlein.png";
	}
	if(pfad.contains("Zwerg")){
		return "Images/ZwergKlein.png";
	}
	return null;
}
	
	
	
}
