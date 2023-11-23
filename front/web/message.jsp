<%
    String message = (String) request.getAttribute("message");
    String redirect = (String) request.getAttribute("redirect");
%>
<script>
    alert("<%= message %>")
    window.location.href = "http://localhost:8080/gestion_stock/<%= redirect %>";
</script>