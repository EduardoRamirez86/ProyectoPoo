<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            background-image: url('https://signosdeestostiempos.files.wordpress.com/2018/09/kdpn.gif'); /* Cambia 'tu_imagen_de_fondo.jpg' con la ruta de tu imagen de fondo */
            background-size: cover;
            background-position: center;
            text-align: center;
            padding: 20px;
            font-family: Arial, sans-serif;
        }

        /* Estilo para el contenedor celeste */
        .celeste {
            background-color: #add8e6; /* Celeste */
            padding: 10px;
            margin-bottom: 5px;
            display: inline-block; /* Para que los contenedores estén en línea */
        }

        /* Estilo para el checkbox rojo */
        .rojo {
            background-color: red;
        }

        form {
            display: inline-block;
            background-color: rgba(255, 255, 255, 0.8); /* Fondo semi-transparente blanco */
            padding: 20px;
            border-radius: 10px;
        }

        button {
            background-color: #4CAF50; /* Verde */
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049; /* Verde más oscuro en hover */
        }
    </style>
    <title>Reserva de Butacas</title>
</head>
<body>

<form action="ReservarButacasServlet" method="post">
    <input type="hidden" name="idUsuario" value="${idUsuario}">
    <input type="hidden" name="idPelicula" value="${idPelicula}">
    <input type="hidden" name="idSala" value="${idSala}">

    <h1>Selecciona tus butacas:</h1><br>

    <%
        // Obtener la lista de butacas reservadas del alcance de la solicitud
        List<String> butacasReservadas = (List<String>) request.getAttribute("butacasReservadas");
        if (butacasReservadas == null) {
            butacasReservadas = new ArrayList<>();
        }
    %>

    <!-- Casillas de verificación para representar los asientos -->
    <% for (int fila = 1; fila <= 8; fila++) { %>
    <% for (int columna = 1; columna <= 5; columna++) { %>
    <% String valorButaca = fila + "_" + columna; %>

    <% if (butacasReservadas.contains(valorButaca)) { %>
    <div class="butaca-reservada">
        <input type="checkbox" name="butaca" value="<%= valorButaca %>" disabled>
        Fila <%= fila %>, Asiento <%= columna %> (Reservada)
    </div>
    <% } else { %>
    <div class="celeste">
        <input type="checkbox" name="butaca" value="<%= valorButaca %>" onclick="cambiarColor(this)">
        Fila <%= fila %>, Asiento <%= columna %>
    </div>
    <% } %>
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
</script>

</body>
</html>
