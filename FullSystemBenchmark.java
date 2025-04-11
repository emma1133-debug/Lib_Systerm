import model.*;
import service.OrderManager;
import structure.*;
import algorithm.*;

/**
 * FullSystemBenchmark.java
 *
 * This class performs comprehensive benchmarking of the major functionalities in the system.
 * It evaluates sorting, searching, order processing, and array list operations under large-scale data.
 * The goal is to validate algorithmic complexity through empirical execution time and memory usage.
 *
 * Author: Emma (Benchmark Edition)
 */
public class FullSystemBenchmark {

    public static void main(String[] args) {
        BookInventory inventory = new BookInventory();
        OrderManager manager = new OrderManager();
        MyArrayList<Book> searchList = new MyArrayList<>();
        long globalStart = System.currentTimeMillis();

        System.out.println("\n================= 📊 FULL SYSTEM BENCHMARK START =================\n");

        // ------------------------- BOOK INVENTORY SETUP -------------------------
        System.out.println("📦 Generating 50,000 sample books...");
        for (int i = 0; i < 50000; i++) {
            String title = "Book " + i;
            String author = "Author " + i;
            double price = Math.random() * 100;
            int qty = 1 + (int) (Math.random() * 10);
            inventory.addBook(title, author, qty, price);
            searchList.add(new Book(title, author, qty, price));
        }
        System.out.println("✅ Books generated successfully.\n");

        // ------------------------- SORTING BENCHMARKS -------------------------
        System.out.println("🔽 SORTING PERFORMANCE:");
        benchmark("sortByTitle() [50k items]", () -> inventory.sortByTitle());
        benchmark("sortByPrice() [50k items]", () -> inventory.sortByPrice());
        benchmark("sortByStock() [50k items]", () -> inventory.sortByStock());
        System.out.println();

        // ------------------------- SEARCHING BENCHMARKS -------------------------
        System.out.println("🔍 LINEAR SEARCH TESTS:");
        String[] keywords = { "Book 0", "Book 10000", "Book 49999", "Missing Book" };
        for (String keyword : keywords) {
            benchmark("searchByTitle(\"" + keyword + "\")", () -> {
                Book result = LinearSearch.searchByTitle(searchList, keyword);
                if (result == null && !keyword.equals("Missing Book")) {
                    System.out.println("⚠️  Unexpected: \"" + keyword + "\" not found.");
                }
            });
        }
        System.out.println();

        // ------------------------- ORDER PROCESSING BENCHMARK -------------------------
        System.out.println("📦 ORDER PROCESSING:");
        MyArrayList<Book> cart = new MyArrayList<>();
        cart.add(new Book("Book 123", "Author 123", 1, 15.0));

        benchmark("placeOrder() x1000", () -> {
            for (int i = 0; i < 1000; i++) {
                Order o = new Order("user" + i, "Hanoi", "u" + i + "@gmail.com", "+8412345678", cart);
                manager.placeOrder(o);
            }
        });

        benchmark("undoLastOrder() x100", () -> {
            for (int i = 0; i < 100; i++) {
                manager.undoLastOrder();
            }
        });
        System.out.println();


        // ------------------------- FINAL SUMMARY -------------------------
        long globalEnd = System.currentTimeMillis();
        long usedMemoryMB = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);

        System.out.println("\n================= ✅ BENCHMARK COMPLETE =================");
        System.out.println("🕒 Total Execution Time: " + (globalEnd - globalStart) + " ms");
        System.out.println("🧠 Approx. Memory Used:  " + usedMemoryMB + " MB");
        System.out.println("=========================================================");
    }

    /**
     * Measures and prints execution time for a labeled task.
     * @param label Description of the task.
     * @param task  Code block to benchmark.
     */
    private static void benchmark(String label, Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        System.out.printf("%-40s: %4d ms%n", label, (end - start));
    }
}
