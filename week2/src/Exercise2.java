public class Exercise2 {
    public static void main(String[] args) {
        Shape redCircle = new Circle("red");
        Shape blueRectangle = new Rectangle("blue");

        System.out.println(redCircle.describe());
        System.out.println(blueRectangle.describe());
    }
}

class Shape {
    String name;
    String color;

    public Shape(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String describe() {
        return "This is a " + color + " " + name + ".";
    }
}

class Circle extends Shape {
    public Circle(String color) {
        super("circle", color);
    }
}

class Rectangle extends Shape {
    public Rectangle(String color) {
        super("rectangle", color);
    }
}
