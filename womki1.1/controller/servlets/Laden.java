package servlets;

import game.character.Figur;
import game.character.Mensch;
import game.exceptions.SpielerException;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.speicherung.DateiArten;
import game.speicherung.SerialisiertFigur;
import game.speicherung.XMLDatei;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Laden
 */
@WebServlet("/Laden")
public class Laden extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Laden() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		int id = (int) request.getSession(true).getAttribute("spielId");
		Object spiel = request.getSession(true).getServletContext().getAttribute("Spiel" + id);
		if (spiel == null) {
			// neues Spiel erzeugen
			// return;
		}
		WorldOfMKIBean game = (WorldOfMKIBean) spiel;
		String dateiname = (String) request.getParameter("dateien");
		
		
		if (dateiname.endsWith("ser")) { 
			try {
				//boolean anz = false;
				//System.out.println("in try ser");
				Figur f=game.ladenFigur2(dateiname, DateiArten.ser);
				//System.out.println(f.getName());
				/*String name = f.getName();
				if(game.getFiguren().size()>0){
				for(int durchlaufspieler = 0; durchlaufspieler < game.getanzFiguren();durchlaufspieler++){
					if((game.getFiguren().get(durchlaufspieler).getName().equals(name))){
						if(anz == false){
							anz=true;
						}else{
							System.out.println("Figur bereits geladen");
						}
						
					}
				}	}*/
				
				
				
				request.getSession().setAttribute("figur",f);
				//System.out.println(request.getSession().getAttribute("figur"));
				KarteAktualisieren2.aktualisierenKarte(game, request);
			} catch (SpielerException e) {
				System.out.println(e.getMessage());
				//System.out.println("catch ser");
			}

			}

		
		if(dateiname.endsWith("xml")){
		System.out.println("in if xml");
			try {
				//System.out.println("in try xml");
				//game.ladenFigur(dateiname, DateiArten.xml);
				Figur f=game.ladenFigur(dateiname, DateiArten.xml);
				request.getSession().setAttribute("figur",f);
				KarteAktualisieren2.aktualisierenKarte(game, request);
				//System.out.println("in Laden: in try laden xml");
			} catch (SpielerException e) {
				System.out.println(e.getMessage());
				//System.out.println("catch xml");
			}
			
			
		}
		
		if(!(dateiname.contains("xml")||dateiname.contains("ser"))){
			System.out.println("Falsches Dateiformat. Datei kann nicht geladen werden.");
		}
		
		request.getSession().getServletContext().setAttribute("currentgame",dateiname);
		String loadedgames = (String)request.getSession().getServletContext().getAttribute("loadedgames");
		if(loadedgames == null){
			request.getSession().getServletContext().setAttribute("loadedgames", dateiname);
		}else{
			request.getSession().getServletContext().setAttribute("loadedgames", loadedgames+","+dateiname);
		}
		


		
		Aktualisieren.aktualisieren(game, request);
		ServletContext con = getServletContext();
		RequestDispatcher rq = con.getRequestDispatcher("/Hauptseite.jsp");
		rq.forward(request, response);
//		response.sendRedirect("/womki1.1/Hauptseite.jsp");
		
	}
	
	private Figur getFigur(WorldOfMKIBean game,String name){
		for(Figur f:game.getFiguren()){
			//System.out.println("spieler"+name);
			//System.out.println(f.getName());
			if(f.getName().equals(name)){
				return f;
			}
		}return null;

	}

}
