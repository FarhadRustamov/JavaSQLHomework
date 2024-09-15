package dbConnect;

import java.sql.ResultSet;

public interface DBConnection {

    void closeConnection();

    ResultSet executeQuery(String sqlQuery);

    void executeUpdate(String sqlQuery);
}
