<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, jakarta.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recibo de Reserva</title>
    <!-- Agregar Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Estilo adicional para personalizar la página si es necesario */
        /* Agrega tu propio CSS aquí */
    </style>
</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">Recibo de Reserva</h2>

    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <h3 class="card-title">Datos de la Reserva</h3>
                    <%
                        int cantidadButacasSeleccionadas = (int) request.getAttribute("cantidadButacasSeleccionadas");

                        Connection conn = null;
                        try {
                            int idUsuario = (int) session.getAttribute("idUsuario");

                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemaprime", "root", "");

                            String queryUsuario = "SELECT nombre_completo, correo_electronico, telefono FROM usuarios WHERE id = ?";
                            PreparedStatement psUsuario = conn.prepareStatement(queryUsuario);
                            psUsuario.setInt(1, idUsuario);
                            ResultSet rsUsuario = psUsuario.executeQuery();

                            if (rsUsuario.next()) {
                    %>

                    <p>Nombre: <%= rsUsuario.getString("nombre_completo") %></p>
                    <p>Email: <%= rsUsuario.getString("correo_electronico") %></p>
                    <p>Teléfono: <%= rsUsuario.getString("telefono") %></p>

                    <%
                        rsUsuario.close();
                        psUsuario.close();
                    } else {
                    %>

                    <p>No se encontraron datos personales para este usuario.</p>

                    <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (conn != null) {
                                    conn.close();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    %>
                </div>
                <div class="col-md-6">
                    <h3 class="card-title">Información de la Película</h3>
                    <%
                        Connection conn2 = null;
                        try {

                            int idPelicula = (int) session.getAttribute("idPelicula");

                            Class.forName("com.mysql.jdbc.Driver");
                            conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemaprime", "root", "");

                            String queryPelicula = "SELECT nombre, genero, clasificacion, valor_tercera_edad, valor_adulto FROM peliculas WHERE id_pelicula = ?";
                            PreparedStatement psPelicula = conn2.prepareStatement(queryPelicula);
                            psPelicula.setInt(1, idPelicula);
                            ResultSet rsPelicula = psPelicula.executeQuery();

                            if (rsPelicula.next()) {
                                String nombrePelicula = rsPelicula.getString("nombre");
                                String generoPelicula = rsPelicula.getString("genero");
                                String clasificacionPelicula = rsPelicula.getString("clasificacion");
                                double valorReserva;

                                // Calcular el valor de la reserva según el tipo seleccionado
                                if ("tercera_edad".equals(request.getParameter("tipoReserva"))) {
                                    valorReserva = rsPelicula.getDouble("valor_tercera_edad");
                                } else {
                                    valorReserva = rsPelicula.getDouble("valor_adulto");
                                }

                                // Calcular el monto total
                                double montoTotal = valorReserva * cantidadButacasSeleccionadas;
                    %>
                    <p>Información de la Película:</p>
                    <p>Nombre: <%= nombrePelicula %></p>
                    <p>Género: <%= generoPelicula %></p>
                    <p>Clasificación: <%= clasificacionPelicula %></p>
                    <p>Reservas: <%= cantidadButacasSeleccionadas %></p>
                    <p>Tipo de Reserva: <%= request.getParameter("tipoReserva") %></p>
                    <p>Monto Total: <%= montoTotal %></p>
                    <%
                        rsPelicula.close();
                        psPelicula.close();
                    } else {
                    %>

                    <p>No se encontró información de la película.</p>

                    <%
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (conn2 != null) {
                                    conn2.close();
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
    <a class="volver-button" href="../cartelera.jsp">Cartelera</a>
</div>


<!-- Agregar Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
