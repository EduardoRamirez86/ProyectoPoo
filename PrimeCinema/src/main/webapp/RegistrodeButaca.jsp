<%@ page import="java.util.List" %>
<%@ page import="com.primecinema.app.modelo.Asiento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seleccionar Asientos</title>
</head>
<body>
<form action="ReservarAsientosServlet" method="post">
    <input type="hidden" name="idUsuario" value="<%= request.getParameter("idUsuario") %>">
    <input type="hidden" name="idPelicula" value="<%= request.getParameter("idPelicula") %>">
    <input type="hidden" name="idSala" value="<%= request.getParameter("idSala") %>">

    <table border="1">
        <c:forEach var="asiento" items="${asientos}"><% Asiento asiento = null; %>
            <tr>
                <td>
                    <input type="checkbox" name="asientosReservados" value="<%= asiento.getIdAsiento() %>">
                </td>
                <td>
                    <%= asiento.getFila() %>-<%= asiento.getColumna() %>
                </td>
            </tr>
        </c:forEach>
    </table>

    <input type="submit" value="Reservar">
</form>

<script>
    function seleccionarColumnas(checkbox, fila) {
        // Obtener todas las checkboxes en la fila actual
        var checkboxes = document.querySelectorAll('input[name="filasReservadas"][value="' + fila + '"]');

        // Iterar sobre las checkboxes y seleccionar/deseleccionar
        checkboxes.forEach(function (cb) {
            cb.checked = checkbox.checked;
        });
    }
</script>
</body>
</html>
