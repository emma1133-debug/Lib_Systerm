package algorithm;

import model.Book;
import structure.MyArrayList;

public class LinearSearch {
    public static Book searchByTitle(MyArrayList<Book> books, String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                return books.get(i);
            }
        }
        return null;
    }
}