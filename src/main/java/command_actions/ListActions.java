package command_actions;

import data.AnimalSpecies;
import data.Demo;
import tables_in_mysql_db.AnimalsTable;

import java.util.Arrays;
import java.util.Scanner;

public class ListActions {

    public boolean initiateList(Scanner scanner, AnimalsTable animalsTable) {
        if (animalsTable.checkIfAnimalsTableEmpty() == 0) {
            System.out.println("Пока ни одного животного не было создано!");
            return true;
        } else {
            int i = 0;
            loop:
            while (i < 3) {
                System.out.printf("Если хотите увидеть всех животных, введите \"%s\", если определенный тип, то введите \"%s\"\n", Demo.ALL, Demo.TYPE);
                String enteredOption = scanner.nextLine().trim().toUpperCase();
                if (Arrays.stream(Demo.values()).map(Object::toString).anyMatch(s -> s.equals(enteredOption))) {
                    Demo option = Demo.valueOf(enteredOption);
                    switch (option) {
                        case ALL:
                            animalsTable.selectAllFromAnimalsTable();
                            break loop;
                        case TYPE:
                            int j = 0;
                            while (j < 3) {
                                System.out.printf("Введите тип животных, которые вы хотите увидеть: %s\n", Arrays.toString(AnimalSpecies.values()));
                                String enteredType = scanner.nextLine().trim().toUpperCase();
                                if (Arrays.stream(AnimalSpecies.values()).map(Object::toString).anyMatch(s -> s.equals(enteredType))) {
                                    AnimalSpecies animalSpecies = AnimalSpecies.valueOf(enteredType);
                                    animalsTable.selectByType(animalSpecies);
                                    break loop;
                                } else if (j == 2) {
                                    System.out.println("Количество попыток исчерпано!");
                                    break loop;
                                } else {
                                    System.out.print("Такого типа нет! ");
                                }
                                j++;
                            }
                    }
                } else if (i == 2) {
                    System.out.println("Количество попыток исчерпано!");
                    break;
                } else {
                    System.out.print("Такого выбора нет! ");
                }
                i++;
            }
            return false;
        }
    }
}
