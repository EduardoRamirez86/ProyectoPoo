package com.primecinema.app.controlador;

import com.primecinema.app.modelo.Sucursal;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ObtenerSucursalesServlet")
public class ObtenerSucursalesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Define la URL, usuario y contraseña de la base de datos
        String url = "jdbc:mysql://localhost/cinemaprime";
        String usuario = "root";
        String contraseña = "";

        // Crea una lista para almacenar las sucursales
        List<Sucursal> listaSucursales = new ArrayList<>();

        try {
            // Establece la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para obtener las sucursales
            String consulta = "SELECT * FROM sucursales";
            PreparedStatement statement = conexion.prepareStatement(consulta);

            ResultSet resultado = statement.executeQuery();

            // Itera a través de los resultados y crea objetos Sucursal
            while (resultado.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setId(resultado.getInt("id"));
                sucursal.setNombreSucursal(resultado.getString("nombre_sucursal"));
                sucursal.setGerente(resultado.getString("gerente"));
                sucursal.setDireccion(resultado.getString("direccion"));
                sucursal.setTelefono(resultado.getString("telefono"));

                listaSucursales.add(sucursal);
            }

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Configura la lista de sucursales como un atributo de solicitud
        request.setAttribute("sucursales", listaSucursales);

        // Redirige a la página JSP "listaSucursales"
        RequestDispatcher dispatcher = request.getRequestDispatcher("listaSucursales.jsp");
        dispatcher.forward(request, response);
    }
}

