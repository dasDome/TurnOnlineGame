package servlets;

import game.management.WorldOfMKIBean;
import game.speicherung.CSVDatei;
import game.speicherung.iDatenzugriff;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class KarteLaden
 */
@WebServlet("/KarteLaden")
public class KarteLadenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private iDatenzugriff datenzugriff;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KarteLadenServlet() {
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
		System.out.println("laden. please wait...");
		String datei=request.getParameter("csv");
		System.out.println(datei);
		CSVDatei csv= new CSVDatei();

		WorldOfMKIBean spiel= csv.laden(datei);
		System.out.println(spiel.getSpielbrett());
		System.out.println("Datei geladen");
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
		request.getSession(true).getServletContext().setAttribute("Spiel"+spielID,spiel);
		
		KarteAktualisieren2.aktualisierenKarte(spiel, request);
		//ServletContext con=request.getSession().getServletContext();

		response.sendRedirect("/womki1.1/NeuerSpieler.jsp");
//		}
//		catch(Exception e){
//			System.out.println("Laden fehlgeschlagen   "+e.getMessage());
//		}
		
		
		
	}

}
