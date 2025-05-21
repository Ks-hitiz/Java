package Exercise5;

public class Ecommerce {
    public static void main(String[] args) {

        Product productA = new Product("Product A", 100.0);

        Customer john = new Customer("John Doe");

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(productA);

        Order order = new Order(john, cart);
        order.placeOrder();
    }
}
