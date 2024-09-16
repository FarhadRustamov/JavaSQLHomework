package animals;

import capabilities.Flyable;

public class Duck extends Animal implements Flyable {

    @Override
    public void say() {
        System.out.println("Кря!");
    }

    @Override
    public void fly() {
        System.out.println("Я лечу!");
    }
}
