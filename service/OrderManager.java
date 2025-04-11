package service;

import model.Book;
import model.Order;
import model.BookInventory;
import structure.MyArrayList;
import structure.MyQueue;
import structure.MyStack;

public class OrderManager {
    private final MyQueue<Order> orderQueue = new MyQueue<>();  //Process next order
    private final MyStack<Order> processedStack = new MyStack<>();
    private final MyArrayList<Order> allOrders = new MyArrayList<>();
    private final BookInventory inventory = new BookInventory(); // used for rollback

    public void placeOrder(Order order) {
        orderQueue.enqueue(order);
        allOrders.add(order);
        System.out.println("📝 Order placed: #" + order.getId() + " (" + order.getBooks().size() + " books)");
    }

    public void processNextOrder() {
        if (orderQueue.isEmpty()) {
            System.out.println("📭 No pending orders.");
            return;
        }
        Order order = orderQueue.dequeue();
        System.out.println("🔄 Next order in queue:\n");
        System.out.println(order);

        System.out.print("❓ Do you want to process this order? (Y/N): ");
        String confirm = new java.util.Scanner(System.in).nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            order.setStatus(Order.Status.COMPLETED);
            processedStack.push(order);
            System.out.println("✅ Order processed.");
        } else {
            orderQueue.enqueue(order);
            System.out.println("⏸️ Order not processed.");
        }
    }

    public void undoLastOrder() {
        if (processedStack.isEmpty()) {
            System.out.println("🔁 No orders to undo.");
            return;
        }

        Order last = processedStack.pop();
        last.setStatus(Order.Status.PENDING);
        orderQueue.enqueue(last);

        for (int i = 0; i < last.getBooks().size(); i++) {
            Book b = last.getBooks().get(i);
            inventory.increaseStock(b.getTitle(), b.getQuantity());
        }

        System.out.println("↩️ Last processed order undone: #" + last.getId());
    }

    public void cancelOrder(int id) {
        Order o = findOrder(id);
        if (o == null || o.getStatus() != Order.Status.PENDING) {
            System.out.println("❌ Order not found or not cancellable.");
            return;
        }

        o.setStatus(Order.Status.CANCELLED);
        for (int i = 0; i < o.getBooks().size(); i++) {
            Book b = o.getBooks().get(i);
            inventory.increaseStock(b.getTitle(), b.getQuantity());
        }

        System.out.println("❌ Order cancelled: #" + id);
    }

    public void printAllOrders() {  //View all orders
        if (allOrders.isEmpty()) {
            System.out.println("📭 No orders found.");
            return;
        }

        for (int i = 0; i < allOrders.size(); i++) {
            System.out.println(allOrders.get(i));
        }
    }

    public void printOrdersByUser(String username) {  //Only user can
        boolean found = false;
        for (int i = 0; i < allOrders.size(); i++) {
            Order o = allOrders.get(i);
            if (o.getCustomerName().equalsIgnoreCase(username)) {
                System.out.println(o);
                found = true;
            }
        }
        if (!found) {
            System.out.println("📭 You have no orders.");
        }
    }

    public void printOrdersByStatus(Order.Status status) {
        boolean found = false;
        for (int i = 0; i < allOrders.size(); i++) {
            Order o = allOrders.get(i);
            if (o.getStatus() == status) {
                System.out.println(o);
                found = true;
            }
        }
        if (!found) {
            System.out.println("📭 No orders with status: " + status);
        }
    }

    public Order findOrder(int id) {  //find by id but with valid user name
        for (int i = 0; i < allOrders.size(); i++) {
            if (allOrders.get(i).getId() == id) {
                return allOrders.get(i);
            }
        }
        return null;
    }
}
