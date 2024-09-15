package animals;

public abstract class Animal {
    private int id;
    private String name;
    private int age;
    private double weight;
    private String color;
    private String type;

    public void say() {
        System.out.println("Я говорю");
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void drink() {
        System.out.println("Я пью");
    }

    public void eat() {
        System.out.println("Я ем");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String getAgeDefinition() {
        if (age % 100 == 11 || age % 100 == 12 || age % 100 == 13 || age % 100 == 14) {
            return " лет"; // внчале всегда проверяю возраст на предмет исключения от 11 до 14
        } else if (age % 10 == 1) {
            return " год";
        } else if (age % 10 == 2 || age % 10 == 3 || age % 10 == 4) {
            return " года";
        } else {
            return " лет";
        }
    }

    @Override
    public String toString() {
        return "Привет! Меня зовут " + name + ", мой id в БД - " + id + ", мой тип - " + type + ", мне " + age + getAgeDefinition() + ", я вешу - " + weight + " кг, мой цвет - " + color + ".";
    }
}