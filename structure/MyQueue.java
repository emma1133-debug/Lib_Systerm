package structure;

public class MyQueue<T> {
    private final MyArrayList<T> list = new MyArrayList<>();

    public void enqueue(T item) {
        list.add(item);
    }

    public T dequeue() {
        T item = list.get(0);
        list.removeAt(0);
        return item;
    }

    public T peek() {
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return list.get(index);
    }

    public void removeAt(int index) {
        list.removeAt(index);
    }
}
