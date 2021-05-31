package wraith.automato.utils;

import java.util.ArrayList;
import java.util.Collection;

public class UniqueArray<T> extends ArrayList<T> {

    public UniqueArray(int initialCapacity) {
        super(initialCapacity);
    }

    public UniqueArray() {
        super();
    }

    public UniqueArray(Collection<? extends T> collection) {
        this.addAll(collection);
    }

    @Override
    public boolean add(T element) {
        if (contains(element)) {
            return false;
        }
        return super.add(element);
    }

    @Override
    public void add(int index, T element) {
        if (contains(element)) {
            return;
        }
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        ++modCount;
        for (T element : collection) {
            this.add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        rangeCheckForAdd(index);
        ++modCount;
        int i = 0;
        for (T element : collection) {
            this.add(index + i, element);
        }
        return true;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }

}
