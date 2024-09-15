package tables_in_MySQL_DB;

import dbConnect.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbsTable<T> {

    protected void createTable(String tableTitle, List<String> listOfColumns) {
        MySQLConnection.getInstance().executeUpdate(String.format("CREATE TABLE %s (%s)", tableTitle, String.join(",", listOfColumns)));
        System.out.println("Таблица создана!");
    }

    protected void dropTable(String tableTitle) {
        MySQLConnection.getInstance().executeUpdate("DROP TABLE " + tableTitle);
        System.out.println("Таблица удалена!");
    }

    protected int checkIfTableEmpty(String tableTitle) {
        int numberOfRows = 0;
        try (ResultSet rs = MySQLConnection.getInstance().executeQuery("SELECT COUNT(*) FROM " + tableTitle)) {
            while (rs.next()) {
                numberOfRows = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось определить, существует ли такая таблица!", e);
        }
        return numberOfRows;
    }

    protected ResultSet selectALL(String tableTitle) {
        return MySQLConnection.getInstance().executeQuery("SELECT * FROM " + tableTitle);
    }

    protected abstract void insertDataIntoTable(T instance);

    protected abstract void updateDataInTable(T instance, int id);
}
