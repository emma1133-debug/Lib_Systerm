package structure;

public class MyStack<T> {
    private final MyArrayList<T> list = new MyArrayList<>();

    public void push(T item) {
        list.add(item);
    }

    public T pop() {
        T item = list.get(size() - 1);
        list.removeAt(size() - 1);
        return item;
    }

    public T peek() {
        return list.get(size() - 1);
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
}
