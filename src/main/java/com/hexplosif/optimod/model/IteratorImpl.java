package com.hexplosif.optimod.model;
import java.util.List;

public class IteratorImpl<T> implements Iterator<T> {
    private final List<T> items;
    private int position = 0;

    public IteratorImpl(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return position < items.size();
    }

    @Override
    public T next() {
        if (this.hasNext()) {
            return items.get(position++);
        }
        throw new IllegalStateException("No more elements in the collection");
    }
}
