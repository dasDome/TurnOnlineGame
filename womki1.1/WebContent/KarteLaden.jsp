<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form method="post" action="KarteLaden">
<h2>Karten zum Laden:</h2><br/><br/>
<!-- <select name="spielauswahl" size="5"> -->
<%

File dir = new File("D:\\Uni\\Inf2\\praktikum\\womki1.1\\");
File[] files = dir.listFiles(new FilenameFilter() {         
    public boolean accept(File dir, String name) {
        return name.endsWith("csv");
    }
});


for(File f: files){
	out.println("<input type='radio' name='csv' value='"+f.getName()+"'>"+f.getName()+"</input>");
	//out.println("<input type='radio' name='csv' value='"+f.getName()+"'>"+f.getName()+"</input>");
	//<input type="radio" name="csv"><%out.println(f.getName());</input>
}
%>
<!-- </select> -->
<input type="submit" value="laden" name="laden" style="width:70px">
</form>
</body>
</html>