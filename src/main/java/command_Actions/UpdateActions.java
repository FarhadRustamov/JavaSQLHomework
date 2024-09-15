package command_Actions;

import animal_properties.AnimalProperties;
import animals.Animal;
import data.AnimalSpecies;
import factory.AnimalFactory;
import tables_in_MySQL_DB.AnimalsTable;
import utils.NumberValidator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UpdateActions {

    public void initiateUpdate(Scanner scanner, AnimalsTable animalsTable) {
        if (animalsTable.checkIfAnimalsTableEmpty() == 0) {
            System.out.println("Пока ни одного животного не было создано и обновлять нечего!");
        } else {
            int i = 0;
            outerLoop:
            while (i < 3) {
                List<Integer> listOfIds = new ArrayList<>();
                try (ResultSet rs = animalsTable.selectAllIds()) {
                    while (rs.next()) {
                        listOfIds.add(rs.getInt(1));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Не удалось получить все id-s!", e);
                }
                System.out.printf("Введите id того животного, которое вы хотите обновить: %s\n", listOfIds);
                String enteredId = scanner.nextLine().trim().toLowerCase();
                NumberValidator numberValidator = new NumberValidator();
                if (numberValidator.validateNumber(enteredId)) {
                    int id = Integer.parseInt(enteredId);
                    if (listOfIds.contains(id)) {
                        int j = 0;
                        while (j < 3) {
                            System.out.printf("Каким животным вы хотите заменить уже существующее: %s\n", Arrays.toString(AnimalSpecies.values()) + "?");
                            String animalString = scanner.nextLine().trim().toUpperCase();
                            if (Arrays.stream(AnimalSpecies.values()).map(Object::toString).anyMatch(s -> s.equals(animalString))) {
                                Animal animal = AnimalFactory.createAnimal(AnimalSpecies.valueOf(animalString));
                                if (AnimalProperties.fillAnimalProperties(animal, scanner)) {
                                    animalsTable.updateDataInTable(animal, id);
                                    System.out.println("Ваше животное обновлено!");
                                }
                                break outerLoop;
                            } else if (j == 2) {
                                System.out.println("Количество попыток исчерпано! Не удалось обновить животное!");
                                break outerLoop;
                            } else {
                                System.out.print("Такого животного не существует! ");
                            }
                            j++;
                        }
                    } else if (i == 2) {
                        System.out.println("Количество попыток исчерпано!");
                        break;
                    } else {
                        System.out.print("Такого id не существует в БД! ");
                    }
                } else if (i == 2) {
                    System.out.println("Количество попыток исчерпано! Не удалось обновить животное!");
                    break;
                } else {
                    System.out.print("Введите целое число! ");
                }
                i++;
            }
        }
    }
}
