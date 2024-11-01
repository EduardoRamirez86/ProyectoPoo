package com.primecinema.app.controlador;

import com.primecinema.app.Conexion; // Asegúrate de importar la clase de conexión correcta
import com.primecinema.app.modelo.Pelicula;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegistroPeliculaServlet")
public class RegistroPeliculaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Recupera los datos del formulario
        String nombrePelicula = request.getParameter("nombre_pelicula");
        String genero = request.getParameter("genero");
        String clasificacion = request.getParameter("clasificacion");
        String formatoProyeccion = request.getParameter("formato_proyeccion");
        double precioTerceraEdad = Double.parseDouble(request.getParameter("precio_tercera_edad"));
        double precioAdultos = Double.parseDouble(request.getParameter("precio_adultos"));

        // Crea una instancia de la película
        Pelicula pelicula = new Pelicula();
        pelicula.setNombre(nombrePelicula);
        pelicula.setGenero(genero);
        pelicula.setClasificacion(clasificacion);
        pelicula.setFormato(formatoProyeccion);
        pelicula.setValorTerceraEdad(precioTerceraEdad);
        pelicula.setValorAdulto(precioAdultos);

        if (registrarPelicula(pelicula)) {
            request.setAttribute("pelicula", pelicula);
            request.getRequestDispatcher("Exito/registroPelicula.jsp").forward(request, response);
        } else {
            response.sendRedirect("Errores/registro_fallido_Registros.jsp"); // Redirige a una página de registro fallido
        }
    }

    private boolean registrarPelicula(Pelicula pelicula) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Conexion.obtenerConexion(); // Utiliza tu método de obtención de conexión
            String consulta = "INSERT INTO Peliculas (nombre, genero, clasificacion, formato, valor_tercera_edad, valor_adulto) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(consulta);
            ps.setString(1, pelicula.getNombre());
            ps.setString(2, pelicula.getGenero());
            ps.setString(3, pelicula.getClasificacion());
            ps.setString(4, pelicula.getFormato());
            ps.setDouble(5, pelicula.getValorTerceraEdad());
            ps.setDouble(6, pelicula.getValorAdulto());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.cerrarConexion(conn, ps, null); // Utiliza tu método de cierre de conexión
        }
    }
}

