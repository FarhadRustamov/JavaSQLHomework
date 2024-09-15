package command_Actions;

import animal_properties.AnimalProperties;
import animals.Animal;
import data.AnimalSpecies;
import factory.AnimalFactory;
import tables_in_MySQL_DB.AnimalsTable;

import java.util.Arrays;
import java.util.Scanner;

public class AddActions {

    public void initiateAdd(Scanner scanner, AnimalsTable animalsTable) {
        int i = 0;
        while (i < 3) {
            System.out.printf("Какое животное вы хотите создать: %s\n", Arrays.toString(AnimalSpecies.values()) + "?");
            String animalString = scanner.nextLine().trim().toUpperCase();
            if (Arrays.stream(AnimalSpecies.values()).map(Object::toString).anyMatch(s -> s.equals(animalString))) {
                Animal animal = AnimalFactory.createAnimal(AnimalSpecies.valueOf(animalString));
                if (AnimalProperties.fillAnimalProperties(animal, scanner)) {
                    animalsTable.insertDataIntoTable(animal);
                    animal.say();
                }
                break;
            } else if (i == 2) {
                System.out.println("Количество попыток исчерпано! Не удалось создать животное!");
                break;
            } else {
                System.out.print("Такого животного не существует! ");
            }
            i++;
        }
    }
}
