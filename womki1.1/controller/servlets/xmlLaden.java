package servlets;

import game.character.Figur;
import game.management.Rollenfiguren;
import game.management.WorldOfMKIBean;
import game.speicherung.DateiArten;
import game.speicherung.iDatenzugriff3;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.api.PropertySet.Property;

/**
 * Servlet implementation class xmlLaden
 */
@WebServlet("/xmlLaden")
public class xmlLaden extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public xmlLaden() {
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
		String goTo = "/Hauptseite.jsp";
		
		
		int id=(int)request.getSession().getAttribute("spielId");
		WorldOfMKIBean game=(WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+id);
		
		String dateiName="D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\saves\\Name2";
		iDatenzugriff3 i=null;
		Properties l=i.laden(dateiName);
		String name= l.getProperty("name");
		request.getSession().setAttribute("figur",getFigur(game, name));
		KarteAktualisieren2.aktualisierenKarte(game, request);
		ServletContext con=getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher(goTo) ;
		rq.forward(request, response);
	}
	private Figur getFigur(WorldOfMKIBean game,String name){
		for(Figur f:game.getFiguren()){
			System.out.println("spieler"+name);
			System.out.println(f.getName());
			if(f.getName().equals(name)){
				return f;
			}
		}return null;
//		
//		throw new RuntimeException("Figur konnte nicht gefunden werden");
	}
}
