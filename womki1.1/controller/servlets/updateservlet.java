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


@WebServlet("/updateservlet")
public class updateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public updateservlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id=(int)request.getSession(true).getAttribute("spielId");
		Object spiel=request.getSession(true).getServletContext().getAttribute("Spiel"+id);
		if(spiel == null){
			System.out.println("Kein Spiel");
			return;
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
		Aktualisieren.aktualisieren(game, request);

		ServletContext con=getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher("/Hauptseite.jsp");
		rq.forward(request, response);
		//response.getWriter().print("<script>window.location.href='/womki1.1/Hauptseite.jsp?breiteSpielbrett='request.getParameter(\"breite\")&längeSpielbrett='request.getParameter(\"länge\")'';</script>");
	}

}
