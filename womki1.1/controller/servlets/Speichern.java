package servlets;

import game.character.Figur;
import game.management.WorldOfMKIBean;
import game.speicherung.DateiArten;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/PdfSpeichern")
public class Speichern extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Speichern() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=(int)request.getSession(true).getAttribute("spielId");
		Object spiel=request.getSession(true).getServletContext().getAttribute("Spiel"+id);
		if(spiel ==null){
			//neues Spiel erzeugen
			//return;
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
		String s=(String)request.getParameter("dateiname");
		//System.out.println(s);
		String test= "D:\\Uni\\Inf2\\praktikum\\womki1.1\\";
		/*ServletContext ctx = this.getServletContext();
		String test = ctx.getRealPath("")+"\\saves\\";
		test.replace(".metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\WorldOfMKIweb-1.3", "WorldOfMKIweb-1.3");
		System.out.println(test);*/
		//String test = "D:\\Uni\\"; 
		s = test+s;
		//System.out.println(s);
		String dateiart=request.getParameter("Speichernmethode");
		try{
			
			
			if(dateiart.equals("pdf")){
			game.speichern(s, DateiArten.pdf);
			System.out.println("pdf");
			}
			if(dateiart.equals("ser")){
				Figur f=(Figur)request.getSession().getAttribute("figur");
				game.speichernFigur2(s, DateiArten.ser,f.getName());
				
			}
			if(dateiart.equals("csv")){
				String pfad= "D:\\Uni\\Inf2\\praktikum\\womki1.1\\";
				String file=(String)request.getParameter("dateiname");
				game.speichern(pfad+file, DateiArten.csv);
				
			}
			if(dateiart.equals("xml")){
				Figur f=(Figur)request.getSession().getAttribute("figur");
				game.speichernFigur(s, DateiArten.xml, f.getName());
				
			}
			
			
			
		}finally{

		Aktualisieren.aktualisieren(game, request);
		ServletContext con=getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher("/Hauptseite.jsp") ;
		rq.forward(request, response);}
	}

}
