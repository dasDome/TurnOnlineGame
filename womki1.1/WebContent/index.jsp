<jsp:useBean id = "WorldOfMKIBean" class="game.management.WorldOfMKIBean" scope="application"/>
<%@ page import="game.management.*" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript">
	function timedRefresh(timeoutPeriod) {
		setTimeout("location.reload(true);",timeoutPeriod);
	}
	</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index</title>
<link rel="stylesheet" type="text/css" href="main.css" />
</head>
<body onload="JavaScript:timedRefresh(5000);">
<%@page import="game.management.*" %>



<div id="main">
	<div id="left" style="float:left;">
		<form method="post" action="SpielBeitreten">
		<select name="spielauswahl" size="5">
		<%
			if(request.getSession().getServletContext().getAttribute("anzahlSpiele") != null){
				int anz = (Integer)request.getSession().getServletContext().getAttribute("anzahlSpiele");
				for(int i = 1; i <= anz; i++){
					WorldOfMKIBean g = (WorldOfMKIBean)request.getSession(true).getServletContext().getAttribute("Spiel"+i);
					if(g.getanzFiguren()< (g.getanzSpielfelder()-1)){
						int runde = (Integer)g.getRunde();
						out.println("<option value='"+i+"'>Spiel "+i+" Runde: "+runde+"</option>");
						//request.getSession(	).getServletContext().setAttribute("runde", g.getRunde());
					}
				}
			}
	
		%>
		</select><br />
		<input type="submit" value="Spiel beitreten"/>
		</form>
	</div>
	
	<div id="right" style="float:right;">
		<form method="post" action="NeuesSpiel.jsp">
		<input type="submit" value="Neues Spiel"/>
		</form>
		<br />
		<form method="post" action="KarteLaden.jsp">
		<input type="submit" value="Karte Laden"/>
		</form>
	</div>
</div>
</body>
</html>