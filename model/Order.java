package model;

import structure.MyArrayList;

import java.time.LocalDateTime;

public class Order {
    public enum Status { PENDING, COMPLETED, CANCELLED }

    private static int idCounter = 1;

    private final int id;
    private final String customerName;
    private final String address;
    private final String email;
    private final String phone;
    private final MyArrayList<Book> books;
    private final LocalDateTime timestamp;
    private Status status;

    public Order(String customerName, String address, String email, String phone, MyArrayList<Book> books) {
        this.id = idCounter++;
        this.customerName = customerName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.books = books;
        this.timestamp = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public MyArrayList<Book> getBooks() {
        return books;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            total += b.getPrice() * b.getQuantity();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("🧾 Order #").append(id).append(" | Status: ");
        switch (status) {
            case PENDING -> sb.append("⏳ Pending (waiting to be processed)");
            case COMPLETED -> sb.append("✅ Completed");
            case CANCELLED -> sb.append("❌ Cancelled by admin");
        }
        sb.append("\n👤 Customer: ").append(customerName);
        sb.append("\n📩 Email: ").append(email);
        sb.append("\n📞 Phone: ").append(phone);
        sb.append("\n📍 Address: ").append(address);
        sb.append("\n📦 Books: ");
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            sb.append(String.format("\n   • %s by %s | Qty: %d | $%.2f",
                    b.getTitle(), b.getAuthor(), b.getQuantity(), b.getPrice()));
        }
        sb.append(String.format("\n💰 Total: $%.2f", getTotal()));
        sb.append("\n🕒 Placed at:  ").append(timestamp);
        sb.append("\n-----------------------------------------");
        return sb.toString();
    }
}
