<%@page import="com.itextpdf.text.log.SysoLogger"%>
<jsp:useBean id="WorldOfMKIBean" class="game.management.WorldOfMKIBean"
	scope="application" />
<%@ page import="game.management.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>World Of MKI</title>

 <style type="text/css">@import url(main.css) all;</style>

  <!--[if IE]>
    <style type="text/css">@import url(mainIE.css);</style>
  <![endif]-->

</head>
<body>
	<%
		
		int id = (Integer)request.getSession().getAttribute("spielId");
		
		WorldOfMKIBean game = (WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+id);
		//out.println(game.getanzFiguren());
		String unten = (String) request.getSession().getServletContext().getAttribute("bewegenError");
		if (unten == "true") {
			out.println("<script type='text/javascript'>alert('Sie können nicht Ausserhalb des Spielfeldes sein!');</script>");
			String meldung ="Sie können nicht Ausserhalb des Spielfeldes sein!";
			game.setMeldung(meldung);
			request.getSession().getServletContext()
					.setAttribute("bewegenError", "false");
		}

		try {
			Figur[] angriff = (Figur[]) request.getSession().getServletContext().getAttribute("kampf");
			if (angriff != null) {

				if (angriff[0] != null && angriff[1] != null) {
					String s = "Kampf: " + "Sieger: "+ angriff[0].getName() + " Verlierer: "+ angriff[1].getName();
					out.println("<script type='text/javascript'>alert('"+ s + "');</script>");
					if(request.getSession().getServletContext().getAttribute("anzahlSpiele") != null){
						game.setMeldung(s);
					}
					request.getSession().getServletContext().setAttribute("kampf", null);
				}
			 else {

				if (angriff[2] != null && angriff[3] != null) {
					String s = "Kampf! " + angriff[2].getName()
							+ " ist gestorben. Sieger: "
							+ angriff[3].getName();
					out.println("<script type='text/javascript'>alert('"
							+ s + "');</script>");
					if(request.getSession().getServletContext().getAttribute("anzahlSpiele") != null){
						game.setMeldung(s);
					}
					request.getSession().getServletContext().setAttribute("kampf", null);
				}
			}
			}
		}catch (Exception e) {
			request.getServletContext().setAttribute("kampf", null);
		}
		

	%>

	<div id="main">
		<div id="oben">
			<div id="Spieleranzahl"><%
				int im=(Integer)request.getSession().getAttribute("spielId");
				WorldOfMKIBean rollenspielMKI=(WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+im);
				out.println("Runde: "+rollenspielMKI.getRunde()+ " Spieleranzahl: "+rollenspielMKI.getAnzGegner()+" Spieler am Zug: "+rollenspielMKI.aktiverSpieler()+"<br/>");
				
				String alleSpieler = "";
				for(int i = 0; i < rollenspielMKI.getanzFiguren(); i++){
					if(i==0)
						alleSpieler += rollenspielMKI.getFiguren().get(i).getName();
					else
						alleSpieler += ", "+rollenspielMKI.getFiguren().get(i).getName();
				}
				out.println(alleSpieler);
				
				%>
			</div>
			<div id="errorbox">
			<%

			if(request.getSession().getServletContext().getAttribute("anzahlSpiele") != null){
					if((game.getMeldungen() != null)&& (game.getMeldungen().size() > 0)){
						for(int meldungsanzahl = game.getMeldungen().size(); meldungsanzahl > 0; meldungsanzahl--){
							out.println((game.getMeldungen().get(meldungsanzahl-1))+"<br />");
						}
					}
				}
			%>



			</div>
			
		</div> 
	

		<div id="karte">
			<%@ include file="Karte.jsp"%>
		</div>
		<div id="spielerInformation">
			<div id="spielerbild">
				<%Figur q=(Figur)request.getSession().getAttribute("figur");
				String rassenname = (String) q.getRasse().toLowerCase();
				out.println("<img src='Images/"+rassenname+".jpg'/>");
				%>
			</div>
			
			<%@ include file="SpielerInformation.jsp"%><br />
			<%
				if (request.getSession().getAttribute("dran") != null) {
					boolean hide = (Boolean) request.getSession().getAttribute(
							"dran");
					if (!hide) {
						out.println("<div id='nichtAmZugLabel'>Du bist nicht am Zug!</div>");
						out.println("<div id='bewegenbuttons' style='visibility:hidden;'>");
					} else {
						out.println("<div id='amZugLabel'>Du bist am Zug!</div>");
						out.println("<div id='bewegenbuttons'>");
					}
				}
			%>
				<%@ include file="bewegen.jsp"%>
			</div>
			<%@ include file="Gegenstand.jsp"%>
			
			<div id="hidden"><%@ include file="update.jsp"%></div>
			<div id="bottom">
				<div id="speicherDiv"><%@ include file="Pdf.jsp"%></div>
				<div id="verlassenDiv"><%@ include file="SpielVerlassen.jsp"%></div>
			</div>
			
		</div>


		<div id="kontrollbereich">

	</div>
	</div>
</body>
</html>