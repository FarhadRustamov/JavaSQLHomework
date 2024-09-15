package tables_in_MySQL_DB;

import animals.Animal;
import data.AnimalSpecies;
import dbConnect.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnimalsTable extends AbsTable<Animal> {
    private final String TABLE_TITLE = "Animals";
    private final List<String> LIST_OF_COLUMNS = List.of("name", "age", "weight", "color", "type");

    public void createAnimalsTable() {
        List<String> listOfColumns = new ArrayList<>();
        listOfColumns.add("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY");
        listOfColumns.add(LIST_OF_COLUMNS.get(0) + " VARCHAR(50) NOT NULL");
        listOfColumns.add(LIST_OF_COLUMNS.get(1) + " INT");
        listOfColumns.add(LIST_OF_COLUMNS.get(2) + " DECIMAL(20,2)");
        listOfColumns.add(LIST_OF_COLUMNS.get(3) + " VARCHAR(50)");
        listOfColumns.add(LIST_OF_COLUMNS.get(4) + " VARCHAR(50)");
        createTable(TABLE_TITLE, listOfColumns);
    }

    public void dropAnimalsTable() {
        dropTable(TABLE_TITLE);
    }

    public int checkIfAnimalsTableEmpty() {
        return checkIfTableEmpty(TABLE_TITLE);
    }

    public void selectAllFromAnimalsTable() {
        try (ResultSet rs = selectALL(TABLE_TITLE)) {
            System.out.printf("%5s %10s %10s %15s %15s %10s%n", "id", "name", "age", "weight", "color", "type");
            System.out.println("-----------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%5d %10s %10d %15s %15s %10s%n",
                        rs.getInt(1), // id
                        rs.getString(2), // name
                        rs.getInt(3), // age
                        rs.getBigDecimal(4).stripTrailingZeros().toPlainString(), // weight
                        rs.getString(5), // color
                        rs.getString(6)); // type
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удается показать данные из таблицы!", e);
        }
    }

    public ResultSet selectAllIds() {
        return MySQLConnection.getInstance().executeQuery("SELECT id FROM " + TABLE_TITLE);
    }

    public void selectByType(AnimalSpecies animalSpecies) {
        try (ResultSet rs = MySQLConnection.getInstance().executeQuery(String.format("SELECT * FROM %s WHERE type = '%s'", TABLE_TITLE, animalSpecies))) {
            System.out.printf("%5s %10s %10s %15s %15s %10s%n", "id", "name", "age", "weight", "color", "type");
            System.out.println("-----------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%5d %10s %10d %15s %15s %10s%n",
                        rs.getInt(1), // id
                        rs.getString(2), // name
                        rs.getInt(3), // age
                        rs.getBigDecimal(4).stripTrailingZeros().toPlainString(), // weight
                        rs.getString(5), // color
                        rs.getString(6)); // type
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удается показать данные из таблицы!", e);
        }
    }

    @Override
    public void insertDataIntoTable(Animal animal) {
        MySQLConnection.getInstance().executeUpdate(String.format(Locale.US, "INSERT INTO %s (%s) VALUES ('%s', %d, %f, '%s', '%s')", TABLE_TITLE, String.join(",", LIST_OF_COLUMNS), animal.getName(), animal.getAge(), animal.getWeight(), animal.getColor(), animal.getType()));
    }

    @Override
    public void updateDataInTable(Animal animal, int id) {
        MySQLConnection.getInstance().executeUpdate(String.format(Locale.US, "UPDATE %s SET name = '%s', age = %d, weight = %f, color = '%s', type = '%s' WHERE id = %d", TABLE_TITLE, animal.getName(), animal.getAge(), animal.getWeight(), animal.getColor(), animal.getClass().getSimpleName().toLowerCase(), id));
    }
}