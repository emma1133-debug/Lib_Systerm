package algorithm;

import model.Book;
import structure.MyArrayList;

public class LinearSearch {
    public static Book searchByTitle(MyArrayList<Book> books, String keyword) {
        for (int i = 0; i < books.size(); i++) {
            String title = books.get(i).getTitle();
            if (title.toLowerCase().contains(keyword.toLowerCase())) {
                return books.get(i);
            }
        }
        return null;
    }
}
