import model.*;
import structure.*;
import service.*;
import algorithm.*;

public class Main {
    private static final BookInventory inventory = new BookInventory();
    private static final OrderManager manager = new OrderManager();
    private static final MyArrayList<UserInfo> userInfoList = new MyArrayList<>();

    public static void main(String[] args) {
        inventory.addBook("Java 101", "John Smith", 5, 15.00);
        inventory.addBook("Algorithms", "Thomas Cormen", 7, 22.50);
        inventory.addBook("Clean Code", "Robert C. Martin", 10, 25.99);

        while (true) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("🌟 WELCOME TO ONLINE BOOKSTORE 🌟");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            String username;
            do {
                System.out.print("🔐 Enter username (or type 'exit' to quit):\n> ");
                username = MyInputReader.nextLine().trim();
                if (username.isBlank()) {
                    System.out.println("❌ Username cannot be blank.");
                }
            } while (username.isBlank());

            if (username.equalsIgnoreCase("exit")) {
                System.out.println("👋 Exiting system. Goodbye!");
                break;
            }

            if (username.equalsIgnoreCase("admin")) {
                System.out.println("You are now in ADMIN mode.");
                runAdminMode();
            } else {
                System.out.println("👋 Welcome, " + username + "! You're now in USER mode.");
                runUserMode(username);
            }
        }
    }

    private static UserInfo findUser(String username) {
        for (int i = 0; i < userInfoList.size(); i++) {
            if (userInfoList.get(i).getUsername().equalsIgnoreCase(username)) {
                return userInfoList.get(i);
            }
        }
        return null;
    }

    private static void runUserMode(String username) {
        while (true) {
            System.out.println("""
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔷 USER MENU
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. Browse and place order
2. View my orders
3. Search my order by ID
4. Search book by name
0. Logout
""");
            System.out.print("🔎 Select option (or M to view menu): ");
            String choice = MyInputReader.nextLine().trim();

            switch (choice) {
                case "1" -> placeOrder(username);
                case "2" -> manager.printOrdersByUser(username);
                case "3" -> {
                    int id = MyInputReader.nextInt("🔍 Enter Order ID to search: ");
                    Order found = manager.findOrder(id);
                    if (found != null && found.getCustomerName().equalsIgnoreCase(username)) {
                        System.out.println(found);
                    } else {
                        System.out.println("❌ Order not found.");
                    }
                }
                case "4" -> searchBookFromInventory();
                case "0" -> {
                    System.out.println("🚪 Logging out... Returning to main screen.");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option");
            }
        }
    }

    private static void runAdminMode() {
        while (true) {
            System.out.println("""
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔷 ADMIN MENU
━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. Process next order
2. Undo last processed order
3. Cancel an order
4. View all orders
5. View orders by status
6. Search any order by ID
7. View inventory
8. Add/update inventory
9. Sort inventory
0. Logout
""");
            System.out.print("🔎 Select option (or M to view menu): ");
            String choice = MyInputReader.nextLine().trim();

            switch (choice) {
                case "1" -> manager.processNextOrder();
                case "2" -> manager.undoLastOrder();
                case "3" -> {
                    System.out.println("🗑️ Orders you can cancel:");
                    manager.printOrdersByStatus(Order.Status.PENDING);
                    int id = MyInputReader.nextInt("Enter Order ID to cancel: ");
                    manager.cancelOrder(id);
                }
                case "4" -> manager.printAllOrders();
                case "5" -> {
                    System.out.println("1. PENDING\n2. COMPLETED\n3. CANCELLED");
                    int s = MyInputReader.nextInt("Enter status: ");
                    manager.printOrdersByStatus(Order.Status.values()[s - 1]);
                }
                case "6" -> {
                    int id = MyInputReader.nextInt("Enter Order ID to search: ");
                    Order o = manager.findOrder(id);
                    if (o != null) System.out.println(o);
                    else System.out.println("❌ Order not found.");
                }
                case "7" -> inventory.printInventory();
                case "8" -> {
                    System.out.println("📦 Add or update inventory:");
                    while (true) {
                        System.out.print("📘 Enter book title (or '0' to finish): ");
                        String title = MyInputReader.nextLine().trim();
                        if (title.equals("0")) break;

                        if (title.isBlank() || title.matches("^\\d+$")) {
                            System.out.println("❌ Invalid title. It must not be blank or just numbers.");
                            continue;
                        }

                        System.out.print("✍️ Author: ");
                        String author = MyInputReader.nextLine().trim();
                        if (author.isBlank() || author.matches("^\\d+$")) {
                            System.out.println("❌ Invalid author. It must not be blank or just numbers.");
                            continue;
                        }

                        int qty = MyInputReader.nextInt("🔢 Quantity (must be > 0): ");
                        if (qty <= 0) {
                            System.out.println("❌ Quantity must be greater than 0.");
                            continue;
                        }

                        double price = MyInputReader.nextDouble("💵 Price (must be > 0): ");
                        if (price <= 0) {
                            System.out.println("❌ Price must be greater than 0.");
                            continue;
                        }

                        inventory.addBook(title, author, qty, price);
                        System.out.printf("✅ Book added/updated: %s by %s (%d copies at $%.2f)\n", title, author, qty, price);
                    }
                }
                case "9" -> {
                    System.out.println("Sort inventory by:");
                    System.out.println("1. Title (A-Z)");
                    System.out.println("2. Price (Low to High)");
                    System.out.println("3. Stock (Low to High)");
                    System.out.print("👉 Select sort option: ");
                    String sortOption = MyInputReader.nextLine().trim();

                    switch (sortOption) {
                        case "1" -> {
                            inventory.sortByTitle();
                            System.out.println("✅ Inventory sorted by title.");
                        }
                        case "2" -> {
                            inventory.sortByPrice();
                            System.out.println("✅ Inventory sorted by price.");
                        }
                        case "3" -> {
                            inventory.sortByStock();
                            System.out.println("✅ Inventory sorted by stock.");
                        }
                        default -> System.out.println("❌ Invalid sort option.");
                    }

                    inventory.viewInventory();
                }
                case "0" -> {
                    System.out.println("🚪 Logging out... Returning to main screen.");
                    return;
                }
            }
        }
    }

    private static void placeOrder(String username) {
        String email, phone, address;

        UserInfo info = findUser(username);
        if (info != null) {
            email = info.getEmail();
            phone = info.getPhone();
            address = info.getAddress();
        } else {
            System.out.print("📩 Enter email (must end with @gmail.com): ");
            email = MyInputReader.nextLine().trim();
            while (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                System.out.print("❌ Invalid email. Please enter a valid @gmail.com address: ");
                email = MyInputReader.nextLine().trim();
            }

            System.out.print("📞 Enter phone number (digits only): +84 ");
            phone = MyInputReader.nextLine().trim();
            while (!phone.matches("\\d{9,11}")) {
                System.out.print("❌ Invalid phone. Must be 9–11 digits. Try again: +84 ");
                phone = MyInputReader.nextLine().trim();
            }
            phone = "+84" + phone;

            System.out.print("📍 Enter shipping address: ");
            address = MyInputReader.nextLine().trim();
            userInfoList.add(new UserInfo(username, email, phone, address));
        }

        MyArrayList<Book> cart = new MyArrayList<>();
        while (true) {
            printCart(cart);
            inventory.printInventory();

            int index = MyInputReader.nextInt("Select book by number (0 to finish, -1 to cancel): ") - 1;
            if (index == -2) return;
            if (index == -1) break;

            if (index < 0 || index >= inventory.getTitles().size()) {
                System.out.println("⚠️ Invalid selection.");
                continue;
            }

            String title = inventory.getTitles().get(index);
            String author = inventory.getAuthor(title);
            double price = inventory.getPrice(title);
            int maxQty = inventory.getStock(title);

            // Trừ tạm số đã chọn trong giỏ
            for (int i = 0; i < cart.size(); i++) {
                Book b = cart.get(i);
                if (b.getTitle().equals(title)) {
                    maxQty -= b.getQuantity();
                }
            }

            if (maxQty == 0) {
                System.out.println("🚫 Out of stock!");
                continue;
            }

            int qty = MyInputReader.nextInt("Quantity (max " + maxQty + "): ");
            if (qty <= 0 || qty > maxQty) {
                System.out.println("⚠️ Invalid quantity.");
                continue;
            }

            cart.add(new Book(title, author, qty, price));
        }

        if (!cart.isEmpty()) {
            Order order = new Order(username, address, email, phone, cart);
            InsertionSort.sort(order.getBooks());
            manager.placeOrder(order);

            for (int i = 0; i < cart.size(); i++) {
                Book b = cart.get(i);
                inventory.reduceStock(b.getTitle(), b.getQuantity());
            }

            System.out.println("📝 Order placed: #" + order.getId() + " (" + cart.size() + " books)");
        }
    }

    private static void searchBookFromInventory() {
        System.out.print("🔎 Enter book title to search: ");
        String keyword = MyInputReader.nextLine().trim();

        MyArrayList<Book> tempList = new MyArrayList<>();
        for (int i = 0; i < inventory.getTitles().size(); i++) {
            String title = inventory.getTitles().get(i);
            String author = inventory.getAuthor(title);
            int qty = inventory.getStock(title);
            double price = inventory.getPrice(title);
            tempList.add(new Book(title, author, qty, price));
        }

        Book found = LinearSearch.searchByTitle(tempList, keyword);
        if (found != null) {
            System.out.printf("✅ Found: %s by %s | Price: $%.2f | In stock: %d\n",
                    found.getTitle(), found.getAuthor(), found.getPrice(), found.getQuantity());
        } else {
            System.out.println("❌ Book not found.");
        }
    }

    private static void printCart(MyArrayList<Book> cart) {
        System.out.println("\n🛒 Your Cart:");
        if (cart.isEmpty()) {
            System.out.println("   (empty)");
            return;
        }

        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            Book b = cart.get(i);
            double sub = b.getQuantity() * b.getPrice();
            System.out.printf("   • %s by %s | Qty: %d | $%.2f each | Subtotal: $%.2f\n",
                    b.getTitle(), b.getAuthor(), b.getQuantity(), b.getPrice(), sub);
            total += sub;
        }
        System.out.printf("💵 Total: $%.2f\n", total);
    }
}
