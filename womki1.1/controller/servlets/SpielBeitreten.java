package servlets;

import game.management.WorldOfMKIBean;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SpielBeitreten")
public class SpielBeitreten extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SpielBeitreten() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).getServletContext();

		int nummer = Integer.valueOf(request.getParameter("spielauswahl"));
		WorldOfMKIBean game = (WorldOfMKIBean)request.getSession(true).getServletContext().getAttribute("Spiel"+nummer);
		request.getSession().setAttribute("spielId",nummer);
//		Aktualisieren.aktualisieren(game, request);
		
		ServletContext con=request.getSession().getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher("/NeuerSpieler.jsp");
		rq.forward(request, response);
		
	}

}
