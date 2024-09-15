package main_console;

import data.Command;
import dbConnect.MySQLConnection;
import tables_in_MySQL_DB.AnimalsTable;
import command_Actions.AddActions;
import command_Actions.ListActions;
import command_Actions.UpdateActions;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnimalsTable animalsTable = new AnimalsTable();
        animalsTable.createAnimalsTable();
        AddActions addActions = new AddActions();
        ListActions listActions = new ListActions();
        UpdateActions updateActions = new UpdateActions();
        Command command;
        while (true) {
            System.out.printf("Пожалуйста, введите одну из команд: %s\n", Arrays.toString(Command.values()));
            String commandString = scanner.nextLine().trim().toUpperCase();
            if (Arrays.stream(Command.values()).map(Object::toString).anyMatch(s -> s.equals(commandString))) { // Вначале создает поток из массива энамов, потом каждый элемент преобразует в стринг (чтобы с искомым элементом были одного типа), а затем совершает поиск среди этого массива
                command = Command.valueOf(commandString);
                switch (command) {
                    case ADD:
                        addActions.initiateAdd(scanner, animalsTable);
                        break;
                    case LIST:
                        if (listActions.initiateList(scanner, animalsTable)) {
                            continue;
                        }
                        break;
                    case UPDATE:
                        updateActions.initiateUpdate(scanner, animalsTable);
                        break;
                    case EXIT:
                        scanner.close();
                        animalsTable.dropAnimalsTable();
                        MySQLConnection.getInstance().closeConnection();
                        System.exit(0);
                }
            } else {
                System.out.print("Неверная команда! ");
            }
        }
    }
}

