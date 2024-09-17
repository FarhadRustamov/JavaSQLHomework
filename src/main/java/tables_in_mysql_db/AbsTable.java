package tables_in_mysql_db;

import database.connect.MySQLConnection;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbsTable<T> {
    private final String tableTitle;

    protected AbsTable(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    protected void createTable(List<String> listOfColumns) {
        if (!checkIfTableExists()) {
            MySQLConnection.getInstance().executeUpdate(String.format("CREATE TABLE %s (%s)", tableTitle, String.join(",", listOfColumns)));
            System.out.println("Таблица создана!");
        }
    }

    protected void dropTable() {
        MySQLConnection.getInstance().executeUpdate("DROP TABLE " + tableTitle);
        System.out.println("Таблица удалена!");
    }

    protected int checkIfTableEmpty() {
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

    protected boolean checkIfTableExists() {
        try (ResultSet rs = MySQLConnection.getInstance().getConnection().getMetaData().getTables(null, null, tableTitle, new String[]{"TABLE"})) {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Непонятно, существует ли таблица или нет!", e);
        }
    }

    protected ResultSet selectALL() {
        return MySQLConnection.getInstance().executeQuery("SELECT * FROM " + tableTitle);
    }

    protected abstract void insertDataIntoTable(T instance);

    protected abstract void updateDataInTable(T instance, int id);
}
