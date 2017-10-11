<%@page import="game.character.*" %>
<%@page import="game.management.*" %>
	<p>
	<% Figur p=(Figur)request.getSession().getAttribute("figur");
		out.println("<div id='info'>");
		out.println("<div id='spielername'>"+p.getName()+"</div><div id='clear'></div>");
		out.println("<div id='angriff'>"+p.getAngriff()+"</div>");
		out.println("<div id='verteidigung'>"+p.getVerteidigung()+"</div><div id='clear'></div>");
		out.println("<div id='gesundheit'>"+p.getGesundheit()+"</div><div id='clear'></div>");
		out.println("<div id='bewegung'>"+p.getBewegung()+"</div>");
		out.println("<div id='scritte'>"+p.getSchritte()+"</div>");
		out.println("</div><div id='clear'></div>");
		/*String s="Inventar: "+"<br />";
		for(int i=0;i<p.getGegenstände().size();i++){
			s+=p.getGegenstände().get(i)+"<br />";
		}
		out.println(s);*/
	%>
	</p>
