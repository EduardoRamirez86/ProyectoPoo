package com.primecinema.app.controlador;

import com.primecinema.app.Conexion;
import com.primecinema.app.modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String nombreCompleto = request.getParameter("nombre_Completo");
        String dui = request.getParameter("dui");
        String direccion = request.getParameter("direccion");
        String correoElectronico = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        if (!usuarioDuplicado(login, dui)) {
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setPassword(password);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setDui(dui);
            usuario.setDireccion(direccion);
            usuario.setCorreoElectronico(correoElectronico);
            usuario.setTelefono(telefono);

            if (registrarUsuario(usuario)) {

                // Después de registrar al usuario con éxito
                request.setAttribute("usuario", usuario);
                request.setAttribute("mensaje", "El registro se ha completado con éxito.");
                request.getRequestDispatcher("Exito/registroUsuario.jsp").forward(request, response);


            } else {
                response.sendRedirect("Errores/registro_fallido.jsp");
            }
        } else {
            // Mostrar un mensaje de error al usuario
            response.sendRedirect("Errores/RegistrosDuplicados.jsp");
        }
    }

    private boolean usuarioDuplicado(String login, String dui) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Conexion.obtenerConexion();

            // Verifica si el usuario ya existe
            String consultaUsuarioExistente = "SELECT COUNT(*) FROM usuarios WHERE login = ?";
            ps = conn.prepareStatement(consultaUsuarioExistente);
            ps.setString(1, login);
            ResultSet resultadoUsuario = ps.executeQuery();

            if (resultadoUsuario.next() && resultadoUsuario.getInt(1) > 0) {
                // El usuario ya existe, muestra un mensaje de error o toma alguna acción adecuada.
                return true;
            }

            // Verifica si el DUI ya existe
            String consultaDuiExistente = "SELECT COUNT(*) FROM usuarios WHERE dui = ?";
            ps = conn.prepareStatement(consultaDuiExistente);
            ps.setString(1, dui);
            ResultSet resultadoDui = ps.executeQuery();

            if (resultadoDui.next() && resultadoDui.getInt(1) > 0) {
                // El DUI ya existe, muestra un mensaje de error o toma alguna acción adecuada.
                return true;
            }

            // Si no se encontraron registros con el mismo usuario o DUI, no hay duplicados.
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Error en la consulta, asumimos duplicado por precaución
        } finally {
            Conexion.cerrarConexion(conn, ps, null);
        }
    }

    private boolean registrarUsuario(Usuario usuario) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Conexion.obtenerConexion();

            String consulta = "INSERT INTO usuarios (login, password, nombre_completo, dui, direccion, correo_electronico, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(consulta);
            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getDui());
            ps.setString(5, usuario.getDireccion());
            ps.setString(6, usuario.getCorreoElectronico());
            ps.setString(7, usuario.getTelefono());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.cerrarConexion(conn, ps, null);
        }
    }
}
