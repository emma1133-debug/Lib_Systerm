package model;

import structure.MyArrayList;

public class BookInventory {
    private final MyArrayList<String> titles = new MyArrayList<>();
    private final MyArrayList<String> authors = new MyArrayList<>();
    private final MyArrayList<Double> prices = new MyArrayList<>();
    private final MyArrayList<Integer> stock = new MyArrayList<>();

    public void addBook(String title, String author, int qty, double price) {
        int index = indexOf(title);
        if (index != -1) {
            stock.set(index, stock.get(index) + qty);
            prices.set(index, price);
            authors.set(index, author);
        } else {
            titles.add(title);
            authors.add(author);
            prices.add(price);
            stock.add(qty);
        }
    }

    public int indexOf(String title) {
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }

    public void reduceStock(String title, int qty) {
        int index = indexOf(title);
        if (index != -1) {
            stock.set(index, stock.get(index) - qty);
        }
    }

    public void increaseStock(String title, int qty) {
        int index = indexOf(title);
        if (index != -1) {
            stock.set(index, stock.get(index) + qty);
        }
    }

    public int getStock(String title) {
        int index = indexOf(title);
        return index != -1 ? stock.get(index) : 0;
    }

    public double getPrice(String title) {
        int index = indexOf(title);
        return index != -1 ? prices.get(index) : 0.0;
    }

    public String getAuthor(String title) {
        int index = indexOf(title);
        return index != -1 ? authors.get(index) : "N/A";
    }

    public MyArrayList<String> getTitles() {
        return titles;
    }


    public void printInventory() {
        System.out.println("\n📚 Available Books:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.printf("%d. %s by %s - $%.2f (%d left)%n",
                    i + 1, titles.get(i), authors.get(i), prices.get(i), stock.get(i));
        }
    }

    public void viewInventory() {
        System.out.println("📚 Current Inventory:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.printf("- %s by %s | Price: %.2f | Stock: %d\n",
                    titles.get(i), authors.get(i), prices.get(i), stock.get(i));
        }
    }

    public void sortByTitle() {
        for (int i = 1; i < titles.size(); i++) {
            String keyTitle = titles.get(i);
            String keyAuthor = authors.get(i);
            Double keyPrice = prices.get(i);
            Integer keyStock = stock.get(i);
            int j = i - 1;
            while (j >= 0 && titles.get(j).compareToIgnoreCase(keyTitle) > 0) {
                titles.set(j + 1, titles.get(j));
                authors.set(j + 1, authors.get(j));
                prices.set(j + 1, prices.get(j));
                stock.set(j + 1, stock.get(j));
                j--;
            }
            titles.set(j + 1, keyTitle);
            authors.set(j + 1, keyAuthor);
            prices.set(j + 1, keyPrice);
            stock.set(j + 1, keyStock);
        }
    }

    public void sortByPrice() {
        for (int i = 1; i < prices.size(); i++) {
            Double keyPrice = prices.get(i);
            String keyTitle = titles.get(i);
            String keyAuthor = authors.get(i);
            Integer keyStock = stock.get(i);
            int j = i - 1;
            while (j >= 0 && prices.get(j) > keyPrice) {
                prices.set(j + 1, prices.get(j));
                titles.set(j + 1, titles.get(j));
                authors.set(j + 1, authors.get(j));
                stock.set(j + 1, stock.get(j));
                j--;
            }
            prices.set(j + 1, keyPrice);
            titles.set(j + 1, keyTitle);
            authors.set(j + 1, keyAuthor);
            stock.set(j + 1, keyStock);
        }
    }

    public void sortByStock() {
        for (int i = 1; i < stock.size(); i++) {
            Integer keyStock = stock.get(i);
            String keyTitle = titles.get(i);
            String keyAuthor = authors.get(i);
            Double keyPrice = prices.get(i);
            int j = i - 1;
            while (j >= 0 && stock.get(j) > keyStock) {
                stock.set(j + 1, stock.get(j));
                titles.set(j + 1, titles.get(j));
                authors.set(j + 1, authors.get(j));
                prices.set(j + 1, prices.get(j));
                j--;
            }
            stock.set(j + 1, keyStock);
            titles.set(j + 1, keyTitle);
            authors.set(j + 1, keyAuthor);
            prices.set(j + 1, keyPrice);
        }
    }
}
