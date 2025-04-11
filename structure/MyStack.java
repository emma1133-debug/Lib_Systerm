package structure;

public class MyStack<T> {
    private final MyArrayList<T> list = new MyArrayList<>();

    public void push(T item) {  //FIFO
        list.add(item);
    }

    public T pop() {  //LAST
        T item = list.get(size() - 1);
        list.removeAt(size() - 1);
        return item;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
