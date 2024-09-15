package dbConnect;

import dbCredentials.Credentials;

import java.sql.*;
import java.util.Map;

public class MySQLConnection implements DBConnection {

    private static MySQLConnection mySQLConnection;
    private Statement statement;
    private Connection connection;

    private MySQLConnection() { // Не вижу смысла создания отдельного метода open(). Создание конекшна и стейтмента специально в конструкторе, чтобы при вызове метода getInstance() сразу создавался всего один конекшн и один стейтмент
        Credentials credentials = new Credentials();
        Map<String, String> mapOfCredentials = credentials.getCredentials("src/main/resources/SQLCredentials.properties");
        try {
            connection = DriverManager.getConnection(String.format("%s/%s", mapOfCredentials.get("url"), mapOfCredentials.get("name")), mapOfCredentials.get("username"), mapOfCredentials.get("password"));
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с БД!", e);
        }
    }

    public static MySQLConnection getInstance() {
        if (mySQLConnection == null) {
            mySQLConnection = new MySQLConnection();
        }
        return mySQLConnection;
    }

    @Override
    public void closeConnection() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                System.out.println("Statement закрыт: " + statement.isClosed()); // просто для проверки
                statement = null;
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection закрыт: " + connection.isClosed()); // просто для проверки
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось закрыть соединение с БД!", e);
        }
    }

    @Override
    public ResultSet executeQuery(String sqlQuery) {
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить данные из БД!", e);
        }
    }

    @Override
    public void executeUpdate(String sqlQuery) {
        try {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить данные в БД!", e);
        }
    }
}
