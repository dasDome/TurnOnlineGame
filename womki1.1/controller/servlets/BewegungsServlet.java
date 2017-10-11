package servlets;

import game.character.Figur;
import game.management.WorldOfMKIBean;

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


@WebServlet("/BewegungsServlet")
public class BewegungsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BewegungsServlet() {
        super();
    }


    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id=(int)request.getSession(true).getAttribute("spielId");
		Object spiel=request.getSession(true).getServletContext().getAttribute("Spiel"+id);
		if(spiel == null){
			System.out.println("Kein Spiel");
			return;
		}
		WorldOfMKIBean game=(WorldOfMKIBean)spiel;
		
    	
    	String oben=request.getParameter("Oben");
    	if(oben!=null){
    		try{
    			Figur[]angriff2=game.bewegenOben();  
    			Aktualisieren.aktualisieren(game, request);
    			
    			request.getSession().getServletContext().setAttribute("bewegenError","false");
    			request.getSession().getServletContext().setAttribute("kampf", angriff2);
    		

    		}catch(Exception e){
    			
    			request.getSession().getServletContext().setAttribute("bewegenError","true");
    			
    		}
    		
    	}
    	
    	String links =request.getParameter("Links");
    	if(links!=null){ 
    		
    		try{
//    			Object[] angriff=
    			Figur[]angriff2=game.bewegenLinks();   
    			Aktualisieren.aktualisieren(game, request);
    			request.getSession().getServletContext().setAttribute("bewegenError","false");
    			request.getSession().getServletContext().setAttribute("kampf", angriff2);
//    			boolean kampf=(boolean)angriff[0];
//    			if(kampf==true){
//    				request.getSession().getServletContext().setAttribute("kampf","true");
//    			}else{
//    				request.getSession().getServletContext().setAttribute("kampf","false");
//    			}
    		}catch(Exception e){
    			request.getSession().getServletContext().setAttribute("bewegenError","true");
    		}
    	
    	//Aktualisieren.aktualisieren(game, request);
    	//request.setAttribute("Bewegung erfolgt", game);
    	}
    	
    	String unten=request.getParameter("Unten");
    	if(unten!=null){
    		try{
//    			Object[] angriff=
    			//System.out.println("test1");
    			Figur[]angriff2=game.bewegenUnten(); 
    			//System.out.println("test2");
    			Aktualisieren.aktualisieren(game, request);
    			//System.out.println("test3");
    			request.getSession().getServletContext().setAttribute("bewegenError","false");
    			request.getSession().getServletContext().setAttribute("kampf", angriff2);
//    			boolean kampf=(boolean)angriff[0];
//    			if(kampf==true){
//    				request.getSession().getServletContext().setAttribute("kampf","true");
//    			}else{
//    				request.getSession().getServletContext().setAttribute("kampf","false");
//    			}
    		}catch(Exception e){
//    			ServletContext con=getServletContext();
//    			RequestDispatcher rq=con.getRequestDispatcher("/Hauptseite.jsp");
//    			rq.forward(request,response);
//    			return;
    			request.getSession().getServletContext().setAttribute("bewegenError","true");
    		}
    	}
    	
    	String rechts=request.getParameter("Rechts");
    	if(rechts!=null){
    		try{
//    			Object[] angriff=
    			Figur[]angriff2=game.bewegenRechts();
    			Aktualisieren.aktualisieren(game, request);
    			request.getSession().getServletContext().setAttribute("bewegenError","false");
    			request.getSession().getServletContext().setAttribute("kampf", angriff2);
//    			boolean kampf=(boolean)angriff[0];
//    			if(kampf==true){
//    				request.getSession().getServletContext().setAttribute("kampf","true");
//    			}else{
//    				request.getSession().getServletContext().setAttribute("kampf","false");
//    			}
    		}catch(Exception e){
    			request.getSession().getServletContext().setAttribute("bewegenError","true");
    		}
    	}
    	
    	String erholen=request.getParameter("Erholen");
    	if(erholen!=null){
    						//müssten wir darauf achten, dass man in einer runde nicht 2x drankommt
    		game.erholen();
    		Aktualisieren.aktualisieren(game, request);
    	}
    	
    	Aktualisieren.aktualisieren(game, request);
    	ServletContext con=getServletContext();
		RequestDispatcher rq=con.getRequestDispatcher("/Hauptseite.jsp");
		rq.forward(request, response);
    	
    }
  
}

    	
