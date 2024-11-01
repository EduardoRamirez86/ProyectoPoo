package com.primecinema.app.controlador;

import com.primecinema.app.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/TruncateAsientosServlet")
public class TruncateAsientosServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;

        try {
            connection = Conexion.obtenerConexion();
            connection.setAutoCommit(false);  // Deshabilitar la confirmación automática

            // Truncar la tabla asientos
            truncarAsientos(connection);

            // Confirmar la transacción
            connection.commit();

            // Redirigir a una página de éxito o a la misma página
            response.sendRedirect("exito.jsp");

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();  // Revertir la transacción en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

            // Imprimir errores en la consola del servidor
            System.err.println("Error en TruncateAsientosServlet: " + e.getMessage());
            response.sendRedirect("error.jsp");
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);  // Restaurar la confirmación automática
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void truncarAsientos(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE asientos")) {
            preparedStatement.executeUpdate();
        }
    }
}
