<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WorldOfMKI</title>
</head>
<body>
	<form method="post" action="NewGame">
	<span>Länge:
	<select name="breite" size="1">
	
		<%for(int i=1;i<=20;i++){
			out.println("<option value='"+i+"'>"+i+"</option>");
			}%>
	</select>
	</span><br /><br />
	<span>Breite:
	<select name="länge" size="1">
		<%for(int i=1;i<=20;i++){
			out.println("<option value='"+i+"'>"+i+"</option>");
			}%>
	</select>
	</span>
	<br /><br />
	<input type="submit" value="OK"/>
	</form>
</body>
</html>