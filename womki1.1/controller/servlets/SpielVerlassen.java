package servlets;

import game.character.Figur;
import game.management.WorldOfMKIBean;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SpielVerlassen")
public class SpielVerlassen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SpielVerlassen() {
		super();

	}

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
		Figur f = (Figur) request.getSession().getAttribute("figur");
		try {
			if (game.getFiguren().contains(f)) {
				Aktualisieren.aktualisieren(game, request);
				game.sterben(f);
				// request.getSession().invalidate();
				System.out.println("in try");
				int anz = (Integer) request.getSession().getServletContext().getAttribute("anzahlSpiele");
				// if(anz==id){
				// request.getSession().getServletContext().setAttribute("anzahlSpiele",null);
				// }
				for (int j = 1; j <= anz; j++) {
					if (j == id) {
						if(game.getanzFiguren()<1){
						request.getSession().getServletContext().setAttribute("Spiel" + j, null);}
					}

				}
			}
		} catch (RuntimeException e) {
			System.out.println("in catch");
			int anz = (Integer) request.getSession().getServletContext().getAttribute("anzahlSpiele");
			// if(anz==id){
			// request.getSession().getServletContext().setAttribute("anzahlSpiele",null);
			// }
			for (int j = 1; j <= anz; j++) {
				System.out.println("in forschleife");
				if (j == id) {
					System.out.println("in if");
					
					request.getSession().getServletContext().setAttribute("Spiel" + id, null);
					request.getSession().getServletContext().setAttribute("anzahlSpiele",anz-1);
					System.out.println("spiel ist auf null");
				}
				

			}
			// request.getSession().getServletContext().setAttribute("anzahlSpiele",null);
			// muss man anderst rauslöschen
		}finally{
			Aktualisieren.aktualisieren(game, request);
			response.sendRedirect("/womki1.1/index.jsp");
		}

		

	}
}
