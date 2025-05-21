public class Exercise7 {
    public static void main(String[] args) {
        Shapee circle = new Circlee(5);
        Shapee rectangle = new Rectanglee(4, 6);

        System.out.println("Circle:");
        System.out.println("Area: " + circle.area());
        System.out.println("Perimeter: " + circle.perimeter());

        System.out.println("\nRectangle:");
        System.out.println("Area: " + rectangle.area());
        System.out.println("Perimeter: " + rectangle.perimeter());
    }
}

abstract class Shapee{
    abstract double area();
    abstract double perimeter();
}
class Circlee extends Shapee {
    private double radius;

    public Circlee(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}
class Rectanglee extends Shapee {
    private double l;
    private double b;

    public Rectanglee(double length, double width) {
        this.l = length;
        this.b = width;
    }

    @Override
    public double area() {
        return l * b;
    }

    @Override
    public double perimeter() {
        return 2 * (l + b);
    }
}
class Trianglee extends Shapee {
    private double a, b, c; // sides of the triangle

    public Trianglee(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }
}
