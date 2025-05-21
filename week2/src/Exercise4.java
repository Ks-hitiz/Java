public class Exercise4 {
    public static void main(String[] args) {
        Drawable d1 = new Circ();
        Drawable d2 = new Rect();
        Drawable d3 = new Tri();

        d1.draw();
        d2.draw();
        d3.draw();
    }
}

interface Drawable{
    void draw();
}
class Circ implements Drawable {
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Rect implements Drawable {
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

class Tri implements Drawable {
    public void draw() {
        System.out.println("Drawing a Triangle");
    }
}
