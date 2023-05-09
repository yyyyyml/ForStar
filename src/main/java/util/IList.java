package util;

import java.util.Iterator;

/**
 * 侵入式链表实现
 * @param <T>
 * @param <P>
 */
public class IList<T, P> implements Iterable<IList.INode<T, P>> {
    // 链表中的节点类
    public static class INode<T, P> {
        private T element;
        private INode<T, P> prev;
        private INode<T, P> next;
        private IList<T, P> parentList = null;

        public INode(T element, INode<T, P> prev, INode<T, P> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public INode<T, P> getPrev() {
            return prev;
        }

        public void setPrev(INode<T, P> prev) {
            this.prev = prev;
        }

        public INode<T, P> getNext() {
            return next;
        }

        public void setNext(INode<T, P> next) {
            this.next = next;
        }

        public IList<T, P> getParentList() {
            return parentList;
        }

        public void setParent(IList<T, P> parentList) {
            this.parentList = parentList;
        }
    }

    private final INode<T, P> head;
    private final INode<T, P> tail;
    private int size;
    private P val;

    public IList(P val) {
        head = new INode<>(null, null, null);
        tail = new INode<>(null, head, null);
        head.setNext(tail);
        size = 0;
        this.val = val;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public INode<T, P> getHead() {
        return head;
    }

    public INode<T, P> getTail() {
        return tail;
    }

    public P getVal() {
        return val;
    }

    public INode<T, P> addBefore(INode<T, P> p, T e) {
        INode<T, P> newNode = new INode<>(e, p.getPrev(), p);
        p.getPrev().setNext(newNode);
        p.setPrev(newNode);
        size++;
        return newNode;
    }

    public INode<T, P> addAfter(INode<T, P> p, T e) {
        INode<T, P> newNode = new INode<>(e, p, p.getNext());
        p.getNext().setPrev(newNode);
        p.setNext(newNode);
        size++;
        return newNode;
    }

    public INode<T, P> addFirst(T e) {
        return addAfter(head, e);
    }

    public INode<T, P> addLast(T e) {
        return addBefore(tail, e);
    }

    public T remove(INode<T, P> p) {
        p.getPrev().setNext(p.getNext());
        p.getNext().setPrev(p.getPrev());
        size--;
        return p.getElement();
    }

    public INode<T, P> getFirst() {
        if (isEmpty()) return null;
        return head.getNext();
    }

    public INode<T, P> getLast() {
        if (isEmpty()) return null;
        return tail.getPrev();
    }

    public INode<T, P> getNext(INode<T, P> p) {
        if (p == tail) return null;
        return p.getNext();
    }

    public INode<T, P> getPrev(INode<T, P> p) {
        if (p == head) return null;
        return p.getPrev();
    }

    @Override
    public Iterator<INode<T, P>> iterator() {
        return new IIterator(head, tail);
    }

    // 迭代器的实现
    class IIterator<T, P> implements Iterator<INode<T, P>> {
        INode<T, P> head;
        INode<T, P> tail;
        INode<T, P> cur;

        IIterator(INode<T, P> head, INode<T, P> tail) {
            this.head = head;
            this.tail = tail;
            this.cur = head;
        }

        @Override
        public boolean hasNext() {
            return this.cur.getNext() != tail;
        }

        @Override
        public INode<T, P> next() {
            var t = this.cur.getNext();
            this.cur = this.cur.getNext();
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}


