package servlets;

import game.character.Figur;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.objekte.Kurzschwert;
import game.objekte.Langschwert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NeuerSpielerServlet")
public class NeuerSpielerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NeuerSpielerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goTo = "/Hauptseite.jsp";
		String name=request.getParameter("spielerName");
		if(name==null || name.length()<=1){     
			goTo="/name.jsp";
		}
		String art=request.getParameter("spielerArt");		
		Rollenfiguren rollenfigur=null;
		for(int i=0;i<Rollenfiguren.values().length;i++){
			if(art.equals(Rollenfiguren.values()[i].toString())){
				rollenfigur=Rollenfiguren.values()[i];
			}
		}
		int id=(int)request.getSession().getAttribute("spielId");
		WorldOfMKIBean game=(WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+id);
		try{
			game.neuerSpieler(rollenfigur, name);
			Figur f=getFigur(game,name);
			goTo = "/Hauptseite.jsp";
			//Alles Aktualliseren
		}catch(Exception e){
			//Fehlermeldung
			goTo="/name.jsp";
		}finally{
			request.getSession().setAttribute("figur",getFigur(game, name));
			KarteAktualisieren2.aktualisierenKarte(game, request);
			ServletContext con = getServletContext();
			RequestDispatcher rq = con.getRequestDispatcher(goTo);
			rq.forward(request, response);
			
		}
		//request.getSession().setAttribute("figur",getFigur(game, name));
		//KarteAktualisieren.aktualisierenKarte(game, request,response);
		
		//response.sendRedirect(goTo);
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
