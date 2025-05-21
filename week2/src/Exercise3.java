public class Exercise3 {
    public static void main(String[] args){
        Shapes circle = new Circles(5);
        System.out.println("Circle");
        System.out.println("Area: " + circle.area());
        System.out.println("Perimeter: " + circle.perimeter());

        Shapes rectangle = new Rectangles(4, 6);
        System.out.println("Rectangle");
        System.out.println("Area: " + rectangle.area());
        System.out.println("Perimeter: " + rectangle.perimeter());
    }
}

abstract class Shapes{
    abstract double area();
    abstract double perimeter();
}

class Circles extends Shapes{
    static final double PI = 3.14;
    double radius;
    public Circles(double n){
        this.radius = n;
    }
    double area(){
        return (PI * (radius * radius));
    }
    double perimeter(){
        return (2 * PI * radius);
    }
}

class Rectangles extends Shapes{
    double l;
    double b;
    public Rectangles(double l,double b){
        this.l = l;
        this.b = b;
    }
    double area(){
        return (l * b);
    }
    double perimeter(){
        return 2 * (l + b);
    }
}

class Triangles extends Shapes{
    double a,b,c;
    public Triangles (double a,double b,double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    double area(){
        return (a + b + c);
    }
    double perimeter(){
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}