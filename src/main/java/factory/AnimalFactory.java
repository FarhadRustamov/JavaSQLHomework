package factory;

import animals.Animal;
import animals.Cat;
import animals.Dog;
import animals.Duck;
import data.AnimalSpecies;

public class AnimalFactory {

    public static Animal createAnimal(AnimalSpecies animalSpecies) {
        switch (animalSpecies) {
            case CAT:
                return new Cat();
            case DOG:
                return new Dog();
            case DUCK:
                return new Duck();
            default:
                throw new RuntimeException();
        }
    }
}
