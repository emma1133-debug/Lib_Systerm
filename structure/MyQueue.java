package structure;

public class MyQueue<T> {
    private final MyArrayList<T> list = new MyArrayList<>();

    public void enqueue(T item) {   //Undo order
        list.add(item);
    }

    public T dequeue() {
        T item = list.get(0);
        list.removeAt(0);
        return item;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
