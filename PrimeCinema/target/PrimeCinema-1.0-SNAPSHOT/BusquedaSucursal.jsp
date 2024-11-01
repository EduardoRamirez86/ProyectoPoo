<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resultado de búsqueda por sucursal</title>
    <link rel="stylesheet" href="css/Menu7.css">
    <!-- Agrega tus estilos adicionales si es necesario -->
</head>
<body>
<nav class="menu">
    <section class="menu__container">
        <h1 class="menu__logo">Bienvenido PrimeCinema</h1>
        <ul class="menu__links">
            <li class="menu__item menu__item--show">
                <a href="menuUsuario.jsp" class="menu__link menu__link--inside">Inicio</a>
            </li>
            <li class="menu__item menu__item--show">
                <a href="cartelera.jsp" class="menu__link menu__link--inside">Cartelera</a>
            </li>
            <li class="menu__item menu__item--show">
                <a href="estrenos.jsp" class="menu__link menu__link--inside">Estrenos</a>
            </li>
            <li class="menu__item menu__item--show">
                <a href="ObtenerSucursalesServlet" class="menu__link menu__link--inside">Sucursales</a>
            </li>
            <li class="menu__item menu__item--show">
                <a href="quienesSomos.jsp" class="menu__link menu__link--inside">Quienes Somos</a>
            </li>
        </ul>
        <div class="menu__hamburguer">
            <img src="xd" class="menu__img">
        </div>
    </section>
</nav>
<h1 class="centered-title">Resultado de búsqueda por sucursal</h1>

<div class="form-container">
    <!-- Formulario de búsqueda por nombre de película -->
    <form action="BuscarPeliculasServlet" method="post" class="search-form">
        <label for="nombrePelicula">Buscar por película:</label>
        <div class="input-group">
            <input type="text" id="nombrePelicula" name="nombrePelicula" class="search-input" placeholder="Nombre de la película" required>
            <button type="submit" class="search-button">Buscar</button>
        </div>
    </form>

    <!-- Formulario de búsqueda por nombre de sucursal -->
    <form action="BuscarSucursalServlet" method="post" class="search-form">
        <label for="nombreSucursal">Buscar por sucursal:</label>
        <div class="input-group">
            <select id="nombreSucursal" name="nombreSucursal" class="search-select" required>
                <option value="" disabled selected>Seleccione una sucursal</option>
                <%-- Realiza la consulta a la base de datos para obtener las sucursales --%>
                <%
                    // Configuración de la conexión a la base de datos (ajusta según tu entorno)
                    String url = "jdbc:mysql://localhost/cinemaprime";
                    String usuario = "root";
                    String contraseña = "";

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

                        // Consulta SQL para obtener las sucursales
                        String consulta = "SELECT nombre_sucursal FROM sucursales";
                        try (PreparedStatement statement = conexion.prepareStatement(consulta)) {
                            try (ResultSet resultSet = statement.executeQuery()) {
                                while (resultSet.next()) {
                                    String sucursal = resultSet.getString("nombre_sucursal");
                %>
                <option value="<%= sucursal %>"><%= sucursal %></option>
                <%
                                }
                            }
                        }
                        conexion.close();
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                %>
            </select>
            <button type="submit" class="search-button">Buscar</button>
        </div>
    </form>
</div>

<div class="cartelera">
    <%
        String rutaBaseImagenes = (String) request.getAttribute("rutaBaseImagenes");

        // Obtén los resultados de la búsqueda desde los atributos de solicitud
        List<String> resultados = (List<String>) request.getAttribute("resultados");

        // Muestra los resultados en el JSP
        if (resultados != null && !resultados.isEmpty()) {
            for (String resultado : resultados) {
                String[] partes = resultado.split(" - ");
                String nombrePelicula = partes[0];
                String sucursal = partes[1];
                String numeroSala = partes[2];
                String horario = partes[3];

                // Construye la ruta de la imagen sin reemplazar los espacios
                String rutaImagen = rutaBaseImagenes + nombrePelicula + ".jpg";
    %>
    <div class="cartelera-item">
        <img src="<%= rutaImagen %>" alt="Portada de <%= nombrePelicula %>" class="movie-image">
        <h3><%= nombrePelicula %></h3>
        <p>Sucursal: <%= sucursal %></p>
        <p>Sala: <%= numeroSala %></p>
        <p>Horario: <%= horario %></p>
        <form action="DetallePeliculaServlet" method="post">
            <input type="hidden" name="pelicula" value="<%= nombrePelicula %>">
            <input type="submit" value="Comprar">
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p>No se encontraron resultados para "<%= request.getParameter("nombreSucursal") %>".</p>
    <%
        }
    %>
</div>

</body>
</html>
