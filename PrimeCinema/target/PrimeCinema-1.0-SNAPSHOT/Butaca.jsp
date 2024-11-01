<%@ page import="java.sql.*" %>
<%@ page import="com.primecinema.app.Conexion" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background-image: url('https://signosdeestostiempos.files.wordpress.com/2018/09/kdpn.gif');
            background-size: cover;
            background-position: center;
            text-align: center;
            padding: 20px;
            font-family: Arial, sans-serif;
        }

        .celeste {
            background-color: #add8e6;
            padding: 10px;
            margin-bottom: 5px;
            display: inline-block;
        }

        .rojo {
            background-color: red;
        }

        .verde {
            background-color: #00FF00;
        }

        form {
            display: inline-block;
            background-color: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 10px;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
    <title>Reserva de Butacas</title>
</head>
<body>

<%
    Connection connection = Conexion.obtenerConexion();
%>
<%
    int idPelicula = (int) session.getAttribute("idPelicula");
    int idSala1 = (int) session.getAttribute("idSala");
    int idUsuario=(int) session.getAttribute("idUsuario");
    // Ahora puedes usar idPelicula e idSala en tu JSP
%>
<p>ID de la Película: <%= idPelicula %></p>
<p>ID de la Sala: <%= idSala1 %></p>
<p>ID del Usuario: <%= idUsuario %></p>
<form action="ReservarButacasServlet" method="post">
    <input type="hidden" name="idUsuario" value="${idUsuario}">
    <input type="hidden" name="idPelicula" value="${idPelicula}">
    <input type="hidden" name="idSala" value="${idSala}">

    <h1>Selecciona tus butacas:</h1><br>

    <!-- Agregar el select para el tipo de reserva -->
    <label for="tipoReserva">Seleccione el tipo de reserva:</label>
    <select name="tipoReserva" id="tipoReserva">
        <option value="adulto">Adulto</option>
        <option value="tercera_edad">Adulto Mayor</option>
    </select>

    <!-- Casillas de verificación para representar los asientos -->
    <!-- Filas y columnas -->
    <% for (int fila = 1; fila <= 8; fila++) { %>
    <% for (int columna = 1; columna <= 5; columna++) { %>
    <%
        boolean asientoReservado = false;
        if (connection != null) {
            String idSalaParam = request.getParameter("idSala");
            if (idSalaParam != null) {
                int idSala = Integer.parseInt(idSalaParam);

                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM asientos WHERE id_sala = ? AND fila = ? AND columna = ? AND estado = 'reservada'")) {
                    preparedStatement.setInt(1, idSala);
                    preparedStatement.setInt(2, fila);
                    preparedStatement.setInt(3, columna);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            int count = resultSet.getInt(1);
                            asientoReservado = (count > 0);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>
    <div class="celeste <%= asientoReservado ? "verde" : "" %>">
    <input type="checkbox" name="butaca" value="<%= fila %>_<%= columna %>" <% if (asientoReservado) { %> checked <% } %> onclick="cambiarColor(this)">
        Fila <%= fila %>, Asiento <%= columna %>
    </div>


    <% } %>
    <br>
    <% } %>

    <button type="submit">Reservar</button>
</form>

<script>
    // Función para cambiar el color al hacer clic en el checkbox
    function cambiarColor(checkbox) {
        checkbox.parentNode.classList.toggle("rojo", checkbox.checked);
    }
    // Variable para almacenar el precio seleccionado
    let precioSeleccionado = 0;
</script>

<%
    Conexion.cerrarConexion(connection, null, null);
%>



</body>
</html>
