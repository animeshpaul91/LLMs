package models;

public class GenericPrinter <T> {
    private T item;

    public GenericPrinter() {
    }

    public GenericPrinter(final T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(final T item) {
        this.item = item;
    }

    public void print() {
        System.out.println(item);
    }
}
