package servlets;

import java.io.IOException;

import game.character.Figur;
import game.management.WorldOfMKIBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Aktualisieren {
	public static void aktualisieren(WorldOfMKIBean game, HttpServletRequest request) throws ServletException, IOException{
		KarteAktualisieren2.aktualisierenKarte(game, request);
		//RequestDispatcher dispatcher=request.getRequestDispatcher("KarteAktualisieren");
		//dispatcher.forward(request, null);
		
		Figur f=(Figur)request.getSession().getAttribute("figur");
		if(game.getsieg()){
		request.getSession().setAttribute("sieg", true);
		}else{
			
			request.getSession().setAttribute("sieg", false);
		}
		for(Figur figur: game.getFiguren()){
			if(figur.equals(f)){
				request.getSession().setAttribute("figur", figur);
			}
		}
	
		
		if(game.aktiverSpieler() != null){
			String name = game.aktiverSpieler();
			
			if(name.equals(f.getName())){
				request.getSession().setAttribute("dran", true);
			}
			else{
				request.getSession().setAttribute("dran", false);
			}
		}
		
		KarteAktualisieren2.aktualisierenKarte(game, request);
		
	}
}
