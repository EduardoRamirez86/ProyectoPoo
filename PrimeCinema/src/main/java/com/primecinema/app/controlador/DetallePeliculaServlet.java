package com.primecinema.app.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DetallePeliculaServlet")
public class DetallePeliculaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");

        // Obtén el nombre de la película seleccionada del parámetro enviado desde el JSP "cartelera"
        String nombrePelicula = request.getParameter("pelicula");

        // Definir variables para almacenar los datos de la película
        String nombre = null;
        String genero = null;
        String clasificacion = null;
        String formato = null;
        double valorTerceraEdad = 0.0;
        double valorAdulto = 0.0;

        // Realiza la conexión a la base de datos (asegúrate de ajustar la URL, el usuario y la contraseña)
        String url = "jdbc:mysql://localhost/cinemaprime";
        String usuario = "root";
        String contraseña = "";

        try {
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para obtener los datos de la película
            String consulta = "SELECT id_pelicula,nombre, genero, clasificacion, formato, valor_tercera_edad, valor_adulto FROM peliculas WHERE nombre = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombrePelicula);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {

                // Obtener el ID de la película
                int idPelicula = resultado.getInt("id_pelicula");

                request.setAttribute("idPelicula", idPelicula);
                nombre = resultado.getString("nombre");
                genero = resultado.getString("genero");
                clasificacion = resultado.getString("clasificacion");
                formato = resultado.getString("formato");
                valorTerceraEdad = resultado.getDouble("valor_tercera_edad");
                valorAdulto = resultado.getDouble("valor_adulto");


                // Consulta SQL para obtener información de la sala relacionada
                String consultaSala = "SELECT s.id_sala, s.numero_sala, s.id_sucursal, s.horario_proyeccion, s.fecha_proyeccion " +
                        "FROM salas s " +
                        "WHERE s.id_pelicula = ?";
                PreparedStatement statementSala = conexion.prepareStatement(consultaSala);
                statementSala.setInt(1, idPelicula);

                ResultSet resultadoSala = statementSala.executeQuery();

                if (resultadoSala.next()) {
                    // Set the sala data as attributes in the session using a different variable name
                    HttpSession sessionObj = request.getSession();
                    sessionObj.setAttribute("idSala", resultadoSala.getInt("id_sala"));
                    sessionObj.setAttribute("numeroSala", resultadoSala.getInt("numero_sala"));
                    sessionObj.setAttribute("idSucursal", resultadoSala.getInt("id_sucursal"));
                    sessionObj.setAttribute("horarioProyeccion", resultadoSala.getString("horario_proyeccion"));
                    sessionObj.setAttribute("fechaProyeccion", resultadoSala.getString("fecha_proyeccion"));
                    sessionObj.setAttribute("idPelicula", idPelicula);
                }
            }

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Establece los datos de la película como atributos en la solicitud
        request.setAttribute("nombre", nombre);
        request.setAttribute("genero", genero);
        request.setAttribute("clasificacion", clasificacion);
        request.setAttribute("formato", formato);
        request.setAttribute("valor_tercera_edad", valorTerceraEdad);
        request.setAttribute("valor_adulto", valorAdulto);

        request.setAttribute("idUsuario", idUsuario);

        // Redirige la solicitud a una página JSP para mostrar los detalles
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario/detallePelicula.jsp");
        dispatcher.forward(request, response);
    }
}

