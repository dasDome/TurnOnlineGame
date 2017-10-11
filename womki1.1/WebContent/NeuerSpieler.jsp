<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WorldOfMKI</title>
</head>
<body>
	<form method="post" action="NeuerSpielerServlet">
		<p>Spielername:</p>
		<input type="text" name="spielerName" value="SpielerName"/><br /><br />
		
		<input type="radio" name="spielerArt" value="Zwerg" checked/>Zwerg<br />
		<input type="radio" name="spielerArt" value="Ork"/>Ork<br />
		<input type="radio" name="spielerArt" value="Mensch"/>Mensch<br />
		<br />
		
		<input type="submit" value="OK"/>
	</form>
	<form method="post" action="Laden">
	<select name="dateien" size="5">
<%@ page import="java.io.*"%>

<%@ page import="game.management.WorldOfMKIBean"%>
<%@ page import="game.speicherung.*"%>
<% 
	//File folder = new File("D:\\Uni\\Inf2\\praktikum\\womki1.1\\WebContent\\saves\\");
	File folder = new File("D:\\Uni\\Inf2\\praktikum\\womki1.1\\");
	File[] listOfFiles = folder.listFiles( new FilenameFilter() {         
	    public boolean accept(File folder, String name) {
	    	// beim laden in session den name der Datei+endung reinschreiben
	    	// hilfsmethode in laden um zu prüfen, ob Spielername schon vergeben
	    	// is game null wenn nein dann figurenarray.namen  mit laden vergleichen
	    	
	    	return name.endsWith("ser")||name.endsWith("xml");
	    }
	});

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
    	  
    	  
		/*if(request.getSession().getAttribute("spielId")!=null){
    		 int id = (Integer)request.getSession().getAttribute("spielId");
    		 if(request.getSession().getServletContext().getAttribute("Spiel"+id) != null){
    			 WorldOfMKIBean game = (WorldOfMKIBean)request.getSession().getServletContext().getAttribute("Spiel"+id);
    			 if(game.xmlvorhanden(listOfFiles[i].getName(), DateiArten.xml)!=null){
    			 String name = game.xmlvorhanden(listOfFiles[i].getName(), DateiArten.xml);
    			 }
    			 for(int durchlaufspieler = 0; durchlaufspieler < game.getanzFiguren();durchlaufspieler++){
    				 if(!(game.getFiguren().get(durchlaufspieler).getName().equals(name))){
    					 out.println("<option value='"+listOfFiles[i].getName()+"'>"+listOfFiles[i].getName()+"</option>");
    				 }
    			 }
    		 }
		}*/
		
		String test = (String)request.getSession().getServletContext().getAttribute("loadedgames");
		System.out.println(test);
		if(test!=null){
			String[] loaded = test.split(",");
			for(int k=0; k<loaded.length; k++){
				if(listOfFiles[i].getName() != loaded[k]){
					out.println("<option value='"+listOfFiles[i].getName()+"'>"+listOfFiles[i].getName()+"</option>");
				}
			}
		}else{
			out.println("<option value='"+listOfFiles[i].getName()+"'>"+listOfFiles[i].getName()+"</option>");
		}
		
        //System.out.println("File " + listOfFiles[i].getName());
        //out.println("<option value='"+listOfFiles[i].getName()+"'>"+listOfFiles[i].getName()+"</option>");
      } //else if (listOfFiles[i].isDirectory()) {
        //System.out.println("Directory " + listOfFiles[i].getName());
      //}
    }%>
	</select>
	<input type="submit" name="Submit" value="Senden">
<!--   <input type="hidden" name="MAX_FILE_SIZE" value="100000"> --> 
<!--   <input type="file" name="datei" size="40" maxlength="100000" id="dateiNameLaden"> -->
<!--   <input type="submit" name="Submit" value="Senden"> -->
</form>
</body>
</html>