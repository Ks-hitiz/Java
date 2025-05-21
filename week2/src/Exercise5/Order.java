package Exercise5;

public class Order {
    private Customer customer;
    private ShoppingCart cart;

    public Order(Customer customer, ShoppingCart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public void placeOrder() {
        System.out.println("Order placed for " + customer.getName());
        System.out.println("Items:");
        for (Product p : cart.getProducts()) {
            System.out.println(">> " + p);
        }
        System.out.println("Total: $" + cart.getTotalPrice());
    }
}
