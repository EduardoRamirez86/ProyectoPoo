package com.primecinema.app.controlador;

import com.primecinema.app.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ReservarButacasServlet")
public class ReservarButacasServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;

        try {
            connection = Conexion.obtenerConexion();
            connection.setAutoCommit(false);

            HttpSession session = request.getSession();
            int idUsuario = (int) session.getAttribute("idUsuario");
            int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
            int idSala = Integer.parseInt(request.getParameter("idSala"));

            // Obtener las butacas seleccionadas
            String[] butacasSeleccionadas = request.getParameterValues("butaca");

            // Obtener las butacas ya reservadas en la sala
            List<String> butacasReservadas = obtenerButacasReservadas(idSala, connection);

            // Establecer la variable en el alcance de la solicitud
            request.setAttribute("butacasReservadas", butacasReservadas);

            // Redirigir a la página JSP
            request.getRequestDispatcher("Butaca.jsp").forward(request, response);

            // En el bucle donde generas las butacas, verifica si están reservadas
            for (String butaca : butacasSeleccionadas) {
                String[] partes = butaca.split("_");
                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);

                // Verificar si la butaca está reservada
                if (butacasReservadas.contains(butaca)) {
                    // La butaca está reservada, puedes hacer algo aquí si lo deseas
                }

                reservarButaca(idSala, fila, columna, connection);
                obtenerIdCompra(idUsuario, idPelicula, idSala, fila, columna, connection);
            }

            // Confirmar la transacción
            connection.commit();

            // Redirigir a una página de éxito
            response.sendRedirect("exito.jsp");

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

            // Imprimir errores en la consola del servidor
            System.err.println("Error en ReservarButacasServlet: " + e.getMessage());
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void reservarButaca(int idSala, int fila, int columna, Connection connection) throws SQLException {
        String estado = "reservada";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO asientos (fila, columna, estado, id_sala) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, fila);
            preparedStatement.setInt(2, columna);
            preparedStatement.setString(3, estado);
            preparedStatement.setInt(4, idSala);

            preparedStatement.executeUpdate();
        }
    }

    private int obtenerIdCompra(int idUsuario, int idPelicula, int idSala, int fila, int columna, Connection connection) throws SQLException {
        int idCompra = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO compras (id_usuario, id_pelicula, id_sala, fila, columna) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id_compra = LAST_INSERT_ID(id_compra)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idPelicula);
            preparedStatement.setInt(3, idSala);
            preparedStatement.setInt(4, fila);
            preparedStatement.setInt(5, columna);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    idCompra = resultSet.getInt(1);
                }
            }
        }

        return idCompra;
    }

    private List<String> obtenerButacasReservadas(int idSala, Connection connection) throws SQLException {
        List<String> butacasReservadas = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT fila, columna FROM asientos WHERE id_sala = ? AND estado = 'reservada'")) {

            preparedStatement.setInt(1, idSala);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int fila = resultSet.getInt("fila");
                    int columna = resultSet.getInt("columna");
                    butacasReservadas.add(fila + "_" + columna);
                }
            }
        }

        return butacasReservadas;
    }
}
