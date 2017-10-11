<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="xmlLaden">
  Datei:
  <input type="hidden" name="MAX_FILE_SIZE" value="100000">
  <input type="file" name="datei" size="40" maxlength="100000">
  <input type="submit" name="Submit" value="Senden">
</form>

</body>
</html>