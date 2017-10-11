package servlets;


import game.character.Figur;
import game.management.WorldOfMKIBean;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/NewGame")
public class NewGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public NewGame() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession(true).getServletContext();
		int breite=Integer.valueOf(request.getParameter("breite"));
		int länge=Integer.valueOf(request.getParameter("länge"));
		
		WorldOfMKIBean game= new WorldOfMKIBean(länge,breite);
		
		Object id=request.getSession().getServletContext().getAttribute("anzahlSpiele");
		int spielID;
		if(id==null){
			spielID=1;
		}else{
			spielID=(int)id;
			spielID++;
			
		}
		request.getSession().getServletContext().setAttribute("anzahlSpiele",spielID);
		
		request.getSession().setAttribute("spielId",spielID);
		request.getSession(true).getServletContext().setAttribute("Spiel"+spielID,game);
		KarteAktualisieren2.aktualisierenKarte(game, request);
		ServletContext con=request.getSession().getServletContext();

		response.sendRedirect("/womki1.1/NeuerSpieler.jsp");

		
	}

}
