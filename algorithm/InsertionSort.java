package algorithm;

import model.Book;
import structure.MyArrayList;

public class InsertionSort {

    public static void sort(MyArrayList<Book> books) {
        for (int i = 1; i < books.size(); i++) {
            Book key = books.get(i);
            int j = i - 1;
            while (j >= 0 && books.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books.set(j + 1, books.get(j));
                j--;
            }
            books.set(j + 1, key);
        }
    }

    public static void sortByTitle(MyArrayList<String> titles, MyArrayList<Double> prices, MyArrayList<Integer> stock) {
        for (int i = 1; i < titles.size(); i++) {
            String tKey = titles.get(i);
            Double pKey = prices.get(i);
            Integer sKey = stock.get(i);

            int j = i - 1;
            while (j >= 0 && titles.get(j).compareToIgnoreCase(tKey) > 0) {
                titles.set(j + 1, titles.get(j));
                prices.set(j + 1, prices.get(j));
                stock.set(j + 1, stock.get(j));
                j--;
            }
            titles.set(j + 1, tKey);
            prices.set(j + 1, pKey);
            stock.set(j + 1, sKey);
        }
    }

    public static void sortByPrice(MyArrayList<String> titles, MyArrayList<Double> prices, MyArrayList<Integer> stock) {
        for (int i = 1; i < prices.size(); i++) {
            Double pKey = prices.get(i);
            String tKey = titles.get(i);
            Integer sKey = stock.get(i);

            int j = i - 1;
            while (j >= 0 && prices.get(j) > pKey) {
                prices.set(j + 1, prices.get(j));
                titles.set(j + 1, titles.get(j));
                stock.set(j + 1, stock.get(j));
                j--;
            }
            prices.set(j + 1, pKey);
            titles.set(j + 1, tKey);
            stock.set(j + 1, sKey);
        }
    }

    public static void sortByStock(MyArrayList<String> titles, MyArrayList<Double> prices, MyArrayList<Integer> stock) {
        for (int i = 1; i < stock.size(); i++) {
            Integer sKey = stock.get(i);
            String tKey = titles.get(i);
            Double pKey = prices.get(i);

            int j = i - 1;
            while (j >= 0 && stock.get(j) > sKey) {
                stock.set(j + 1, stock.get(j));
                titles.set(j + 1, titles.get(j));
                prices.set(j + 1, prices.get(j));
                j--;
            }
            stock.set(j + 1, sKey);
            titles.set(j + 1, tKey);
            prices.set(j + 1, pKey);
        }
    }
}
