package net.nonswag.tnl.core.api.object;

import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Objects;

public class AsyncList<E> implements Iterable<E> {

    private int size = 0;
    @Nonnull
    private Object[] elements = new Object[10];

    @SafeVarargs
    public AsyncList(@Nonnull E... e) {
        size = e.length;
        elements = e;
    }

    public AsyncList() {
    }

    public boolean add(@Nullable E e) {
        if (size == elements.length) ensureCapacity();
        elements[size++] = e;
        return true;
    }

    public void remove(@Nonnull E e) {
        remove(indexOf(e));
    }

    public void clear() {
        this.size = 0;
        elements = new Object[10];
    }

    @Nullable
    public E get(int i) {
        return i >= size || i < 0 ? null : (E) elements[i];
    }

    public void remove(int i) {
        if (i >= size || i < 0) return;
        Object item = elements[i];
        int index = elements.length - (i + 1);
        System.arraycopy(elements, i + 1, elements, i, index);
        size--;
    }

    public int indexOf(@Nullable E e) {
        AsyncIterator iterator = iterator();
        while (iterator.hasNext()) if (Objects.equals(iterator.next(), e)) return iterator.getIndex();
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public boolean contains(@Nullable E e) {
        return indexOf(e) >= 0;
    }

    @Override
    public AsyncIterator iterator() {
        return new AsyncIterator();
    }

    @Getter
    public class AsyncIterator implements Iterator<E> {
        private int index;

        public boolean hasNext() {
            return index < size;
        }

        @Nullable
        @Override
        public E next() {
            if (index >= size) throw new NoSuchElementException();
            return (E) elements[index++];
        }

        public void remove() {
            AsyncList.this.remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}
