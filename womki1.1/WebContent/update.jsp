<form action="updateservlet" method="post">
<input type="submit" value="Aktualisieren" id ="Aktualisieren" name="Aktualisieren">
</form>
<script type="text/javascript">
window.onload= function() {
	setInterval(function () {document.getElementById("Aktualisieren").click();}, 5000);
	};  
</script>