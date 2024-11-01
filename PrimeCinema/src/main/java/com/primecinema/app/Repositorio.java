package com.primecinema.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repositorio {

    public static int obtenerTotalButacasReservadas(int idUsuario, Connection connection) throws SQLException {
        int totalReservadas = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM DetalleCompras dc JOIN Compras c ON dc.id_compra = c.id_compra WHERE c.id_usuario = ?")) {

            preparedStatement.setInt(1, idUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalReservadas = resultSet.getInt(1);
                }
            }
        }

        return totalReservadas;
    }

    public static void reservarButaca(int idSala, int fila, int columna, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Asientos SET estado = 'reservada' WHERE id_sala = ? AND fila = ? AND columna = ?")) {

            preparedStatement.setInt(1, idSala);
            preparedStatement.setInt(2, fila);
            preparedStatement.setInt(3, columna);

            preparedStatement.executeUpdate();
        }
    }

    public static int obtenerIdCompra(int idUsuario, int idSala, Connection connection) throws SQLException {
        int idCompra = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Compras (id_usuario, id_sala) VALUES (?, ?) ON DUPLICATE KEY UPDATE id_compra = LAST_INSERT_ID(id_compra)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setInt(2, idSala);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    idCompra = resultSet.getInt(1);
                }
            }
        }

        return idCompra;
    }

    public static void registrarDetalleCompra(int idCompra, int idSala, int fila, int columna, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO DetalleCompras (id_compra, id_asiento) SELECT ?, id_asiento FROM Asientos WHERE id_sala = ? AND fila = ? AND columna = ?")) {

            preparedStatement.setInt(1, idCompra);
            preparedStatement.setInt(2, idSala);
            preparedStatement.setInt(3, fila);
            preparedStatement.setInt(4, columna);

            preparedStatement.executeUpdate();
        }
    }
}
