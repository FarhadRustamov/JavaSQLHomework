package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException("Не получилось загрузить данные из файла: " + filePath, e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден: " + filePath, e);
        }
        return properties;
    }
}
