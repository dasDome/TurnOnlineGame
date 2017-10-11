<div id="Karte">
<table border="0" cellpadding="0" cellspacing="0">
	<% int x=(Integer)request.getAttribute("längeSpielbrett");
		int y=(Integer) request.getAttribute("breiteSpielbrett");
		int breite=500/(x);
		if(y>x)
			breite = 500/(y);
		for(int i=x;i>=1;i--){
			out.println("<tr>");
			for(int j=1;j<=y;j++){
				out.println("<td><img src='");
				out.println((String) request.getSession().getServletContext().getAttribute("feld "+i+"|"+j)+"' style='width:"+breite+"px;'/>");
				out.println("</td>");
			}
			out.println("</tr>");
		}
	%>
</table>
</div>