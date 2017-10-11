<div id="Gegenstand">

<form action="fallenLassen" method="get"> 
<p>
<select name="Ausrüstung" size="1">
	<%@page import="game.character.*" %>
	<%@page import="game.objekte.*" %>
	<%
	String test;
	game.character.Figur f=(Figur)session.getAttribute("figur");
	for(Gegenstand g:f.getGegenstände()){
		out.println("<option value='"+g.toString()+"'>");
		out.println(g.toString());
		out.println("</option>");
	}
/* 	int i=(Integer)request.getAttribute("all");
	for(int j=0;j<i;j++){
		out.println("<option value=(String)j+1>");
		out.println((String)request.getAttribute("gegenstand"+j));
		out.println("</option>"); */
/* 	} */
	
	%>
</select>
</p>

<input type="submit" value="OK"/>

</form> 

</div>