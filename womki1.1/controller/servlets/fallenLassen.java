package servlets;

import game.management.WorldOfMKIBean;
import game.objekte.Gegenstand;
import game.objekte.Objekte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/fallenLassen")
public class fallenLassen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public fallenLassen() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Object spiel=request.getSession(true).getServletContext().getAttribute("aktuellesSpiel");
//		if(spiel ==null){
//			//neues Spiel erzeugen
//			//return;
//		}
//		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
//		ArrayList<String> gegenstände=new ArrayList<String>();
//		HashMap<String,String> Spielerdaten=game.getAktiverSpielerDaten();
//		if(Spielerdaten.containsValue("gegenstände")){
//			String s=Spielerdaten.get("gegenstände");
//			String[] all=s.split(";");
//			for(int i=0;i<all.length;i++){
//				//String a=p[i];						//muss ich überhaupt casten?
//				//Gegenstand f=Gegenstand.parse(a);
//				gegenstände.add(all[i]);
//			}
//			int size=gegenstände.size();
//			request.setAttribute("size",size);
//			for(int j=0;j<gegenstände.size();j++){
//				String einzeln=gegenstände.get(j);
//				request.setAttribute("gegenstand"+j, einzeln);
//			}
//		}
		
		int id=(int)request.getSession(true).getAttribute("spielId");
		Object spiel=request.getSession(true).getServletContext().getAttribute("Spiel"+id);
		if(spiel ==null){
			System.out.println("Kein Spiel");
			return;
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
//		Object p=request.getAttribute("Ausrüstung");	// Obtion value
		String item=request.getParameter("Ausrüstung");
		for(int i=0;i<Objekte.values().length;i++){
			if(item.equals(Objekte.values()[i].toString())){
//				try{
				game.fallenlassen(Objekte.values()[i]);
				HashMap<String,String> daten=game.getAktiverSpielerDaten();
//				}catch(Exception e){
//					System.out.println("Fail");
//					//Fehlermeldung
//				}
			}
		}
		Aktualisieren.aktualisieren(game, request);
		ServletContext con=getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher("/Hauptseite.jsp");
		rq.forward(request, response);
	}

	public void fallenlassen(HttpServletRequest request){
		
		int id=(int)request.getSession().getAttribute("spielId");
		Object spiel=request.getSession(true).getServletContext().getAttribute("Spiel"+id);
		if(spiel ==null){
	
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
		Object p=request.getAttribute("Ausrüstung");				// Obtion value
		game.fallenlassen((Objekte)p);
	}
	

	
	
	
	
}
